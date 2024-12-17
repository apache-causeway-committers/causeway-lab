/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.causeway.incubator.viewer.vaadin.ui.actionresponse;

import java.time.Duration;

import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.incubator.viewer.vaadin.model.models.VoidModel;
import org.apache.causeway.incubator.viewer.vaadin.model.util.PageUtils;
import org.apache.causeway.incubator.viewer.vaadin.ui.pages.voidreturn.VoidReturnPage;
import org.apache.causeway.wicketstubs.ContentDisposition;
import org.apache.causeway.wicketstubs.IRequestHandler;
import org.apache.causeway.wicketstubs.IResourceStream;
import org.apache.causeway.wicketstubs.RedirectRequestHandlerWithOpenUrlStrategy;
import org.apache.causeway.wicketstubs.ResourceStreamRequestHandler;
import org.apache.causeway.wicketstubs.api.AbstractAjaxBehavior;
import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.Page;
import org.apache.causeway.wicketstubs.api.RequestCycle;
import org.apache.causeway.wicketstubs.api.Url;

public enum ActionResultResponseHandlingStrategy {
    REDIRECT_TO_VOID {
        @Override
        public void handleResults(
                final MetaModelContext commonContext,
                final ActionResultResponse resultResponse) {

            PageUtils.pageRedirect(new VoidReturnPage(new VoidModel()));
        }
    },
    REDIRECT_TO_PAGE {
        @Override
        public void handleResults(
                final MetaModelContext commonContext,
                final ActionResultResponse resultResponse) {

            // force any changes in state etc to happen now prior to the redirect;
            // in the case of an object being returned, this should cause our page mementos
            // (eg EntityModel) to hold the correct state.  I hope.

            commonContext.getTransactionService().flushTransaction();

            // "redirect-after-post"
            resultResponse.getPageRedirect().applyTo(RequestCycle.get());
        }
    },
    SCHEDULE_HANDLER {
        @Override
        public void handleResults(
                final MetaModelContext commonContext,
                final ActionResultResponse resultResponse) {

            final RequestCycle requestCycle = RequestCycle.get();
            AjaxRequestTarget target = requestCycle.find(AjaxRequestTarget.class).orElse(null);

            if (target == null) {
                // non-Ajax request => just stream the Lob to the browser
                // or if this is a no-arg action, there also will be no parent for the component
                requestCycle.scheduleRequestHandlerAfterCurrent(resultResponse.getHandler());
            } else {
                // otherwise,
                // Ajax request => respond with a redirect to be able to stream the Lob to the client
                final IRequestHandler requestHandler = resultResponse.getHandler();
                if(requestHandler instanceof ResourceStreamRequestHandler) {
                    ResourceStreamRequestHandler scheduledHandler = (ResourceStreamRequestHandler) requestHandler;
                    StreamAfterAjaxResponseBehavior streamingBehavior = new StreamAfterAjaxResponseBehavior(scheduledHandler);
                    final Page page = target.getPage();
                    page.add(streamingBehavior);
                    CharSequence callbackUrl = streamingBehavior.getCallbackUrl();
                    scheduleJs(target, javascriptFor_sameWindow(callbackUrl), 10);
                } else if(requestHandler instanceof RedirectRequestHandlerWithOpenUrlStrategy) {
                    final RedirectRequestHandlerWithOpenUrlStrategy redirectHandler =
                            (RedirectRequestHandlerWithOpenUrlStrategy) requestHandler;

                    final String url = redirectHandler.getRedirectUrl();
                    final String fullUrl = expanded(requestCycle, url);

                    if(redirectHandler.getOpenUrlStrategy().isNewWindow()) {
                        scheduleJs(target, javascriptFor_newWindow(fullUrl), 100);
                    } else {
                        scheduleJs(target, javascriptFor_sameWindow(fullUrl), 100);
                    }
                } else {
                    throw _Exceptions.unrecoverable(
                            "no logic implemented to handle IRequestHandler of type %s",
                            requestHandler.getClass().getName());
                }

            }
        }
    },
    OPEN_URL_IN_NEW_BROWSER_WINDOW {
        @Override
        public void handleResults(
                final MetaModelContext commonContext,
                final ActionResultResponse resultResponse) {

            final AjaxRequestTarget target = resultResponse.getTarget();
            final String url = resultResponse.getUrl();
            final RequestCycle requestCycle = RequestCycle.get();
            final String fullUrl = expanded(requestCycle, url);

            scheduleJs(target, javascriptFor_newWindow(fullUrl), 100);
        }
    },
    OPEN_URL_IN_SAME_BROWSER_WINDOW {
        @Override
        public void handleResults(
                final MetaModelContext commonContext,
                final ActionResultResponse resultResponse) {

            final AjaxRequestTarget target = resultResponse.getTarget();
            final String url = resultResponse.getUrl();
            final RequestCycle requestCycle = RequestCycle.get();
            final String fullUrl = expanded(requestCycle, url);

            scheduleJs(target, javascriptFor_sameWindow(fullUrl), 100);
        }
    };

    public abstract void handleResults(
            MetaModelContext commonContext,
            ActionResultResponse resultResponse);

    /**
     * @see #expanded(String)
     */
    public static String expanded(final RequestCycle requestCycle, final String url) {
        return null;
    }

    /**
     * very simple templating support, the idea being that "antiCache=${currentTimeMillis}"
     * will be replaced automatically.
     */
    public static String expanded(String urlStr) {
        if(urlStr.contains("antiCache=${currentTimeMillis}")) {
            urlStr = urlStr.replace("antiCache=${currentTimeMillis}", "antiCache="+System.currentTimeMillis());
        }
        return urlStr;
    }

    private static String javascriptFor_newWindow(final CharSequence url) {
        return "function(){Wicket.Event.publish(Causeway.Topic.OPEN_IN_NEW_TAB, '" + url + "');}";
    }

    private static String javascriptFor_sameWindow(final CharSequence url) {
        return "\"window.location.href='" + url + "'\"";
    }

    private static void scheduleJs(final AjaxRequestTarget target, final String js, final int millis) {
        // the timeout is needed to let Wicket release the channel
        target.appendJavaScript(String.format("setTimeout(%s, %d);", js, millis));
    }


    /**
     * A special Ajax behavior that is used to stream the contents of a Lob after
     * an Ajax request.
     */
    private static class StreamAfterAjaxResponseBehavior extends AbstractAjaxBehavior {
        private static final long serialVersionUID = 1L;

        private final String fileName;
        private final IResourceStream resourceStream;
        private final Duration cacheDuration;

        public StreamAfterAjaxResponseBehavior(final ResourceStreamRequestHandler scheduledHandler) {
            this.fileName = scheduledHandler.getFileName();
            this.resourceStream = scheduledHandler.getResourceStream();
            this.cacheDuration = scheduledHandler.getCacheDuration();
        }

        public CharSequence getCallbackUrl() {
            return null;
        }
    }
}

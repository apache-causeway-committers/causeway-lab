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
package org.apache.causeway.incubator.viewer.vaadin.ui.pages.value;

import java.util.function.BiFunction;

import org.apache.causeway.applib.services.publishing.spi.PageRenderSubscriber;
import org.apache.causeway.applib.services.user.UserMemento;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.object.ManagedObjects;
import org.apache.causeway.incubator.viewer.vaadin.model.hints.CausewayEnvelopeEvent;
import org.apache.causeway.incubator.viewer.vaadin.model.models.ActionModel;
import org.apache.causeway.incubator.viewer.vaadin.model.models.ValueModel;
import org.apache.causeway.incubator.viewer.vaadin.ui.pages.PageAbstract;
import org.apache.causeway.incubator.viewer.vaadin.ui.util.Vdn;
import org.apache.causeway.viewer.commons.model.components.UiComponentType;
import org.apache.causeway.wicketstubs.api.AuthorizeInstantiation;
import org.apache.causeway.wicketstubs.api.Broadcast;
import org.apache.causeway.wicketstubs.api.PageParameterUtils;

import lombok.val;

/**
 * Web page representing an action invocation.
 */
@AuthorizeInstantiation(UserMemento.AUTHORIZED_USER_ROLE)
public class ValuePage
        extends PageAbstract {

    private static final long serialVersionUID = 1L;

    private static final String ID_ACTION_NAME = "actionName";
    private final ValueModel valueModel;

    /**
     * For use with
     */
    public ValuePage(final ValueModel valueModel) {
        this(valueModel, actionNameFrom(valueModel));
    }

    private ValuePage(final ValueModel valueModel, final String actionName) {
        super(PageParameterUtils.newPageParameters(), actionName, UiComponentType.VALUE);
        this.valueModel = valueModel;

        Vdn.labelAdd(themeDiv, ID_ACTION_NAME, actionName);

        addChildComponents(themeDiv, valueModel);
        addBookmarkedPages(themeDiv);
    }

    private static String actionNameFrom(final ValueModel valueModel) {
        ActionModel actionModel = valueModel.getActionModelHint();
        if (actionModel != null) {
            return actionModel.getFriendlyName();
        }
        return "Results"; // fallback, probably not required because hint should always exist on the model.
    }

    @Override
    protected void send(PageAbstract pageAbstract, Broadcast broadcast, CausewayEnvelopeEvent causewayEnvelopeEvent) {

    }

    @Override
    public void onRendering(final Can<PageRenderSubscriber> enabledObjectRenderSubscribers) {
        onRenderingOrRendered(enabledObjectRenderSubscribers, (pageRenderSubscriber, value) -> {
            pageRenderSubscriber.onRenderingValue(value);
            return null;
        });
    }

    @Override
    public void onRendered(final Can<PageRenderSubscriber> enabledObjectRenderSubscribers) {
        onRenderingOrRendered(enabledObjectRenderSubscribers, (pageRenderSubscriber, value) -> {
            pageRenderSubscriber.onRenderedValue(value);
            return null;
        });
    }

    private void onRenderingOrRendered(
            final Can<PageRenderSubscriber> pageRenderSubscribers,
            final BiFunction<PageRenderSubscriber, Object, Void> handler) {

        if (pageRenderSubscribers.isEmpty()) {
            return;
        }

        // guard against unspecified
        ManagedObjects.asSpecified(valueModel.getObject())
                .ifPresent(managedObject -> {

                    val nullableValuePojo = managedObject.getPojo();

                    pageRenderSubscribers.forEach(subscriber -> handler.apply(subscriber, nullableValuePojo));
                });
    }
}

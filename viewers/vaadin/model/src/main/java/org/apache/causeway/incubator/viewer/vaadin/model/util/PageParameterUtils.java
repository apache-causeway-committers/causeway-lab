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
package org.apache.causeway.incubator.viewer.vaadin.model.util;

import java.util.Optional;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.Identifier;
import org.apache.causeway.applib.services.bookmark.Bookmark;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.object.ManagedObjects;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.core.metamodel.util.Facets;
import org.apache.causeway.incubator.viewer.vaadin.model.mementos.PageParameterNames;
import org.apache.causeway.incubator.viewer.vaadin.model.models.UiObjectVdn;
import org.apache.causeway.wicketstubs.IPageRequestHandler;
import org.apache.causeway.wicketstubs.api.PageParameters;
import org.apache.causeway.wicketstubs.PageRequestHandlerTracker;
import org.apache.causeway.wicketstubs.api.RequestCycle;
import org.apache.causeway.wicketstubs.api.StringValue;

import lombok.NonNull;
import lombok.val;
import lombok.experimental.UtilityClass;

/**
 * A helper class for dealing with PageParameters
 */
@UtilityClass
public class PageParameterUtils {

    /**
     * The name of the special request parameter that controls whether the page header/navigation bar
     * should be shown or not
     */
    public static final String CAUSEWAY_NO_HEADER_PARAMETER_NAME = "causeway.no.header";

    /**
     * The name of the special request parameter that controls whether the page footer
     * should be shown or not
     */
    public static final String CAUSEWAY_NO_FOOTER_PARAMETER_NAME = "causeway.no.footer";

    /**
     * Creates a new instance of PageParameters that preserves some special request parameters
     * which should propagate in all links created by Causeway
     *
     * @return a new PageParameters instance
     */
    public PageParameters newPageParameters() {
        val newPageParameters = new PageParameters();
        val requestCycle = RequestCycle.get();

        if (requestCycle != null) {
            Optional.ofNullable(PageRequestHandlerTracker.getFirstHandler(requestCycle))
                    .map(IPageRequestHandler::getPageParameters)
                    .ifPresent(currentPageParameters -> {
                        final StringValue noHeader = currentPageParameters.get(CAUSEWAY_NO_HEADER_PARAMETER_NAME);
                        if (!noHeader.isNull()) {
                            newPageParameters.set(CAUSEWAY_NO_HEADER_PARAMETER_NAME, noHeader.toString());
                        }
                        final StringValue noFooter = currentPageParameters.get(CAUSEWAY_NO_FOOTER_PARAMETER_NAME);
                        if (!noFooter.isNull()) {
                            newPageParameters.set(CAUSEWAY_NO_FOOTER_PARAMETER_NAME, noFooter.toString());
                        }

                    });

        }
        return newPageParameters;
    }
/*
    public Stream<NamedPair> streamCurrentRequestParameters() {
        return Optional.ofNullable(RequestCycle.get()).stream()
                .map(RequestCycle::getRequest)
                .map(Request::getRequestParameters)
                .flatMap(params->
                        params.getParameterNames().stream()
                                .map(key->new NamedPair(key, params.getParameterValue(key).toString()))
                );
    }
*/
    // -- FACTORY METHODS FOR PAGE PARAMETERS

    public PageParameters createPageParametersForBookmark(final Bookmark bookmark) {
        val pageParameters = PageParameterUtils.newPageParameters();
        PageParameterNames.OBJECT_OID.addStringTo(pageParameters, bookmark.stringify());
        return pageParameters;
    }

    /**
     * Factory method for creating {@link PageParameters} to represent an
     * object.
     */
    public PageParameters createPageParametersForObject(final ManagedObject adapter) {

        val pageParameters = PageParameterUtils.newPageParameters();
        val isEntity = ManagedObjects.isIdentifiable(adapter);

        if (isEntity) {
            ManagedObjects.stringify(adapter)
                    .ifPresent(oidStr -> PageParameterNames.OBJECT_OID.addStringTo(pageParameters, oidStr));
        } else {
            // don't do anything; instead the page should be redirected back to
            // an EntityPage so that the underlying EntityModel that contains
            // the memento for the transient ObjectAdapter can be accessed.
        }
        return pageParameters;
    }

    public PageParameters createPageParametersForBookmarkablePageLink(
            final ManagedObject adapter) {

        return
                ManagedObjects.isIdentifiable(adapter)
                        && !ManagedObjects.isNullOrUnspecifiedOrEmpty(adapter)
                        ? UiObjectVdn.ofAdapter(
                                Facets.projected(adapter))
                        .getPageParametersWithoutUiHints()
                        : PageParameterUtils.createPageParametersForObject(null);
    }

    public PageParameters createPageParametersForAction(
            final ManagedObject adapter,
            final ObjectAction objectAction,
            final Can<ManagedObject> paramValues) {

        val pageParameters = createPageParameters(adapter, objectAction);

        // capture argument values
        for (val argumentAdapter : paramValues) {
            val encodedArg = encodeArg(argumentAdapter);
            PageParameterNames.ACTION_ARGS.addStringTo(pageParameters, encodedArg);
        }

        return pageParameters;
    }

    public Optional<Bookmark> toBookmark(final PageParameters pageParameters) {
        val oidStr = PageParameterNames.OBJECT_OID.getStringFrom(pageParameters);
        return _Strings.isEmpty(oidStr)
                ? Optional.empty()
                : Optional.of(Bookmark.parseElseFail(oidStr));
    }

    // -- HELPERS

    private static PageParameters createPageParameters(
            final ManagedObject adapter, final ObjectAction objectAction) {

        val pageParameters = PageParameterUtils.newPageParameters();

        ManagedObjects.stringify(adapter)
                .ifPresent(oidStr ->
                        PageParameterNames.OBJECT_OID.addStringTo(pageParameters, oidStr));

        val actionScope = objectAction.getScope();
        PageParameterNames.ACTION_TYPE.addEnumTo(pageParameters, actionScope);

        val actionOnTypeSpec = objectAction.getDeclaringType();
        if (actionOnTypeSpec != null) {
            PageParameterNames.ACTION_OWNING_SPEC.addStringTo(pageParameters, actionOnTypeSpec.getFullIdentifier());
        }

        val actionId = determineActionId(objectAction);
        PageParameterNames.ACTION_ID.addStringTo(pageParameters, actionId);

        return pageParameters;
    }

    private static String determineActionId(final ObjectAction objectAction) {
        final Identifier identifier = objectAction.getFeatureIdentifier();
        if (identifier != null) {
            return identifier.getMemberNameAndParameterClassNamesIdentityString();
        }
        // fallback (used for action sets)
        return objectAction.getId();
    }

    private static final String NULL_ARG = "$nullArg$";

    private String encodeArg(final ManagedObject adapter) {
        if (adapter == null) {
            return NULL_ARG;
        }
        return ManagedObjects.stringify(adapter).orElse(null);
    }

    //FIXME don't silently ignore failures
    private @Nullable ManagedObject decodeArg(
            final @NonNull MetaModelContext mmc,
            final ObjectSpecification objSpec,
            final String encoded) {
        if (NULL_ARG.equals(encoded)) {
            return null;
        }
        try {
            return Bookmark.parseUrlEncoded(encoded)
                    .flatMap(mmc.getObjectManager()::loadObject)
                    .orElse(null);
        } catch (final Exception e) {
            e.printStackTrace(); // I suppose useful when in prototyping mode only
            return null;
        }
    }

}

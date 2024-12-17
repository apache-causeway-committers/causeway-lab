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
package org.apache.causeway.incubator.viewer.vaadin.ui.util;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.incubator.viewer.vaadin.model.links.LinkAndLabel;
import org.apache.causeway.incubator.viewer.vaadin.ui.util.VdnDecorators.ActionLink;
import org.apache.causeway.viewer.commons.model.decorators.ConfirmDecorator.ConfirmDecorationModel;
import org.apache.causeway.viewer.commons.model.layout.UiPlacementDirection;
import org.apache.causeway.wicketstubs.AbstractLink;
import org.apache.causeway.wicketstubs.Label;
import org.apache.causeway.wicketstubs.Model;
import org.apache.causeway.wicketstubs.api.BookmarkablePageLink;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.IModel;
import org.apache.causeway.wicketstubs.api.ListItem;
import org.apache.causeway.wicketstubs.api.MarkupContainer;
import org.apache.causeway.wicketstubs.api.Page;
import org.apache.causeway.wicketstubs.api.PageParameters;

import lombok.NonNull;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class VdnLinks {

    public <T extends Page> AbstractLink newBookmarkablePageLink(
            final String linkId, final PageParameters pageParameters, final Class<T> pageClass) {
        return new BookmarkablePageLink<Void>(linkId, pageClass, pageParameters);
    }

    /**
     * For rendering {@link LinkAndLabel} within additional-link panels or drop-downs.
     */
    public AbstractLink asAdditionalLink(
            final Component tooltipReceiver,
            final String titleId,
            final LinkAndLabel linkAndLabel,
            final boolean isForceAlignmentWithBlankIcon) {

        val link = linkAndLabel.getUiComponent();
        val action = linkAndLabel.getManagedAction().getAction();
        val hasDisabledReason = false;

/*       val hasDisabledReason = link instanceof ActionLink && _Strings.isNotEmpty(((ActionLink) link).getReasonDisabledIfAny());

        VdnTooltips.addTooltip(tooltipReceiver, hasDisabledReason
                ? ((ActionLink) link).getReasonDisabledIfAny()
                : linkAndLabel.getDescription().orElse(null));
*/
        if (ObjectAction.Util.returnsBlobOrClob(action)) {
            Vdn.cssAppend(link, "noVeil");
        }
        if (action.isPrototype()) {
            Vdn.cssAppend(link, "prototype");
        }
//        Vdn.cssAppend(link, linkAndLabel.getFeatureIdentifier());

        if (action.getSemantics().isAreYouSure()) {
            if (action.getParameterCount() == 0) {
                if (!hasDisabledReason) {
                    val translationService = linkAndLabel.getAction().getMetaModelContext()
                            .getTranslationService();
                    val confirmUiModel = ConfirmDecorationModel
                            .areYouSure(translationService, UiPlacementDirection.BOTTOM);
//                    VdnDecorators.getConfirm().decorate(link, confirmUiModel);
                }
            }
            // ensure links receive the danger style
            // don't care if expressed twice
//            VdnDecorators.getDanger().decorate(link);
        } else {
/*            Vdn.cssAppend(link, linkAndLabel.isRenderOutlined()
                    || action.isPrototype()
                    ? BootstrapConstants.ButtonSemantics.SECONDARY.buttonOutlineCss()
                    : BootstrapConstants.ButtonSemantics.SECONDARY.buttonDefaultCss());*/
        }

        linkAndLabel
                .getAdditionalCssClass()
                .ifPresent(cssClass -> Vdn.cssAppend(link, cssClass));

/*        val viewTitleLabel = Vdn.labelAdd(link, titleId,
                linkAndLabel::getFriendlyName);*/

        val faLayers = linkAndLabel.lookupFontAwesomeLayers(isForceAlignmentWithBlankIcon);

//        VdnDecorators.getIcon().decorate(viewTitleLabel, faLayers);
//        VdnDecorators.getMissingIcon().decorate(viewTitleLabel, faLayers);
        return link;
    }

    public static <T, R extends MarkupContainer> R listItemAsDropdownLink(
            final @NonNull ListItem<T> item,
            final @NonNull R container,
            final @NonNull String titleId, final @NonNull Function<T, IModel<String>> titleProvider,
            final @NonNull String iconId, final @Nullable Function<T, IModel<String>> iconProvider,
            final @Nullable BiFunction<T, Label, IModel<String>> cssFactory) {

        val t = item.getModelObject();

        // add title and icon to the link

        Vdn.labelAdd(container, titleId, titleProvider.apply(t));

        final Label viewItemIcon = Vdn.labelAdd(container, iconId, Optional.ofNullable(iconProvider)
                .map(iconProv -> iconProv.apply(t))
                .orElseGet(() -> Model.of("")));

        Optional.ofNullable(cssFactory)
                .map(cssFact -> cssFact.apply(t, viewItemIcon))
                .ifPresent(cssModel -> Vdn.cssAppend(viewItemIcon, cssModel));

        return container;

    }
}

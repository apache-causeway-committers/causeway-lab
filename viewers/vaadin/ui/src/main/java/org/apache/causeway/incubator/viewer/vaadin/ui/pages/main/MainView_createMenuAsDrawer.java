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
package org.apache.causeway.incubator.viewer.vaadin.ui.pages.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.viewer.commons.applib.services.branding.BrandingUiModel;
import org.apache.causeway.viewer.commons.applib.services.header.HeaderUiModel;

import lombok.val;

//@Log4j2
final class MainView_createMenuAsDrawer {

    static Component apply(
            final MetaModelContext commonContext,
            final HeaderUiModel headerUiModel,
            final UiActionHandlerVaa uiActionHandlerVaa
    ) {
        val drawerLayout = new VerticalLayout(){{
            setHeightFull();
            setPadding(false);
            setMargin(false);
            setSpacing(false);
        }};

        val leftMenuBuilder = CustomMenuBuilderVaa.of(commonContext, drawerLayout, uiActionHandlerVaa, true);
        val rightMenuBuilder = CustomMenuBuilderVaa.of(commonContext, drawerLayout, uiActionHandlerVaa, false);

        headerUiModel.navbar().primary().visitMenuItems(leftMenuBuilder);
        headerUiModel.navbar().secondary().visitMenuItems(rightMenuBuilder);
        headerUiModel.navbar().tertiary().visitMenuItems(rightMenuBuilder);

        return drawerLayout;
    }

    // -- HELPER

    private static Component createTitleOrLogo(
            final MetaModelContext commonContext,
            final BrandingUiModel brandingUiModel) {

        val brandingName = brandingUiModel.getName();
        val brandingLogo = brandingUiModel.getLogoHref();

        if (brandingLogo.isPresent()) {
            val webAppContextPath = commonContext.getWebAppContextPath();
            val logo = new Image(
                    webAppContextPath.prependContextPathIfLocal(brandingLogo.get()),
                    "brandingLogo");
            logo.setWidth("48px"); //TODO make this part of the UI model
            logo.setHeight("48px"); //TODO make this part of the UI model
            return logo;
        }
        return new Text(brandingName.orElse("App"));

    }

}

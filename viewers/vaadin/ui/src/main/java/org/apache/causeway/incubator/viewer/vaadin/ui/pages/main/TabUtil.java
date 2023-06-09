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

import java.util.Optional;
import java.util.function.Consumer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;

import org.apache.causeway.core.runtimeservices.menubars.bootstrap.MenuBarsServiceBootstrap;

import lombok.experimental.UtilityClass;

//XXX not used
@UtilityClass
final class TabUtil {

    static void createMenuTabs(final MenuBarsServiceBootstrap menuBarsService, final Consumer<Tab> onTabCreated) {
        // onTabCreated.accept(TabUtil.createTab("Dashboard", DashboardView.class));
    }

    static void selectTab(final Tabs tabs, final Class<? extends Component> viewClass) {
        String target = RouteConfiguration.forSessionScope().getUrl(viewClass);
        Optional<Component> tabToSelect = tabs.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> tabs.setSelectedTab((Tab) tab));
    }

    static Tab createTab(final String title, final Class<? extends Component> viewClass) {
        final String text = null;
        return createTab(populateLink(new RouterLink(text, viewClass), title));
    }

    // -- HELPER

    private static Tab createTab(final Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(final T a, final String title) {
        a.add(title);
        return a;
    }



}

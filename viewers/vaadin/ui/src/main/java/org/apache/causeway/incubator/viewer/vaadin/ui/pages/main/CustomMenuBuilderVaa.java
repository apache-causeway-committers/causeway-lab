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

import java.util.Objects;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.causeway.applib.fa.FontAwesomeLayers;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.viewer.commons.applib.services.menu.MenuVisitor;
import org.apache.causeway.viewer.commons.applib.services.menu.model.MenuAction;
import org.apache.causeway.viewer.commons.applib.services.menu.model.MenuDropdown;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor(staticName = "of")
//@Log4j2
class CustomMenuBuilderVaa implements MenuVisitor {

    private final MetaModelContext commonContext;
    private final HasComponents menuBar;
    private final UiActionHandlerVaa uiActionHandlerVaa;
    private final boolean isPrimary;

    private VerticalLayout currentTopLevelMenu = null;

    @Override
    public void onTopLevel(final MenuDropdown menuDto) {
        val layout = new VerticalLayout(){{
            setWidthFull();
            setPadding(false);
            setMargin(false);
            setSpacing(false);
            setAlignItems(Alignment.START);
        }};
        val details = new Details(menuDto.name()) {{
            addThemeVariants(DetailsVariant.SMALL);
            setOpened(isPrimary);
            add(layout);
        }};
        currentTopLevelMenu = layout;
        menuBar.add(details);
    }

    @Override
    public void onMenuAction(final MenuAction menuAction) {
        Objects.requireNonNull(currentTopLevelMenu, "currentTopLevelMenu");
        menuAction.managedAction().ifPresent(managedAction -> {
            val button = new Button(menuAction.name()) {{
                addThemeVariants(ButtonVariant.LUMO_SMALL);
                // FIXME Alf use factory
                // FIXME Alf use class and css
                getStyle().setMarginLeft("1em");
                getStyle().setBorder("none");
                getStyle().setBackgroundColor("transparent");
                getStyle().setMarginBottom("0em");
                getStyle().setMarginTop("0em");
            }};

            currentTopLevelMenu.add(button);

            ObjectAction.Util.cssClassFaFactoryFor(
                            managedAction.getAction(),
                            managedAction.getOwner()).ifPresent(faLayersProvider -> {

                FontAwesomeLayers layers = faLayersProvider.getLayers();
                FontAwesomeLayers.IconType iconType = layers.getIconType();
                if(iconType == FontAwesomeLayers.IconType.SINGLE) {
                    // only SINGLE expected/handled
                    if(layers.getIconEntries() != null) {
                        String cssClasses = layers.getIconEntries().get(0).getCssClasses();
                        button.setIcon(new FaIcon(cssClasses));
                    }
                }
            });

            button.addClickListener(event -> {
                uiActionHandlerVaa.handleActionLinkClicked(managedAction);
            });
        });
    }

    @Override
    public void onSectionSpacer() {
        val sectionSpacer = new Div(){{
            setWidth("100%");
            setHeight("0.1em");
        }};
        menuBar.add(sectionSpacer);
    }

    @Override
    public void onSectionLabel(final String named) {
        val label = new Div() {{
            add(named);
        }};
        menuBar.add(label);
    }

}
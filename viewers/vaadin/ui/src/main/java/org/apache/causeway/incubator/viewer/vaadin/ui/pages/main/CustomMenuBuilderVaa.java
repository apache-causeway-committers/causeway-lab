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

import org.apache.causeway.applib.Identifier;
import org.apache.causeway.applib.id.LogicalType;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.core.metamodel.facets.members.iconfa.FaFacet;
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
            val iconName = getIconName(menuAction);
            button.setIcon(new FaIcon(iconName));
            button.addClickListener(event -> {
                uiActionHandlerVaa.handleActionLinkClicked(managedAction);
            });
        });
    }

    /**
     * workaround for missing cssClassFa - to be fixed
     *
     * @param menuAction
     * @return
     */
    private String getIconName(final MenuAction menuAction) {
        var specLoader = MetaModelContext.instanceElseFail().getSpecificationLoader();

        final Identifier id = menuAction.actionId();
        final LogicalType lt = id.getLogicalType();
        final Class<?> clazz = lt.getCorrespondingClass();

        var spec = specLoader.loadSpecification(clazz);
        var valueSampleAct = spec.getAction(id.getMemberLogicalName()).orElseThrow();
        final FaFacet faFacet = valueSampleAct.getFacet(FaFacet.class);

        final String iconName = extractElementFromFacet("classes", faFacet);
        return iconName;
    }

    // ugly hack to get a hold of the fa class name
    private String extractElementFromFacet(String element, FaFacet faFacet) {
        final String elementsStr = faFacet.toString()
                .replace("FaFacetForActionLayoutAnnotation[", "")
                .replace("]", "");
        final String[] elements = elementsStr.split(";");
        for (String s : elements) {
            String t = s.trim();
            if (t.startsWith(element)) {
                final String value = t.split("=")[1].trim();
                if (!value.startsWith("!"))
                    return "fas fa-" + value;
            }
        }
        return "fa-question";
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
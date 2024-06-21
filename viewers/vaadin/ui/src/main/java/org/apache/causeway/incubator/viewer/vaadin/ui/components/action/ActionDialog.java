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
package org.apache.causeway.incubator.viewer.vaadin.ui.components.action;

import java.util.function.Predicate;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedAction;
import org.apache.causeway.core.metamodel.interactions.managed.ParameterNegotiationModel;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.UiComponentFactoryVaa;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

public class ActionDialog extends Dialog {

    public static ActionDialog forManagedAction(
            final @NonNull UiComponentFactoryVaa uiComponentFactory,
            final @NonNull ManagedAction managedAction,
            final @NonNull Predicate<Can<ManagedObject>> submitCallback) {

        val actionDialog = new ActionDialog(uiComponentFactory, managedAction, submitCallback);

        // Add a listener to make the dialog transparent, while moved
        actionDialog.getElement().addEventListener("dragstart", event -> {
            actionDialog.addClassName("transparent-dialog");
            //actionDialog.getElement().getStyle().set("background-color", "transparent");
            //actionDialog.getElement().getStyle().set("opacity", "0.5");
        });
        actionDialog.getElement().addEventListener("dragend", event -> {
            actionDialog.removeClassName("transparent-dialog");
            //actionDialog.getElement().getStyle().set("background-color", "opaque");
            //actionDialog.getElement().getStyle().set("opacity", "1");
        });

        return actionDialog;
    }

    protected ActionDialog(
            final UiComponentFactoryVaa uiComponentFactory,
            final ManagedAction managedAction,
            final Predicate<Can<ManagedObject>> submitCallback
    ) {
        setDraggable(true);
//        setModal(true);
        setResizable(true);
        setCloseOnEsc(true);

        // Dialog Theme
        setWidth(60, Unit.EM);
        setHeight("auto");
        setMinWidth(30, Unit.EM);
        setMinHeight(5, Unit.EM);
        // -- Header

        val hideableComponents = Can.<Component>of();
        val header = header(managedAction, hideableComponents);
        getHeader().add(header.buttons);
        setHeaderTitle(header.title);
        // -- Content

        val actionForm = ActionForm.forManagedAction(uiComponentFactory, managedAction);
        add(new Scroller(actionForm) {{
            setScrollDirection(ScrollDirection.VERTICAL);
        }});
        // -- Footer
        val footer = footer(managedAction, actionForm.getPendingArgs(), submitCallback);
        getFooter().add(footer);

    }

    // -- HELPER
    private record HeaderParts(String title, Component buttons){}
    private HeaderParts header(
            final ManagedAction managedAction,
            final Can<Component> hideableComponents
    ) {
        val minButton = new Button(VaadinIcon.ANGLE_DOWN.create());
        val maxButton = new Button(VaadinIcon.EXPAND_SQUARE.create());
        val closeButton = new Button(VaadinIcon.CLOSE_SMALL.create());

        // -- layout
        val header = new HorizontalLayout() {{
            setWidthFull();
            val spacer = new Div();
            spacer.getStyle().set("margin", "auto");
            add(spacer);
            add(minButton);
            add(maxButton);
            add(closeButton);
        }};

        // -- binding
        val resizeHandler = DialogResizeHandler.of(this, hideableComponents);
        resizeHandler.bindMinimise(minButton);
        resizeHandler.bindMaximise(maxButton);
        closeButton.addClickListener(event -> close());

        val title = managedAction.getFriendlyName();
        return new HeaderParts(title, header);
    }

    private Component footer(
            final ManagedAction managedAction,
            final ParameterNegotiationModel pendingArgs,
            final Predicate<Can<ManagedObject>> submitCallback
    ) {

        // TODO translate
        val okButton = new Button("Ok") {{
            addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }};
        // TODO translate
        val cancelButton = new Button("Cancel") {{
            addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        }};
        // -- layout
        okButton.getStyle().set("margin-right", "auto");
        val footer = new HorizontalLayout(okButton, cancelButton) {{
            setWidthFull();
        }};


        // -- binding
        okButton.addClickListener(event -> {
            //invoke the action and route to the result page
            if (submitCallback.test(pendingArgs.getParamValues())) {
                close();
            } else {
                //TODO handle validation feedback (vetos)
            }
        });
        cancelButton.addClickListener(event -> close());

        return footer;
    }

    // -- RESIZING

    @RequiredArgsConstructor(staticName = "of")
    private static class DialogResizeHandler {

        private static final String DOCK = "dock";
        private static final String FULLSCREEN = "fullscreen";

        private boolean isDocked = false;
        private boolean isFullScreen = false;

        private final Dialog dialog;
        private final Can<Component> hidableComponents;

        private Button minButton;
        private Button maxButton;

        public void bindMinimise(final Button minButton) {
            this.minButton = minButton;
            minButton.addClickListener(event -> minimise());
        }

        public void bindMaximise(final Button maxButton) {
            this.maxButton = maxButton;
            maxButton.addClickListener(event -> maximise());
        }

        private void initialSize() {
            minButton.setIcon(VaadinIcon.ANGLE_DOWN.create());
            dialog.getElement().getThemeList().remove(DOCK);
            maxButton.setIcon(VaadinIcon.EXPAND_SQUARE.create());
            dialog.getElement().getThemeList().remove(FULLSCREEN);
            dialog.setWidth("600px");
            dialog.setHeight("auto");
        }

        private void minimise() {
            if (isDocked) {
                initialSize();
            } else {
                if (isFullScreen) {
                    initialSize();
                }
                minButton.setIcon(VaadinIcon.ANGLE_UP.create());
                dialog.getElement().getThemeList().add(DOCK);
                dialog.setWidth("320px");
            }
            isDocked = !isDocked;
            isFullScreen = false;
            hidableComponents.forEach(comp -> comp.setVisible(!isDocked));
        }

        private void maximise() {
            if (isFullScreen) {
                initialSize();
            } else {
                if (isDocked) {
                    initialSize();
                }
                maxButton.setIcon(VaadinIcon.COMPRESS_SQUARE.create());
                dialog.getElement().getThemeList().add(FULLSCREEN);
                dialog.setSizeFull();
                hidableComponents.forEach(comp -> comp.setVisible(true));
            }
            isFullScreen = !isFullScreen;
            isDocked = false;
        }

    }

}

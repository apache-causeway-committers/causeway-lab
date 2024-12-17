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
package org.apache.causeway.incubator.viewer.vaadin.ui.components.widgets.bootstrap;

import org.apache.causeway.incubator.viewer.vaadin.model.models.ActionPrompt;
import org.apache.causeway.incubator.viewer.vaadin.ui.util.Vdn;
import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.Draggable;
import org.apache.causeway.wicketstubs.api.DraggableConfig;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;
import org.apache.causeway.wicketstubs.api.IModel;
import org.apache.causeway.wicketstubs.api.IPartialPageRequestHandler;
import org.apache.causeway.wicketstubs.api.Modal;
import org.apache.causeway.wicketstubs.api.WebMarkupContainer;

/**
 * A base class for all modal dialogs
 */
public class ModalDialog<T>
        extends Modal<T>
        implements ActionPrompt {

    private static final long serialVersionUID = 1L;

    public ModalDialog(final String markupId) {
        this(markupId, null);
    }

    public ModalDialog(final String id, final IModel<T> model) {
        super(id, model);
        setFadeIn(false);
        setUseKeyboard(true);
        setDisableEnforceFocus(true);
        setOutputMarkupPlaceholderTag(true);
        Vdn.containerAdd(this, getContentId()); // initial empty content
    }

    private void setOutputMarkupPlaceholderTag(boolean b) {
    }

    private void setDisableEnforceFocus(boolean b) {
    }

    private void setUseKeyboard(boolean b) {
    }

    private void setFadeIn(boolean b) {
        //FIXME
    }

    @Override
    public void setTitle(final Component component, final AjaxRequestTarget target) {

    }

    private Object get(String s) {
        return null;
    }

    @Override
    public void setPanel(final Component component, final AjaxRequestTarget target) {
        addOrReplace(component);
        showPrompt(target);
    }

    private void addOrReplace(Component component) {
        //FIXME
    }

    @Override
    public void showPrompt(final AjaxRequestTarget target) {
    }

    @Override
    public String getTitleId() {
        return "header-label";
    }

    @Override
    public String getContentId() {
        return "content";
    }

    @Override
    public void closePrompt(final AjaxRequestTarget target) {
        addJavaScriptForClosing(target);
        setVisible(false);
    }

    private void setVisible(boolean b) {
    }

    @Override
    protected WebMarkupContainer createDialog(final String id) {
        WebMarkupContainer dialog = super.createDialog(id);
        return dialog;
    }

    // -- HELPER

    private void addJavaScriptForClosing(final AjaxRequestTarget target) {

    }

    protected Modal<T> appendShowDialogJavaScript(IPartialPageRequestHandler target) {
        return null; //FIXME
    }

    protected T renderHead(IHeaderResponse response) {
        return null;//FIXME
    }
}

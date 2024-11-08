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
package org.apache.causeway.incubator.viewer.vaadin.ui.components.actionprompt;

import org.apache.causeway.incubator.viewer.vaadin.model.models.ActionPromptWithExtraContent;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.widgets.bootstrap.ModalDialog;
import org.apache.causeway.incubator.viewer.vaadin.ui.util.Vdn;
import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.GenericPanel;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;
import org.apache.causeway.wicketstubs.api.IModel;
import org.apache.causeway.wicketstubs.api.IPartialPageRequestHandler;
import org.apache.causeway.wicketstubs.api.Modal;

public class ActionPromptModalWindow
extends ModalDialog<Void>
implements ActionPromptWithExtraContent {

    private static final long serialVersionUID = 1L;

    private static final String ID_EXTRA_CONTENT = "extraContent";

    public static ActionPromptModalWindow newModalWindow(final String id) {
        return new ActionPromptModalWindow(id);
    }

    public ActionPromptModalWindow(final String id) {
        super(id);
    }

    @Override
    public Modal<Void> appendShowDialogJavaScript(final IPartialPageRequestHandler target) {

        // the default implementation seems to make its two calls in the wrong order, in particular with
        // appendDisableEnforceFocus called after the modal javascript object has already been created.
        // so this patch makes sure it is called before hand.  This results in the JavaScript fragment being
        // invoked twice, but what the hey.
        //appendDisableEnforceFocus(target);

        // we continue to call the original implementation, for maintainability
        return super.appendShowDialogJavaScript(target);
    }


    @Override
    public Void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        Vdn.javaScriptAdd(response, Vdn.EventTopic.FOCUS_FIRST_PARAMETER, getMarkupId());
        return null; //FIXME
    }

    private Object getMarkupId() {
        return null; //FIXME
    }

    @Override
    public String getExtraContentId() {
        return ID_EXTRA_CONTENT;
    }


    @Override
    public void setExtraContentPanel(final Component extraContentComponent, final AjaxRequestTarget target) {
        extraContentComponent.setMarkupId(getExtraContentId());
        addOrReplace(extraContentComponent);
    }

    private void addOrReplace(Component extraContentComponent) {
    }

    @Override
    public void setTitle(Component component, AjaxRequestTarget target) {

    }

    @Override
    public void setPanel(Component component, AjaxRequestTarget target) {

    }

    @Override
    public void showPrompt(AjaxRequestTarget target) {

    }

    @Override
    public void closePrompt(AjaxRequestTarget target) {

    }

    @Override
    public IModel<Void> getModel() {
        return super.getModel();
    }

    @Override
    public GenericPanel<Void> setModel(IModel<Void> model) {
        return super.setModel(model);
    }

    @Override
    public Void getModelObject() {
        return super.getModelObject();
    }

    @Override
    public GenericPanel<Void> setModelObject(Void object) {
        return super.setModelObject(object);
    }
}

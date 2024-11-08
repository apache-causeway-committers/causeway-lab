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
package org.apache.causeway.incubator.viewer.vaadin.ui.components.actionpromptsb;

import org.apache.causeway.incubator.viewer.vaadin.model.models.ActionPromptWithExtraContent;
import org.apache.causeway.incubator.viewer.vaadin.ui.util.Vdn;
import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.GenericPanel;
import org.apache.causeway.wicketstubs.api.IModel;
import org.apache.causeway.wicketstubs.api.WebMarkupContainer;

public class ActionPromptSidebar
        extends GenericPanel<Void>
        implements ActionPromptWithExtraContent {

    private static final long serialVersionUID = 1L;

    private static final String ID_HEADER = "header";
    private static final String ID_ACTION_PROMPT = "actionPrompt";
    private static final String ID_EXTRA_CONTENT = "extraContent";

    public ActionPromptSidebar(final String id) {
        super(id);

        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);

        Vdn.labelAdd(this, getTitleId(), "(no action)");

        add(new WebMarkupContainer(getContentId()));
        add(new WebMarkupContainer(getExtraContentId()));
    }

    private void setOutputMarkupPlaceholderTag(boolean b) {
    }

    private void setOutputMarkupId(boolean b) {
    }

    public static ActionPromptSidebar newSidebar(final String id) {
        return new ActionPromptSidebar(id);
    }

    @Override
    public String getTitleId() {
        return ID_HEADER;
    }

    @Override
    public String getContentId() {
        return ID_ACTION_PROMPT;
    }

    @Override
    public String getExtraContentId() {
        return ID_EXTRA_CONTENT;
    }

    private Object getMarkupId() {
        return null; //FIXME
    }

    @Override
    public void closePrompt(final AjaxRequestTarget target) {
        setVisible(false);
        // we no longer remove the panel, because hitting 'Esc' seems to cause the
        // cancelButton callback to be fired twice, resulting in a stack trace:
        //
        // org.apache.wicket.core.request.handler.ComponentNotFoundException: Component
        // 'theme:actionPromptSidebar:actionPrompt:parameters:inputForm:cancelButton' has been removed from page.)

        // addOrReplace(new WebMarkupContainer(getContentId()));
        addOrReplace(new WebMarkupContainer(getExtraContentId()));

        if (target != null) {
            hide(target);
        }
    }

    private void setVisible(boolean b) {
        //FIXME
    }

    private void addOrReplace(WebMarkupContainer webMarkupContainer) {
    }

    private void show(final AjaxRequestTarget target) {
        target.appendJavaScript("$('#wrapper').removeClass('toggled')");
    }

    private void hide(final AjaxRequestTarget target) {
        target.appendJavaScript("$('#wrapper').addClass('toggled')");
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

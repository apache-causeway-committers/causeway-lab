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
package org.apache.causeway.incubator.viewer.vaadin.ui.pages.voidreturn;

import org.apache.causeway.applib.services.user.UserMemento;
import org.apache.causeway.incubator.viewer.vaadin.model.hints.CausewayEnvelopeEvent;
import org.apache.causeway.incubator.viewer.vaadin.model.models.ActionModel;
import org.apache.causeway.incubator.viewer.vaadin.model.models.VoidModel;
import org.apache.causeway.incubator.viewer.vaadin.ui.pages.PageAbstract;
import org.apache.causeway.incubator.viewer.vaadin.ui.util.Vdn;
import org.apache.causeway.viewer.commons.model.components.UiComponentType;
import org.apache.causeway.wicketstubs.api.AuthorizeInstantiation;
import org.apache.causeway.wicketstubs.api.Broadcast;
import org.apache.causeway.wicketstubs.api.PageParameterUtils;

/**
 * Web page representing an action invocation.
 */
@AuthorizeInstantiation(UserMemento.AUTHORIZED_USER_ROLE)
public class VoidReturnPage
        extends PageAbstract {

    private static final long serialVersionUID = 1L;

    private static final String ID_ACTION_NAME = "actionName";

    public VoidReturnPage(final VoidModel model) {
        this(model, actionNameFrom(model));
    }

    private VoidReturnPage(final VoidModel model, final String actionName) {
        super(PageParameterUtils.newPageParameters(), actionName, UiComponentType.VOID_RETURN);

        Vdn.labelAdd(themeDiv, ID_ACTION_NAME, actionName);

        addChildComponents(themeDiv, model);
        addBookmarkedPages(themeDiv);
    }

    private static String actionNameFrom(final VoidModel model) {
        ActionModel actionModel = model.getActionModelHint();
        if (actionModel != null) {
            return actionModel.getFriendlyName();
        }
        return "Results"; // fallback, probably not required because hint should always exist on the model.
    }

    @Override
    protected void send(PageAbstract pageAbstract, Broadcast broadcast, CausewayEnvelopeEvent causewayEnvelopeEvent) {

    }
}

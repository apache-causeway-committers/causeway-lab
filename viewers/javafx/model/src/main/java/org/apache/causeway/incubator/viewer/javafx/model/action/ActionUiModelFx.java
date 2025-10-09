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
package org.apache.causeway.incubator.viewer.javafx.model.action;

import org.apache.causeway.core.metamodel.interactions.managed.ManagedAction;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.incubator.viewer.javafx.model.context.UiContextFx;
import org.apache.causeway.viewer.commons.model.action.HasManagedAction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

@RequiredArgsConstructor(staticName = "of")
public class ActionUiModelFx
implements HasManagedAction {

    private final UiContextFx uiContext;

    @Getter
    private final ManagedAction managedAction;

    @Override
    public ObjectAction getAction() {
        return managedAction.getMetaModel();
    }

    public MenuItem createMenuUiComponent() {
        var menuItem = new MenuItem(getManagedAction().getFriendlyName());

        return uiContext.getIconDecoratorForMenuItem()
                .decorate(menuItem, lookupFontAwesomeLayers(false));
    }

    public Node createRegularUiComponent() {
        var uiLabel = new Label(getManagedAction().getFriendlyName());

        return uiContext.getIconDecoratorForLabeled()
                .decorate(uiLabel, lookupFontAwesomeLayers(false));
    }

}

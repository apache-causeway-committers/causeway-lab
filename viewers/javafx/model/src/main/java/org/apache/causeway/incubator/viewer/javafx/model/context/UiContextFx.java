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
package org.apache.causeway.incubator.viewer.javafx.model.context;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.causeway.applib.services.iactnlayer.InteractionService;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.incubator.viewer.javafx.model.action.ActionUiModelFactoryFx;
import org.apache.causeway.incubator.viewer.javafx.model.events.JavaFxViewerConfig;
import org.apache.causeway.viewer.commons.model.decorators.DisablingDecorator;
import org.apache.causeway.viewer.commons.model.decorators.IconDecorator;
import org.apache.causeway.viewer.commons.model.decorators.PrototypingDecorator;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;

public interface UiContextFx {

    JavaFxViewerConfig getJavaFxViewerConfig();

    InteractionService getInteractionService();
    ActionUiModelFactoryFx getActionUiModelFactory();

    void setNewPageHandler(Consumer<Node> onNewPage);
    void setPageFactory(Function<ManagedObject, Node> pageFactory);

    void route(ManagedObject object);
    void route(Supplier<ManagedObject> objectSupplier);

    // -- DECORATORS

    IconDecorator<Labeled, Labeled> getIconDecoratorForLabeled();
    IconDecorator<MenuItem, MenuItem> getIconDecoratorForMenuItem();

    DisablingDecorator<Button> getDisablingDecoratorForButton();
    DisablingDecorator<Node> getDisablingDecoratorForFormField();

    PrototypingDecorator<Button, Node> getPrototypingDecoratorForButton();
    PrototypingDecorator<Node, Node> getPrototypingDecoratorForFormField();




}

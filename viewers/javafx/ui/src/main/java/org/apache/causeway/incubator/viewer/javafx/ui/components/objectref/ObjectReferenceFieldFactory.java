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
package org.apache.causeway.incubator.viewer.javafx.ui.components.objectref;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedParameter;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedProperty;
import org.apache.causeway.incubator.viewer.javafx.ui.components.UiComponentHandlerFx;
import org.apache.causeway.viewer.commons.model.components.UiComponentFactory.ComponentRequest;

import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import lombok.val;

@org.springframework.stereotype.Component
@jakarta.annotation.Priority(PriorityPrecedence.MIDPOINT)
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ObjectReferenceFieldFactory implements UiComponentHandlerFx {

    @Override
    public boolean isHandling(final ComponentRequest request) {
        return request.getFeatureTypeSpec().isEntityOrViewModelOrAbstract()
                || request.getFeatureType().isEnum();
    }

    @Override
    public Node handle(final ComponentRequest request) {

        //TODO 1) move all the logic that is in the request to the underlying ManagedProperty
        // 2) pass the ManagedProperty over with the request object
        // 3) design for an API to bind a ManagedProperty to a FormField, also make sure this works
        // with Vaadin's FormLayout/Field API
//        val textValue = request.getFeatureValue(String.class)
//                .orElse("");

        val uiComponent = new Label(request.getManagedFeature().getIdentifier().toString());

        if(request.getManagedFeature() instanceof ManagedParameter) {

            val managedParameter = (ManagedParameter)request.getManagedFeature();

//            uiComponent.textProperty().
//
//            managedParameter.validate(proposedValue)

            //TODO bind to parameter model

        } else if(request.getManagedFeature() instanceof ManagedProperty) {

            val managedProperty = (ManagedProperty)request.getManagedFeature();
            //TODO bind to property model
        }

        return uiComponent;
    }


}

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
package org.apache.causeway.incubator.viewer.javafx.ui.components.other;

import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.incubator.viewer.javafx.ui.components.UiComponentHandlerFx;
import org.apache.causeway.viewer.commons.model.components.UiComponentFactory.ComponentRequest;

import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.val;

@org.springframework.stereotype.Component
@jakarta.annotation.Priority(PriorityPrecedence.LAST)
public class FallbackFieldFactory implements UiComponentHandlerFx {

    @Override
    public boolean isHandling(ComponentRequest request) {
        return true; // the last handler in the chain
    }

    @Override
    public Node handle(ComponentRequest request) {

        val spec = request.getFeatureTypeSpec();
        return new Label(spec.getCorrespondingClass().getSimpleName() + " type not handled");
    }

}

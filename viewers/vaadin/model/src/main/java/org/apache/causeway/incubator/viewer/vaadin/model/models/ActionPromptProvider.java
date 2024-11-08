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
package org.apache.causeway.incubator.viewer.vaadin.model.models;

import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.services.metamodel.BeanSort;
import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.Page;

public interface ActionPromptProvider {

    public static ActionPromptProvider getFrom(final Component component) {
        final Page page = component.getPage();
        if(page == null) {
            throw new IllegalArgumentException("Programming error: component must be added to a page "
                    + "in order to locate the ActionPromptProvider");
        }
        return getFrom(page);
    }

    public static ActionPromptProvider getFrom(final Page page) {
        if(page instanceof ActionPromptProvider) {
            return (ActionPromptProvider) page;
        }
        // else
        throw new IllegalArgumentException("Programming error: all pages should inherit from PageAbstract, "
                + "which serves as the ActionPromptProvider");
    }

    public ActionPrompt getActionPrompt(
            final PromptStyle promptStyle,
            final BeanSort sort);

    void closePrompt(final AjaxRequestTarget target);
}

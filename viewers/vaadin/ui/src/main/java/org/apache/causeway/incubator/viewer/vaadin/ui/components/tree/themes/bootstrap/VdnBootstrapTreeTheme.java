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
package org.apache.causeway.incubator.viewer.vaadin.ui.components.tree.themes.bootstrap;

import org.apache.causeway.wicketstubs.api.Behavior;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.CssHeaderItem;
import org.apache.causeway.wicketstubs.api.CssResourceReference;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;

public class VdnBootstrapTreeTheme extends Behavior {
    private static final long serialVersionUID = 1L;

    private static final CssResourceReference CSS =
            new CssResourceReference(Behavior.class, "wkt-tree-theme.css");

    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        tag.append("class", "tree-theme-bootstrap", " ");
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(CSS));
    }
}

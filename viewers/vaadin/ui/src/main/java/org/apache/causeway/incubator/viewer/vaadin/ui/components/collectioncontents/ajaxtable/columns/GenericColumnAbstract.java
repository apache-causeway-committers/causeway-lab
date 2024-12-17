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
package org.apache.causeway.incubator.viewer.vaadin.ui.components.collectioncontents.ajaxtable.columns;

import org.apache.causeway.applib.services.i18n.TranslationContext;
import org.apache.causeway.core.metamodel.context.HasMetaModelContext;
import org.apache.causeway.core.metamodel.tabular.DataRow;
import org.apache.causeway.incubator.viewer.vaadin.ui.ComponentFactory;
import org.apache.causeway.incubator.viewer.vaadin.ui.app.registry.ComponentFactoryRegistry;
import org.apache.causeway.incubator.viewer.vaadin.ui.app.registry.HasComponentFactoryRegistry;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.collectioncontents.ajaxtable.CollectionContentsAsAjaxTablePanel;
import org.apache.causeway.viewer.commons.model.components.UiComponentType;
import org.apache.causeway.wicketstubs.Application;
import org.apache.causeway.wicketstubs.Model;
import org.apache.causeway.wicketstubs.api.AbstractColumn;
import org.apache.causeway.wicketstubs.api.AjaxFallbackDefaultDataTable;
import org.apache.causeway.wicketstubs.api.IModel;

import lombok.val;

/**
 * Represents a {@link AbstractColumn} within a
 * {@link AjaxFallbackDefaultDataTable}.
 *
 * <p>
 * Part of the implementation of {@link CollectionContentsAsAjaxTablePanel}.
 */
public abstract class GenericColumnAbstract
        extends AbstractColumn<DataRow, String>
        implements GenericColumn, HasMetaModelContext {
    private static final long serialVersionUID = 1L;

    private transient ComponentFactoryRegistry componentRegistry;

    protected GenericColumnAbstract(
            final String columnName) {
        this(Model.of(columnName), null);
    }

    protected GenericColumnAbstract(
            final IModel<String> columnNameModel,
            final String sortColumn) {
        super(columnNameModel, sortColumn);
    }

    protected ComponentFactory findComponentFactory(final UiComponentType uiComponentType, final IModel<?> model) {
        return getComponentRegistry().findComponentFactory(uiComponentType, model);
    }

    protected ComponentFactoryRegistry getComponentRegistry() {
        if (componentRegistry == null) {
            val componentFactoryRegistryAccessor = (HasComponentFactoryRegistry) Application.get();
            componentRegistry = componentFactoryRegistryAccessor.getComponentFactoryRegistry();
        }
        return componentRegistry;
    }

    protected String translate(final String raw) {
        return getMetaModelContext().getTranslationService().translate(TranslationContext.empty(), raw);
    }

}

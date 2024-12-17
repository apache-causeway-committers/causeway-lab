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

import java.util.Optional;

import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.incubator.viewer.vaadin.model.models.EntityCollectionModel;
import org.apache.causeway.incubator.viewer.vaadin.ui.ComponentFactory;
import org.apache.causeway.incubator.viewer.vaadin.ui.app.registry.ComponentFactoryRegistry;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.collectioncontents.ajaxtable.CollectionContentsAsAjaxTablePanel;
import org.apache.causeway.wicketstubs.api.IModel;

/**
 * A {@link GenericColumnAbstract column} within a
 * {@link CollectionContentsAsAjaxTablePanel} representing a single property of the
 * provided {@link ManagedObject}.
 *
 * <p>
 * Looks up the {@link ComponentFactory} to render the property from the
 * {@link ComponentFactoryRegistry}.
 */
public abstract class AssociationColumnAbstract
        extends GenericColumnAbstract {

    private static final long serialVersionUID = 1L;

    protected final EntityCollectionModel.Variant collectionVariant;
    protected final String memberId;
    protected final String parentTypeName;
    protected final String describedAs;

    public AssociationColumnAbstract(
            final EntityCollectionModel.Variant collectionVariant,
            final IModel<String> columnNameModel,
            /**
             * If empty, sorting is disabled for this column.
             * <p>
             * Not every column (e.g. as mapped to an association) is sortable.
             * The referenced type must implement {@link Comparable}.
             */
            final Optional<String> sortProperty,
            final String memberId,
            final String parentTypeName,
            final Optional<String> describedAs) {

        super(columnNameModel, sortProperty.orElse(null));
        this.collectionVariant = collectionVariant;
        this.memberId = memberId;
        this.parentTypeName = parentTypeName;
        this.describedAs = describedAs.orElse(null);
    }

}

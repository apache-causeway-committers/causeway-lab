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
package org.apache.causeway.incubator.viewer.vaadin.model.models.interction.coll;

import org.apache.causeway.applib.Identifier;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedAction;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedCollection;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.core.metamodel.spec.feature.OneToManyAssociation;
import org.apache.causeway.core.metamodel.tabular.DataTableInteractive;
import org.apache.causeway.core.metamodel.tabular.DataTableMemento;
import org.apache.causeway.incubator.viewer.vaadin.model.models.interction.BookmarkedObjectVdn;
import org.apache.causeway.incubator.viewer.vaadin.model.models.interction.HasBookmarkedOwnerAbstract;
import org.apache.causeway.viewer.commons.model.object.HasUiParentObject;
import org.apache.causeway.viewer.commons.model.object.UiObject;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;

/**
 * Bound to a BookmarkedObjectVdn, with the {@link DataTableInteractive}
 * representing either a <i>Collection</i> or an <i>Action</i>'s result.
 *
 * @implSpec the state of the DataTableModel is held transient,
 * that means it does not survive a serialization/de-serialization cycle;
 * it is recreated on load
 * @see HasBookmarkedOwnerAbstract
 */
public class DataTableModelVdn
        extends HasBookmarkedOwnerAbstract<DataTableInteractive>
        implements
        HasUiParentObject<UiObject> {

    // -- FACTORIES

    public static DataTableModelVdn forActionModel(
            final @NonNull BookmarkedObjectVdn bookmarkedObjectModel,
            final @NonNull ObjectAction actMetaModel,
            final @NonNull ManagedObject actionResult) {

        val managedAction = ManagedAction
                .of(bookmarkedObjectModel.getObject(), actMetaModel, Where.NOT_SPECIFIED);
        val tableInteractive = DataTableInteractive.forAction(
                managedAction,
                actionResult);
        return new DataTableModelVdn(
                bookmarkedObjectModel, actMetaModel.getFeatureIdentifier(), tableInteractive);
    }

    public static @NonNull DataTableModelVdn forCollection(
            final @NonNull BookmarkedObjectVdn bookmarkedObjectModel,
            final @NonNull OneToManyAssociation collMetaModel) {

        val tableInteractive = DataTableInteractive.forCollection(
                ManagedCollection
                        .of(bookmarkedObjectModel.getObject(), collMetaModel, Where.NOT_SPECIFIED));
        return new DataTableModelVdn(
                bookmarkedObjectModel, collMetaModel.getFeatureIdentifier(), tableInteractive);
    }

    // -- CONSTRUCTION

    private static final long serialVersionUID = 1L;

    @Getter private final Identifier featureIdentifier;
    private final DataTableMemento tableMemento;

    private DataTableModelVdn(
            final BookmarkedObjectVdn bookmarkedObject,
            final Identifier featureIdentifier,
            final DataTableInteractive tableInteractive) {
        super(bookmarkedObject);
        this.featureIdentifier = featureIdentifier;
        this.tableMemento = tableInteractive.createMemento();
        setObject(tableInteractive); // memoize
        tableMemento.setupBindings(tableInteractive);
    }

    // --

    @Override
    public UiObject getParentUiModel() {
        return ()->super.getBookmarkedOwner();
    }

    @Override
    protected DataTableInteractive load() {
        val tableInteractive = tableMemento.getDataTableModel(getBookmarkedOwner());
        tableMemento.setupBindings(tableInteractive);
        return tableInteractive;
    }

}

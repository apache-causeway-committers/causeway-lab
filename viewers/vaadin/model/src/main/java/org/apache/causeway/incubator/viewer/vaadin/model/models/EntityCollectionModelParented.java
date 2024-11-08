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

import org.apache.causeway.applib.layout.component.CollectionLayoutData;
import org.apache.causeway.applib.services.bookmark.Bookmark;
import org.apache.causeway.core.metamodel.spec.feature.OneToManyAssociation;
import org.apache.causeway.incubator.viewer.vaadin.model.hints.UiHintContainer;
import org.apache.causeway.incubator.viewer.vaadin.model.models.interction.coll.DataTableModelVdn;
import org.apache.causeway.wicketstubs.api.Component;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;

public class EntityCollectionModelParented
        extends EntityCollectionModelAbstract
        implements UiHintContainer {

    private static final long serialVersionUID = 1L;

    // TODO parent object model, maybe should not be exposed
    // maybe could be resolved in the process of decoupling the ActionModel from Wicket
    @Getter private final @NonNull UiObjectVdn entityModel;
    @Getter private final @NonNull CollectionLayoutData layoutData;

    // -- FACTORIES

    public static EntityCollectionModelParented forParentObjectModel(
            final @NonNull UiObjectVdn entityModel, final @NonNull CollectionLayoutData layoutData) {

        val coll = entityModel
                .getTypeOfSpecification()
                .getCollectionElseFail(layoutData.getId()); // collection's member-id

        val dataTableModel = DataTableModelVdn
                .forCollection(entityModel.bookmarkedObjectModel(), coll);

        return new EntityCollectionModelParented(
                dataTableModel,
                entityModel,
                layoutData);
    }

    // -- CONSTRUCTOR

    protected EntityCollectionModelParented(
            final @NonNull DataTableModelVdn delegate,
            final @NonNull UiObjectVdn parentObjectModel,  //TODO maybe instead use the delegate (?)
            final @NonNull CollectionLayoutData layoutData) {
        super(delegate, Variant.PARENTED);
        this.entityModel = parentObjectModel;
        this.layoutData = layoutData;
    }

    // -- UI HINT CONTAINER

    public static final String HINT_KEY_SELECTED_ITEM = "selectedItem";

    @Override
    public String getHint(final Component component, final String attributeName) {
        return getEntityModel().getHint(component, attributeName);
    }

    @Override
    public void setHint(final Component component, final String attributeName, final String attributeValue) {
        getEntityModel().setHint(component, attributeName, attributeValue);
    }

    @Override
    public void clearHint(final Component component, final String attributeName) {
        getEntityModel().clearHint(component, attributeName);
    }

    @Override
    public OneToManyAssociation getMetaModel() {
        return (OneToManyAssociation) super.getMetaModel();
    }

    @Override
    public void setObject(Object object) {

    }

    public Bookmark asHintingBookmark() {
        return (Bookmark) entityModel.getOwnerBookmark();
    }

}

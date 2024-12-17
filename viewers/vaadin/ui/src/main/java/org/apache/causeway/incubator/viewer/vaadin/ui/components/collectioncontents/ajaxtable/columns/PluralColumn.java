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

import java.io.Serializable;
import java.util.Optional;

import org.apache.causeway.incubator.viewer.vaadin.model.models.EntityCollectionModel;
import org.apache.causeway.wicketstubs.api.IModel;

import lombok.Builder;
import lombok.experimental.Accessors;

public final class PluralColumn
        extends AssociationColumnAbstract {

    private static final long serialVersionUID = 1L;

    @lombok.Value
    @Builder
    @Accessors(fluent = true)
    public static class RenderOptions implements Serializable {
        private static final long serialVersionUID = 1L;
        @Builder.Default final int titleAbbreviationThreshold = 50;
        @Builder.Default final int maxElements = 5;
        @Builder.Default final boolean isRenderEmptyBadge = true;
    }

    private final RenderOptions opts;

    public PluralColumn(
            final EntityCollectionModel.Variant collectionVariant,
            final IModel<String> columnNameModel,
            final String propertyId,
            final String parentTypeName,
            final Optional<String> describedAs,
            final RenderOptions opts) {
        super(collectionVariant, columnNameModel,
                Optional.empty(), // empty sortProperty (hence never sortable)
                propertyId, parentTypeName, describedAs);
        this.opts = opts;
    }

}

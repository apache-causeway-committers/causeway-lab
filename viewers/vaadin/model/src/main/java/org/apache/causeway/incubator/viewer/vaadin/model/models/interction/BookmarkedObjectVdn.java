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
package org.apache.causeway.incubator.viewer.vaadin.model.models.interction;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.services.bookmark.Bookmark;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.incubator.viewer.vaadin.model.models.ModelAbstract;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;

public final class BookmarkedObjectVdn
        extends ModelAbstract<ManagedObject> {

    private static final long serialVersionUID = 1L;
    @Getter private final @NonNull Bookmark bookmark;

    /**
     * overwrites any current cache entry, only safe when no other views/models reference the same ManagedObject
     */
    public static BookmarkedObjectVdn ofAdapter(
            final @NonNull ManagedObject domainObject) {
        val bookmark = domainObject.getBookmarkElseFail();
        return new BookmarkedObjectVdn(bookmark, domainObject);
    }

    public static BookmarkedObjectVdn ofBookmark(final @Nullable Bookmark bookmark) {
        return new BookmarkedObjectVdn(bookmark);
    }

    private BookmarkedObjectVdn(final @NonNull Bookmark bookmark) {
        super();
        this.bookmark = bookmark;
    }

    private BookmarkedObjectVdn(
            final @NonNull Bookmark bookmark,
            final @Nullable ManagedObject domainObject) {
        super(domainObject);
        this.bookmark = bookmark;
    }

    public final ManagedObject asManagedObject() {
        val entityOrViewmodel = super.getObject();
        return entityOrViewmodel;
    }

    @Override
    public final void setObject(final Object object) {
        throw _Exceptions.unsupportedOperation("ManagedObjectWkt is immuatable");
    }

    @Override
    protected final ManagedObject load() {
        val adapter = getMetaModelContext().getObjectManager().loadObjectElseFail(bookmark);
        return adapter;
    }

}


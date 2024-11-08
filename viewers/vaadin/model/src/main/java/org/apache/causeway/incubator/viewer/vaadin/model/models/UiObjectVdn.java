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

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.causeway.incubator.viewer.vaadin.model.models.interction.BookmarkedObjectVdn;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.exceptions.unrecoverable.ObjectNotFoundException;
import org.apache.causeway.applib.fa.FontAwesomeLayers;
import org.apache.causeway.applib.services.bookmark.Bookmark;
import org.apache.causeway.applib.services.hint.HintStore;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.collections._Maps;
import org.apache.causeway.core.metamodel.commons.ViewOrEditMode;
import org.apache.causeway.core.metamodel.consent.InteractionInitiatedBy;
import org.apache.causeway.core.metamodel.facets.object.icon.ObjectIcon;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.object.ManagedObjects;
import org.apache.causeway.core.metamodel.spec.feature.MixedIn;
import org.apache.causeway.core.metamodel.spec.feature.OneToOneAssociation;
import org.apache.causeway.core.metamodel.spec.feature.memento.PropertyMemento;
import org.apache.causeway.incubator.viewer.vaadin.model.hints.UiHintContainer;
//import org.apache.causeway.incubator.viewer.vaadin.model.models.interction.BookmarkedObjectVdn;
import org.apache.causeway.incubator.viewer.vaadin.model.models.interction.HasBookmarkedOwnerAbstract;
import org.apache.causeway.incubator.viewer.vaadin.model.models.interction.prop.PropertyInteractionVdn;
import org.apache.causeway.incubator.viewer.vaadin.model.util.ComponentHintKey;
import org.apache.causeway.incubator.viewer.vaadin.model.util.PageParameterUtils;
import org.apache.causeway.viewer.commons.model.hints.RenderingHint;
import org.apache.causeway.viewer.commons.model.object.UiObject;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.PageParameters;
import org.apache.causeway.wicketstubs.api.ResourceReference;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Synchronized;
import lombok.val;
import lombok.extern.log4j.Log4j2;

/**
 * Backing model to represent a domain object as {@link ManagedObject}.
 */
@Log4j2
public class UiObjectVdn
        extends HasBookmarkedOwnerAbstract<ManagedObject>
        implements
        UiObject,
        ObjectAdapterModel,
        UiHintContainer,
        BookmarkableModel {

    private static final long serialVersionUID = 1L;

    // -- FACTORIES

    public static UiObjectVdn ofPageParameters(
            final PageParameters pageParameters) {
        val bookmark = PageParameterUtils.toBookmark(pageParameters).orElse(null);
        return ofBookmark(bookmark);
    }

    public static UiObjectVdn ofAdapter(
            final @Nullable ManagedObject adapter) {
        return new UiObjectVdn(BookmarkedObjectVdn.ofAdapter(adapter),
                ViewOrEditMode.VIEWING, RenderingHint.REGULAR);
    }

    public static UiObjectVdn ofAdapterForCollection(
            final ManagedObject adapter,
            final @NonNull EntityCollectionModel.Variant variant) {
        return new UiObjectVdn(BookmarkedObjectVdn.ofAdapter(adapter),
                ViewOrEditMode.VIEWING, variant.getTitleColumnRenderingHint());
    }


    public static UiObjectVdn ofBookmark(
            final @Nullable Bookmark bookmark) {
        return new UiObjectVdn(BookmarkedObjectVdn.ofBookmark(bookmark),
                ViewOrEditMode.VIEWING, RenderingHint.REGULAR);
    }

    // -- CONSTRUCTORS

    /**
     * As used by TreeModel (same as {@link #ofAdapter(ManagedObject)}
     */
    protected UiObjectVdn(
            final ManagedObject adapter) {
        this(BookmarkedObjectVdn.ofAdapter(adapter),
                ViewOrEditMode.VIEWING, RenderingHint.REGULAR);
    }

    private UiObjectVdn(
            final @NonNull BookmarkedObjectVdn bookmarkedObject,
            final ViewOrEditMode viewOrEditMode,
            final RenderingHint renderingHint) {
        super(bookmarkedObject);
        this.viewOrEditMode = viewOrEditMode;
        this.renderingHint = renderingHint;
    }

    @Override
    protected ManagedObject load() {
        return super.getBookmarkedOwner();
    }

    // -- BOOKMARKABLE MODEL

    @Override
    public PageParameters getPageParameters() {
        return _HintPageParameterSerializer
                .hintStoreToPageParameters(
                        hintStore(),
                        getPageParametersWithoutUiHints(),
                        getOwnerBookmark());
    }

    @Override
    public PageParameters getPageParametersWithoutUiHints() {
        return PageParameterUtils.createPageParametersForObject(getBookmarkedOwner());
    }

    @Override
    public boolean isInlinePrompt() {
        return false;
    }

    // -- HINT SUPPORT

    @Getter(onMethod = @__(@Override))
    @Setter(onMethod = @__(@Override))
    private ViewOrEditMode viewOrEditMode;

    @Getter(onMethod = @__(@Override))
    private RenderingHint renderingHint;

    @Override
    public String getHint(final Component component, final String keyName) {
        final ComponentHintKey componentHintKey = ComponentHintKey.create(super.getMetaModelContext(), component, keyName);
        if (componentHintKey != null) {
            return componentHintKey.get(getOwnerBookmark());
        }
        return null;
    }

    @Override
    public void setHint(final Component component, final String keyName, final String hintValue) {
        ComponentHintKey componentHintKey = ComponentHintKey.create(super.getMetaModelContext(), component, keyName);
        componentHintKey.set(getOwnerBookmark(), hintValue);
    }

    @Override
    public void clearHint(final Component component, final String attributeName) {
        setHint(component, attributeName, null);
    }

    // -- OTHER OBJECT SPECIFIC

    @Override
    public String getTitle() {
        return getManagedObject().getTitle();
    }

    @Override
    public Either<ObjectIcon, FontAwesomeLayers> getIcon() {
        return getManagedObject().eitherIconOrFaLayers();
    }

    public Either<ResourceReference, FontAwesomeLayers> getIconAsResourceReference() {
        return getIcon()
                .mapLeft(objectIcon ->
                        imageResourceCache().resourceReferenceForObjectIcon(objectIcon));
    }

    @Override
    public ManagedObject getManagedObject() {
        return getObject();
    }

    // -- PROPERTY MODELS (CHILDREN)

    private transient Map<PropertyMemento, ScalarPropertyModel> propertyScalarModels;

    private Map<PropertyMemento, ScalarPropertyModel> propertyScalarModels() {
        if (propertyScalarModels == null) {
            propertyScalarModels = _Maps.<PropertyMemento, ScalarPropertyModel>newHashMap();
        }
        return propertyScalarModels;
    }

    /**
     * Lazily populates with the current value of each property.
     */
    public ScalarModel getPropertyModel(
            final OneToOneAssociation property,
            final ViewOrEditMode viewOrEdit,
            final RenderingHint renderingHint) {

        val bookmarkedObjectModel = bookmarkedObjectModel();

        //[CAUSEWAY-3532] guard against (owner entity) object deleted/not-found
        //
        // due to the lazy nature of the underlying model,
        // (that is loading entities only if required),
        // this guard only triggers, once the first property model gets looked up;
        // in other words: this guard only works if every entity has at least a property
        val ownerPojo = bookmarkedObjectModel.asManagedObject()
                .getPojo();
        if (ownerPojo == null) {
            throw new ObjectNotFoundException(
                    bookmarkedObjectModel.getBookmark().getIdentifier());
        }

        val pm = property.getMemento();
        val propertyScalarModels = propertyScalarModels();
        final ScalarModel existingScalarModel = propertyScalarModels.get(pm);
        if (existingScalarModel != null) {
            return existingScalarModel;
        }

        val propertyInteractionModel = new PropertyInteractionVdn(
                bookmarkedObjectModel,
                pm.getIdentifier().getMemberLogicalName(),
                renderingHint.asWhere());

        final long modelsAdded = propertyInteractionModel.streamPropertyUiModels()
                .map(uiModel -> ScalarPropertyModel.wrap(uiModel, viewOrEdit, renderingHint))
                .peek(scalarModel -> log.debug("adding: {}", scalarModel))
                .filter(scalarModel -> propertyScalarModels.put(pm, scalarModel) == null)
                .count(); // consume the stream

        // future extensions might allow to add multiple UI models per single property model (typed tuple support)
        _Assert.assertEquals(1L, modelsAdded, () ->
                String.format("unexpected number of propertyScalarModels added %d", modelsAdded));

        return propertyScalarModels.get(pm);
    }

    @Override
    public Stream<Bookmark> streamPropertyBookmarks() {
        val candidateAdapter = this.getObject();

        return candidateAdapter.getSpecification()
                .streamProperties(MixedIn.EXCLUDED)
                .map(prop ->
                        ManagedObjects.bookmark(prop.get(candidateAdapter, InteractionInitiatedBy.PASS_THROUGH))
                                .orElse(null)
                )
                .filter(_NullSafe::isPresent);
    }

    // -- VIEW OR EDIT

    @Override
    public UiObjectVdn toEditingMode() {
        //noop for objects
        return this;
    }

    @Override
    public UiObjectVdn toViewingMode() {
        //noop for objects
        return this;
    }

    // -- DETACH

    @Override
    protected void onDetach() {
        propertyScalarModels().values()
                .forEach(ScalarPropertyModel::detach);
        super.onDetach();
        propertyScalarModels = null;
    }

    // -- TAB AND COLUMN (metadata if any)

    @Setter
    private @Nullable Bookmark contextBookmarkIfAny;

    @Override
    @Synchronized
    @Deprecated // this check should be made available with 'core' models - and not modeled here
    public boolean isContextAdapter(final ManagedObject other) {
        return contextBookmarkIfAny == null
                ? false
                : Objects.equals(contextBookmarkIfAny, other.getBookmark().orElse(null))
                ;
    }

    // -- HELPER

    private transient HintStore hintStore;

    private HintStore hintStore() {
        return hintStore = getMetaModelContext().loadServiceIfAbsent(HintStore.class, hintStore);
    }

    private transient ImageResourceCache imageResourceCache;

    private ImageResourceCache imageResourceCache() {
        return imageResourceCache = getMetaModelContext().loadServiceIfAbsent(ImageResourceCache.class, imageResourceCache);
    }

}

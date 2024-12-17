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
package org.apache.causeway.incubator.viewer.vaadin.ui.components.collection.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jakarta.mail.Message;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.config.CausewayConfiguration;
import org.apache.causeway.core.metamodel.tabular.DataTableInteractive;
import org.apache.causeway.incubator.viewer.vaadin.model.links.Menuable;
import org.apache.causeway.incubator.viewer.vaadin.model.models.EntityCollectionModel;
import org.apache.causeway.incubator.viewer.vaadin.model.util.ComponentHintKey;
import org.apache.causeway.incubator.viewer.vaadin.ui.CollectionContentsAsFactory;
import org.apache.causeway.incubator.viewer.vaadin.ui.app.registry.ComponentFactoryKey;
import org.apache.causeway.incubator.viewer.vaadin.ui.panels.PanelAbstract;
import org.apache.causeway.viewer.commons.model.components.UiComponentType;

import lombok.NonNull;
import lombok.val;

/**
 * Provides a list of links for selecting other views that support
 * {@link UiComponentType#COLLECTION_CONTENTS} with a backing
 * {@link EntityCollectionModel}.
 */
public class CollectionPresentationSelectorPanel
        extends PanelAbstract<DataTableInteractive, EntityCollectionModel> {

    private static final long serialVersionUID = 1L;

    private static final String ID_VIEWS = "views";
    private static final String ID_VIEW_LIST = "viewList";
    private static final String ID_VIEW_LINK = "viewLink";
    private static final String ID_VIEW_ITEM = "viewItem";
    private static final String ID_VIEW_ITEM_TITLE = "viewItemTitle";
    private static final String ID_VIEW_ITEM_ICON = "viewItemIcon";
    private static final String ID_VIEW_ITEM_CHECKMARK = "viewItemCheckmark"; // indicator for the selected item
    private static final String ID_VIEW_BUTTON_ICON = "viewButtonIcon";
    private static final String ID_SECTION_SEPARATOR = "sectionSeparator";
    private static final String ID_SECTION_LABEL = "sectionLabel";


    private final CollectionPresentationSelectorHelper selectorHelper;
    private final ComponentHintKey componentHintKey;

    private ComponentFactoryKey selectedComponentFactory;

    public CollectionPresentationSelectorPanel(
            final String id,
            final EntityCollectionModel model) {
        this(id, model, ComponentHintKey.noop());
    }

    public CollectionPresentationSelectorPanel(
            final String id,
            final EntityCollectionModel model,
            final ComponentHintKey componentHintKey) {
        super(id, model);
        this.componentHintKey = componentHintKey;

        selectorHelper = new CollectionPresentationSelectorHelper(
                model, getComponentFactoryRegistry(), componentHintKey);
    }

    /**
     * Build UI only after added to parent.
     */
    @Override
    public void onInitialize() {
        super.onInitialize();
        addDropdown();
    }

    private void addDropdown() {

    }

    private void setOutputMarkupId(boolean b) {
    }

    /**
     * Sorts given CollectionContentsAsFactory(s) by their orderOfAppearanceInUiDropdown,
     * in order of discovery otherwise.
     *
     * @see CollectionContentsAsFactory#orderOfAppearanceInUiDropdown()
     */
    private List<Menuable> sorted(final Can<ComponentFactoryKey> componentFactories) {
        val presentations = sorted(componentFactories, _Util.filterTablePresentations());
        val exports = sorted(componentFactories, _Util.filterTableExports());
        val sortedWithSeparators = new ArrayList<Menuable>(
                presentations.size() + exports.size() + 2); // heap optimization, not strictly required

        boolean needsSpacer = false;

        if (!presentations.isEmpty()) {
            sortedWithSeparators.add(Menuable.sectionLabel(translate("Presentations")));
            sortedWithSeparators.addAll(presentations);
            needsSpacer = true;
        }
        if (!exports.isEmpty()) {
            if (needsSpacer) {
                sortedWithSeparators.add(Menuable.sectionSeparator());
            }
            sortedWithSeparators.add(Menuable.sectionLabel(translate("Exports")));
            sortedWithSeparators.addAll(exports);
        }

        return sortedWithSeparators;
    }

    private List<LinkEntry> sorted(
            final Can<ComponentFactoryKey> componentFactories,
            final Predicate<? super ComponentFactoryKey> filter) {
        final List<LinkEntry> sorted = componentFactories.stream()
                .filter(filter)
                .sorted(_Util.orderByOrderOfAppearanceInUiDropdown())
                .map((final ComponentFactoryKey factory) -> LinkEntry.linkEntry(factory))
                .collect(Collectors.toList());
        return sorted;
    }

    private Message getPage() {
        return null;
    }

    // -- UTILITY

    @Override
    public CausewayConfiguration.Viewer.Common getCommonViewerSettings() {
        return super.getCommonViewerSettings();
    }

    @lombok.Value
    static class LinkEntry implements Menuable {
        private static final long serialVersionUID = 1L;

        // -- FACTORIES

        public static LinkEntry linkEntry(final @NonNull ComponentFactoryKey componentFactoryKey) {
            return new LinkEntry(componentFactoryKey);
        }

        // -- CONSTRUCTION

        final ComponentFactoryKey componentFactoryKey;

        // -- PREDICATES

        // -- HELPER

        @Override
        public Kind menuableKind() {
            return Kind.LINK;
        }
    }

}




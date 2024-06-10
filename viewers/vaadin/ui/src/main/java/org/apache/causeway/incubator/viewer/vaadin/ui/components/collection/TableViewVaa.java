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
package org.apache.causeway.incubator.viewer.vaadin.ui.components.collection;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.title.TitleService;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.causeway.core.metamodel.spec.feature.OneToManyAssociation;
import org.apache.causeway.core.metamodel.tabular.interactive.DataColumn;
import org.apache.causeway.core.metamodel.tabular.interactive.DataRow;
import org.apache.causeway.core.metamodel.tabular.interactive.DataTableInteractive;
import org.apache.causeway.incubator.viewer.vaadin.model.context.UiContextVaa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class TableViewVaa extends VerticalLayout {

    private static final String NULL_LITERAL = "<NULL>";
    private final UiContextVaa uiContext;
    private final TitleService titleService;

    public static TableViewVaa empty(UiContextVaa uiContext, TitleService titleService) {
        return new TableViewVaa(uiContext, titleService);
    }

    /**
     * Constructs a (page-able) {@link Grid} from given {@code managedCollection}
     */
    public static Component forDataTableModel(
            final @NonNull UiContextVaa uiContext,
            final @NonNull TitleService titleService,
            final @NonNull DataTableInteractive dataTableModel,
            //TODO not used yet (or is redundant)
            final @NonNull Where where
    ) {
        return dataTableModel.getElementCount() == 0
                ? empty(uiContext, titleService)
                : new TableViewVaa(dataTableModel, uiContext, titleService);
    }

    private static String createTableId(final String str) {
        // replace all non-alphanumeric characters with '-'
        return str.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase();
    }

    private TableViewVaa(
            final @NonNull DataTableInteractive dataTableModel,
            final @NonNull UiContextVaa uiContext,
            final @NonNull TitleService titleService
    ) {
        this.uiContext = uiContext;
        this.titleService = titleService;

        setId("table-view-" + createTableId(dataTableModel.getTitle().getValue()));
        setSizeFull();

        val searchField = new TextField() {{
            setPrefixComponent(VaadinIcon.SEARCH.create());
            setPlaceholder("Search");
            setClearButtonVisible(true);
            setValueChangeMode(ValueChangeMode.LAZY);
            setValueChangeTimeout(600);
            setWidthFull();
            // FIXME bind to view state??
        }};
        add(searchField);
        val objectGrid = new Grid<DataRow>() {{
            setId("grid" + createTableId(dataTableModel.getTitle().getValue()));
            setColumnReorderingAllowed(true);
            setSelectionMode(SelectionMode.SINGLE);
            addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            addThemeVariants(GridVariant.LUMO_COMPACT);
        }};
        add(objectGrid);

        val rows = dataTableModel.getDataRowsFiltered().getValue();

        val columns = dataTableModel.getDataColumns().getValue();
        val gridCols = new ArrayList<Grid.Column<DataRow>>();

        // icon with link
        objectGrid.addComponentColumn(item -> {
                    Icon icon = new Icon();
                    icon.getElement().setAttribute("class", "fa fa-user");
                    icon.setColor("green"); //color seems to be overridden by link style
                    Anchor link = new Anchor("https://causeway.apache.org/", icon);
                    return link;
                })
                .setKey("icon")
                .setHeader("Icon");

        // property columns
        columns.forEach((DataColumn column) -> {
            val association = column.getAssociationMetaModel();
            association.getSpecialization().accept(
                    prop -> {
                        val col = objectGrid.addColumn(row ->
                                stringifyPropertyValue(prop, row.getRowElement())
                        );
                        col.setHeader(prop.getCanonicalFriendlyName());
                        col.setSortable(true);
                        gridCols.add(col);
                        //TODO add column description as is provided via property.getColumnDescription()
                    },
                    coll -> {
                        val col = objectGrid.addColumn(row -> {
                            val owningManagedObject = row.getRowElement();
                            val collectionManagedObject = coll.get(owningManagedObject);
                            @SuppressWarnings("unchecked")
                            val values = (Collection<Object>) collectionManagedObject.getPojo();
                            if (values.isEmpty()) {
                                return "";
                            } else {
                                return values.stream().map(titleService::titleOf).collect(Collectors.joining(", "));
                            }
                        });
                        col.setHeader(coll.getCanonicalFriendlyName());
                        gridCols.add(col);
                    });
        });

        // -- binding
        // populate the model
        val rowList = rows.toList();
        val dataview = objectGrid.setItems(rowList);

        dataview.addFilter(row -> {
            val filter = searchField.getValue();
            if (filter == null || filter.isEmpty()) {
                return true;
            }
            val lowerCaseRowCell = new ArrayList<String>();
            columns.forEach((DataColumn column) -> {
                val association = column.getAssociationMetaModel();
                association.getSpecialization().accept(
                        prop -> {
                            lowerCaseRowCell.add(stringifyPropertyValue(prop, row.getRowElement()).toLowerCase());
                        },
                        coll -> {
                            lowerCaseRowCell.add(stringifyCollectionValue(row, coll));
                        });
            });
            return lowerCaseRowCell.stream().anyMatch(cell -> cell.contains(filter.toLowerCase()));
        });
        searchField.addValueChangeListener(event -> dataview.refreshAll());

        objectGrid.recalculateColumnWidths();

        if (!gridCols.isEmpty()) {
            // TODO translate
            // TODO make customizable e.g. sum/count/avg/min/max
            gridCols.get(0).setFooter("Count: " + rowList.size());
        }

        // -- binding
        objectGrid.addItemClickListener(event -> {
            val row = event.getItem();
            uiContext.route(row.getRowElement());
        });
    }


    private String stringifyPropertyValue(
            final ObjectAssociation property,
            final ManagedObject targetObject
    ) {
        try {
            val propertyValue = property.get(targetObject);
            return propertyValue == null
                    ? NULL_LITERAL
                    : propertyValue.getTitle();
        } catch (Exception e) {
            log.debug("Failed to get property value for property {}", property.getId(), e);
            return Optional.ofNullable(e.getMessage()).orElse(e.getClass().getName());
        }
    }

    private String stringifyCollectionValue(DataRow row, OneToManyAssociation coll) {
        try {
            val owningManagedObject = row.getRowElement();
            val collectionManagedObject = coll.get(owningManagedObject);
            @SuppressWarnings("unchecked")
            val values = (Collection<Object>) collectionManagedObject.getPojo();
            if (values.isEmpty()) {
                return "";
            } else {
                return values.stream().map(titleService::titleOf).collect(Collectors.joining(", ")).toLowerCase();
            }
        } catch (Exception e) {
            log.debug("Failed to get property value for property {}", coll.getId(), e);
            return Optional.ofNullable(e.getMessage()).orElse(e.getClass().getName());
        }
    }

}

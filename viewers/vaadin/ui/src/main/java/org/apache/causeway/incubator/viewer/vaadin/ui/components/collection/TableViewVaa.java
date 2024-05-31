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

import java.util.ArrayList;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.causeway.core.metamodel.tabular.interactive.DataColumn;
import org.apache.causeway.core.metamodel.tabular.interactive.DataRow;
import org.apache.causeway.core.metamodel.tabular.interactive.DataTableInteractive;
import org.apache.causeway.incubator.viewer.vaadin.model.context.UiContextVaa;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class TableViewVaa extends VerticalLayout {

    private static final String NULL_LITERAL = "<NULL>";
    private final UiContextVaa uiContext;

    public static TableViewVaa empty(UiContextVaa uiContext) {
        return new TableViewVaa(uiContext);
    }

    /**
     * Constructs a (page-able) {@link Grid} from given {@code managedCollection}
     */
    public static Component forDataTableModel(
            final @NonNull UiContextVaa uiContext,
            final @NonNull DataTableInteractive dataTableModel,
            //TODO not used yet (or is redundant)
            final @NonNull Where where
    ) {
        return dataTableModel.getElementCount() == 0
                ? empty(uiContext)
                : new TableViewVaa(dataTableModel, uiContext);
    }

    private static String createTableId(final String str) {
        // replace all non-alphanumeric characters with '-'
        return str.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase();
    }

    private TableViewVaa(
            final @NonNull DataTableInteractive dataTableModel,
            final @NonNull UiContextVaa uiContext
    ) {
        this.uiContext = uiContext;
        setId("table-view-" + createTableId(dataTableModel.getTitle().getValue()));
        setSizeFull();
        //            final ComboBox<ManagedObject> listBox = new ComboBox<>();
        //            listBox.setLabel(label + " #" + objects.size());
        //            listBox.setItems(objects);
        //            if (!objects.isEmpty()) {
        //                listBox.setValue(objects.get(0));
        //            }
        //            listBox.setItemLabelGenerator(o -> o.titleString());

        val objectGrid = new Grid<DataRow>() {{
            setId("grid" + createTableId(dataTableModel.getTitle().getValue()));
            setColumnReorderingAllowed(true);
            setSelectionMode(SelectionMode.SINGLE);
            addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            //addThemeVariants(GridVariant.LUMO_COMPACT);
        }};
        add(objectGrid);

        val rows = dataTableModel.getDataRowsFiltered().getValue();

        val columns = dataTableModel.getDataColumns().getValue();
        val gridCols = new ArrayList<Grid.Column<DataRow>>();

        if(false) {
            // object link as first columnval gridCols = new ArrayList<Grid.Column<DataRow>>();
            val objectLinkCol = objectGrid.addColumn(row -> {
                // TODO provide icon with link
                return "obj. ref [" + row.getRowElement().getBookmark().orElse(null) + "]";
            });
            gridCols.add(objectLinkCol);
        }
        // property columns

        columns.forEach((DataColumn column) -> {
            val association = column.getAssociationMetaModel();
            association.getSpecialization().accept(
                    prop -> {
                        val col = objectGrid.addColumn(row -> {
                            log.debug("about to get property value for property {}", prop.getId());
                            return stringifyPropertyValue(prop, row.getRowElement());
                        });
                        col.setHeader(prop.getCanonicalFriendlyName());
                        col.setSortable(true);
                        gridCols.add(col);
                        //TODO add column description as is provided via property.getColumnDescription()
                    },
                    coll -> {
                        //TODO Causeway programming model changes: it is now allowed for table-cells to render collections (OneToMany Assoc.)

                    });
        });

        // populate the model
        val rowList = rows.toList();
        val dataview = objectGrid.setItems(rowList);
        objectGrid.recalculateColumnWidths();

        if(!gridCols.isEmpty()) {
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
            final ManagedObject targetObject) {
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


}

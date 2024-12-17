package org.apache.causeway.incubator.viewer.vaadin.ui.components.table;

import java.util.List;

import lombok.NonNull;

import org.apache.causeway.core.metamodel.spec.ObjectSpecification;
import org.apache.causeway.core.metamodel.tabular.DataRow;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.collectioncontents.ajaxtable.CollectionContentsSortableDataProvider;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.collectioncontents.ajaxtable.columns.GenericColumn;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.collectioncontents.ajaxtable.columns.ToggleboxColumn;

public class CausewayAjaxDataTable
        extends DataTableWithPagesAndFilter<DataRow, String> {
    public CausewayAjaxDataTable(
            String idTable,
            List<GenericColumn> columns,
            CollectionContentsSortableDataProvider dataProvider,
            int pageSize,
            ToggleboxColumn toggleboxColumn) {
        super();
    }

    public CausewayAjaxDataTable(@NonNull ObjectSpecification elementType) {
        super(elementType);
    }
}

package org.apache.causeway.incubator.viewer.vaadin.ui.components.table;

import org.apache.causeway.core.metamodel.spec.ObjectSpecification;
import org.apache.causeway.wicketstubs.api.DataTable;

import lombok.NonNull;

public abstract class DataTableWithPagesAndFilter<T, S>
        extends DataTable<T, S> {
    public DataTableWithPagesAndFilter(@NonNull ObjectSpecification elementType) {
        super();
    }

    public DataTableWithPagesAndFilter() {
    }
}

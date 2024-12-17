package org.apache.causeway.incubator.viewer.vaadin.ui.components.filter.table;

import org.apache.causeway.incubator.viewer.vaadin.ui.components.table.CausewayAjaxDataTable;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.table.DataTableWithPagesAndFilter;

import org.apache.causeway.wicketstubs.api.Panel;

public class FilterToolbar extends Panel {

    private static final long serialVersionUID = 1L;
    private static final String ID_TABLE_SEARCH_INPUT = "table-search-input";

    public FilterToolbar(final Object id, final DataTableWithPagesAndFilter<?, ?> table) {
//        super(id);
//        this.table = table;
    }

    public FilterToolbar(Object id, CausewayAjaxDataTable dataTableComponent) {
        super();
    }

}

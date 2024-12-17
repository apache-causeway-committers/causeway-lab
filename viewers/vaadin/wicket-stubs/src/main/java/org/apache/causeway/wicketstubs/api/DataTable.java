package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IColumn;
import org.apache.causeway.wicketstubs.IPageableItems;

import java.util.List;

public class DataTable<T, S> extends Panel implements IPageableItems {
    private final WebMarkupContainer body = null;
    private final List<? extends IColumn<T, S>> columns;

    public DataTable() {
        super();
//        super(elementType);
        columns = List.of();
    }

    public final WebMarkupContainer getBody() {
        return this.body;
    }

    public final List<? extends IColumn<T, S>> getColumns() {
        return this.columns;
    }
    
}

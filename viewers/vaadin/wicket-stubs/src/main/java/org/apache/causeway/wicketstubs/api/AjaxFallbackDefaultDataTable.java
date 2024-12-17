package org.apache.causeway.wicketstubs.api;

import lombok.NonNull;

import org.apache.causeway.core.metamodel.spec.ObjectSpecification;

public class AjaxFallbackDefaultDataTable<T, S> extends DataTable<T, S> {
    private static final long serialVersionUID = 1L;

    public AjaxFallbackDefaultDataTable(@NonNull ObjectSpecification elementType) {
        super();
    }
}
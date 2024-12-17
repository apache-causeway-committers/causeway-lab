package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IRequestListener;

public class Form<T>
        extends WebMarkupContainer
        implements IRequestListener, IGenericComponent<T, Form<T>> {
    public Form(String id) {
        super(id);
    }

    @Override
    public Component setDefaultModelObject(Object var1) {
        return null;
    }

    @Override
    public Object getDefaultModelObject() {
        return null;
    }
}

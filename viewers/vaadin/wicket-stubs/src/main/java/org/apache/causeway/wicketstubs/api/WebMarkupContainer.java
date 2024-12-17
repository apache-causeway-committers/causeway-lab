package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IConverter;

public class WebMarkupContainer
        extends MarkupContainer {

    public WebMarkupContainer(String id) {
        this(id, null);
    }

    public WebMarkupContainer(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    public <C> IConverter<C> getConverter(Class<C> type) {
        return null;
    }

    public void setOutputMarkupId(boolean b) {
    }
}

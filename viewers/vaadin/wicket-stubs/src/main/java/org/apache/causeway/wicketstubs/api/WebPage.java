package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IConverter;

public class WebPage
        extends Page {

    protected WebPage() {
        super();
    }

    protected void renderHead(IHeaderResponse response) {
    }

    protected void renderPage() {
    }

    @Override
    public <C> IConverter<C> getConverter(Class<C> type) {
        return null;
    }
}

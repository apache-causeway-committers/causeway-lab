package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.IHeaderResponse;

public class HeaderResponse
        implements IHeaderResponse {
    @Override
    public void render(CssReferenceHeaderItem cssReferenceHeaderItem) {
    }

    @Override
    public void render(Object o) {
    }

    public void markRendered(Object object) {
    }

    public boolean wasRendered(Object object) {
        return false;
    }

    @Override
    public Response getResponse() {
        return null;
    }

    public void close() {
    }

    @Override
    public boolean isClosed() {
        return false;
    }
}

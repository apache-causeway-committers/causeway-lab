package org.apache.causeway.wicketstubs;


import org.apache.causeway.wicketstubs.api.IHeaderResponse;

public abstract class DecoratingHeaderResponse implements IHeaderResponse {
    private final IHeaderResponse realResponse;

    public DecoratingHeaderResponse(IHeaderResponse real) {
        this.realResponse = real;
    }

    protected final IHeaderResponse getRealResponse() {
        return this.realResponse;
    }

    public void render(HeaderItem item) {
        this.realResponse.render(item);
    }

    public void markRendered(Object object) {
        this.realResponse.markRendered(object);
    }

    public boolean wasRendered(Object object) {
        return this.realResponse.wasRendered(object);
    }

    public Response getResponse() {
        return this.realResponse.getResponse();
    }

    public void close() {
        this.realResponse.close();
    }

    public boolean isClosed() {
        return this.realResponse.isClosed();
    }
}

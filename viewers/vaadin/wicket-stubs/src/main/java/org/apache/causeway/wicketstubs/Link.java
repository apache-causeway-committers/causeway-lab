package org.apache.causeway.wicketstubs;

public abstract class Link<O> {
    public Link(String id) {
    }

    public abstract void onClick();

    public abstract boolean rendersPage();

    protected abstract boolean getStatelessHint();

    protected abstract CharSequence getURL();

    protected void onRequest() {
    }
}

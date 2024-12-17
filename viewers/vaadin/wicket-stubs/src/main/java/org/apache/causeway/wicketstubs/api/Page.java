package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.AjaxRequestHandler;
import org.apache.causeway.wicketstubs.Application;
import org.apache.causeway.wicketstubs.IQueueRegion;
import org.apache.causeway.wicketstubs.IRequestablePage;

public abstract class Page
        extends MarkupContainer
        implements IRequestablePage, IQueueRegion {
    public Page() {
        super();
    }

    public PageParameters getPageParameters() {
        return null;
    }

    public void send(Application app, Broadcast broadcast, AjaxRequestHandler ajaxRequestHandler) {
    }

    public boolean wasRendered(Component resolvedComponent) {
        return false;
    }

    public void visitChildren(DeepChildFirstVisitor deepChildFirstVisitor) {

    }
}

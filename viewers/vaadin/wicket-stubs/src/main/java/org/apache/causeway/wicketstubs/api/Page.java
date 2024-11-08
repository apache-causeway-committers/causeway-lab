package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.AjaxRequestHandler;
import org.apache.causeway.wicketstubs.Application;
import org.apache.causeway.wicketstubs.IQueueRegion;
import org.apache.causeway.wicketstubs.IRequestablePage;

// see: org.apache.wicket.Page;
//FIXME
public abstract class Page
        extends MarkupContainer
        implements IRequestablePage, IQueueRegion {
    public Page(String id) {
        super(id);
    }

    public PageParameters getPageParameters() {
        return null;
    }

    public void send(Application app, Broadcast broadcast, AjaxRequestHandler ajaxRequestHandler) {
    }

    public boolean wasRendered(Component resolvedComponent) {
        return false;
    }

    public void componentRendered(Component component) {
    }

    public void componentAdded(Component child) {
    }

    public void componentRemoved(Component component) {
    }

    public void startComponentRender(Component component) {
    }

    public void endComponentRender(Component component) {
    }

    public boolean isPageStateless() {
        return false;
    }

    public void componentModelChanging(Component component) {
    }

    public Class<? extends MarkupContainer> getPageClass() {
        return null;
    }

    public void componentStateChanging(Component component) {
    }
}

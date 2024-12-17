package org.apache.causeway.wicketstubs;

import java.util.Collection;

import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.Page;

public class AjaxRequestHandler
        extends AbstractPartialPageRequestHandler
        implements AjaxRequestTarget {
    private final PartialPageUpdate update = null;

    public AjaxRequestHandler(Page page) {
        super(page);
    }

    public PartialPageUpdate getUpdate() {
        return this.update;
    }

    @Override
    public void add(Component var1, String var2) {
    }

    @Override
    public void add(Component... var1) {
    }

    @Override
    public void appendJavaScript(CharSequence var1) {
    }

    public final Collection<? extends Component> getComponents() {
        return this.update.getComponents();
    }

    public void detach(IRequestCycle requestCycle) {
    }

    public boolean equals(Object obj) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "AjaxRequestHandler";
    }

    @Override
    public void addListener(IListener var1) {

    }

    @Override
    public void registerRespondListener(ITargetRespondListener var1) {

    }

    @Override
    public String getLastFocusedElementId() {
        return "";
    }

    @Override
    public Page getPage() {
        return null;
    }

    public AjaxRequestTarget orElse(Object o) {
        return null;
    }

}


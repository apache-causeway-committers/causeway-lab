package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IRequestListener;

public abstract class AbstractAjaxBehavior
        extends Behavior
        implements IRequestListener {

    public AbstractAjaxBehavior() {
    }

    public final void bind(Component hostComponent) {
    }

    public final void onComponentTag(Component component, ComponentTag tag) {
        this.onComponentTag(tag);
    }

    public final void afterRender(Component hostComponent) {
        this.onComponentRendered();
    }

    protected void onComponentTag(ComponentTag tag) {
    }

    protected void onComponentRendered() {
    }

}

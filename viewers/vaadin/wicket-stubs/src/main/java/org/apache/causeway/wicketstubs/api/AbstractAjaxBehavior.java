package org.apache.causeway.wicketstubs.api;

import java.util.Iterator;
import java.util.List;

import org.apache.causeway.wicketstubs.IRequestListener;
import org.apache.causeway.wicketstubs.api.INamedParameters.Type;

public abstract class AbstractAjaxBehavior
        extends Behavior
        implements IRequestListener {
    private static final long serialVersionUID = 1L;
    private Component component;

    public AbstractAjaxBehavior() {
    }

    public final void bind(Component hostComponent) {
        Args.notNull(hostComponent, "hostComponent");
        if (this.component != null) {
            throw new IllegalStateException("this kind of handler cannot be attached to multiple components; it is already attached to component " + this.component + ", but component " + hostComponent + " wants to be attached too");
        } else {
            this.component = hostComponent;
            this.onBind();
        }
    }

    @lombok.SneakyThrows
    public CharSequence getCallbackUrl() {
        Component component = this.getComponent();
        if (component == null) {
            throw new IllegalArgumentException("Behavior must be bound to a component to create the URL");
        } else {
            PageParameters parameters = new PageParameters();
            PageParameters pageParameters = component.getPage().getPageParameters();
            List<INamedParameters.NamedPair> allNamedInPath = pageParameters.getAllNamedByType(Type.PATH);
            Iterator var5 = allNamedInPath.iterator();

            while (var5.hasNext()) {
                INamedParameters.NamedPair namedPair = (INamedParameters.NamedPair) var5.next();
                parameters.add(namedPair.getKey(), namedPair.getValue(), Type.PATH);
            }

            return this.getComponent().urlForListener(this, parameters);
        }
    }

    public final void onComponentTag(Component component, ComponentTag tag) {
        this.onComponentTag(tag);
    }

    public final void afterRender(Component hostComponent) {
        this.onComponentRendered();
    }

    protected final Component getComponent() {
        return this.component;
    }

    protected void onComponentTag(ComponentTag tag) {
    }

    protected void onBind() {
    }

    protected void onComponentRendered() {
    }

    public final void unbind(Component component) {
        this.onUnbind();
        this.component = null;
        super.unbind(component);
    }

    protected void onUnbind() {
    }
}

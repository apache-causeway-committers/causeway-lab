package org.apache.causeway.wicketstubs.api;

//org.apache.wicket.behavior.Behavior
//FIXME
public abstract class Behavior {
    public abstract void onComponentTag(final Component component, final ComponentTag tag);

    public void renderHead(final Component component, final IHeaderResponse response) {
    }

    public void onConfigure(Component component) {
    }

    protected void unbind(Component component) {
    }

    public boolean isEnabled(Component component) {
        return true;
    }

    public void onException(Component component, RuntimeException ex) {
    }

    public boolean getStatelessHint(Component component) {
        return false;
    }

    public void beforeRender(Component component) {
    }

    public void afterRender(Component component) {
    }

    public boolean isTemporary(Component component) {
        return false;
    }

    public void detach(Component component) {

    }
}

package org.apache.causeway.wicketstubs.api;

public abstract class Modal<T> extends GenericPanel<T> {
    public Modal(String markupId) {
        this(markupId, (IModel) null);
    }

    public Modal(String id, IModel<T> model) {
        super(id, model);
    }

    private Component getHeaderLabel() {
        return null;//FIXME
    }

    public abstract void setTitle(Component component, AjaxRequestTarget target);

    public abstract void setPanel(Component component, AjaxRequestTarget target);

    public abstract void showPrompt(AjaxRequestTarget target);

    public abstract void closePrompt(AjaxRequestTarget target);

    protected WebMarkupContainer createDialog(String id) {
        return null;
    }
}

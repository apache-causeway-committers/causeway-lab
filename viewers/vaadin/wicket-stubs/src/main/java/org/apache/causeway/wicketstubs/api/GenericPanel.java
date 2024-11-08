package org.apache.causeway.wicketstubs.api;

public abstract class GenericPanel<T>
        extends Panel
        implements IGenericComponent<T, GenericPanel<T>> {
    private static final long serialVersionUID = 1L;

    public GenericPanel(String id) {
        this(id, (IModel)null);
    }

    public GenericPanel(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    public IModel<?> getDefaultModel() {
        return null;
    }

    @Override
    public Component setDefaultModel(IModel<?> var1) {
        return null;
    }

    @Override
    public Component setDefaultModelObject(Object var1) {
        return null;
    }

    @Override
    public Object getDefaultModelObject() {
        return null;
    }

    public abstract void closePrompt(AjaxRequestTarget target);
}



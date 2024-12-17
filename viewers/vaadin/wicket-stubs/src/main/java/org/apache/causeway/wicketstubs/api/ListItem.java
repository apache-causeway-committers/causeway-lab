package org.apache.causeway.wicketstubs.api;

public class ListItem<T>
        extends LoopItem
        implements IGenericComponent<T, ListItem<T>> {
    private static final long serialVersionUID = 1L;

    public ListItem(String id, int index, IModel<T> model) {
        super(id, index, model);
    }

    public ListItem(int index, IModel<T> model) {
        super(index, model);
    }

    public ListItem(String id, int index) {
        super(id, index);
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
}
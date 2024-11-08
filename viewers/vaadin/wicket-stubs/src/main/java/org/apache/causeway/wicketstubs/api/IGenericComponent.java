package org.apache.causeway.wicketstubs.api;

public interface IGenericComponent<T, C extends IGenericComponent<? super T, ?>> {
    default IModel<T> getModel() {
        return (IModel<T>) this.getDefaultModel();
    }

    default C setModel(IModel<T> model) {
        this.setDefaultModel(model);
        return (C) this;
    }

    default T getModelObject() {
        return (T) this.getDefaultModelObject();
    }

    default C setModelObject(T object) {
        this.setDefaultModelObject(object);
        return (C) this;
    }

    IModel<?> getDefaultModel();

    Component setDefaultModel(IModel<?> var1);

    Component setDefaultModelObject(Object var1);

    Object getDefaultModelObject();
}

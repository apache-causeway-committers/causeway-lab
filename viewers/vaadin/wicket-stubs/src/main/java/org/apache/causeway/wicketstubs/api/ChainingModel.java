package org.apache.causeway.wicketstubs.api;

public class ChainingModel<T> implements IModel<T> {
    private Object target;

    public ChainingModel(Object modelObject) {
    }

    public void detach() {
    }

    public void setObject(Object object) {
    }

    public T getObject() {
        return (T) target;
    }

    public final Object getTarget() {
        return this.target;
    }

    public IModel<?> getChainedModel() {
        return this.target instanceof IModel ? (IModel)this.target : null;
    }

    public String toString() {
        return target.toString();
    }

}

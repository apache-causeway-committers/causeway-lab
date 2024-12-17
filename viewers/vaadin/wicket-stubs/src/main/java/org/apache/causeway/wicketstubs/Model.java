package org.apache.causeway.wicketstubs;

import java.io.Serializable;

import org.apache.causeway.wicketstubs.api.IModel;

public class Model<T extends Serializable>
//        implements IObjectClassAwareModel<T>
{
    private static final long serialVersionUID = 1L;
    private T object;

    public static IModel<String> of(Serializable label) {
        return null;
    }

    public static IModel<String> of() {
        return null;
    }

    public T getObject() {
        return this.object;
    }

    public void setObject(Object object) {
    }

    public void detach() {
    }
}

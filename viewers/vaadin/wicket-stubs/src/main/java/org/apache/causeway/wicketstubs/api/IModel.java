package org.apache.causeway.wicketstubs.api;

public interface IModel<T> {

    T getObject();

    void setObject(Object object);

    void detach();
}

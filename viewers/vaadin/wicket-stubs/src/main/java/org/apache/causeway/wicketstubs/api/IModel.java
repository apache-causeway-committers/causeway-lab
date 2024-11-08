package org.apache.causeway.wicketstubs.api;

//FIXME
public interface IModel<T> {

    T getObject();

    void setObject(Object object);

    void detach();
}

package org.apache.causeway.wicketstubs.api;

import java.io.Serializable;

import org.apache.causeway.wicketstubs.IDetachable;
import org.apache.causeway.wicketstubs.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingModel<T> implements IModel<T> {
    private static final Logger LOG = LoggerFactory.getLogger(ChainingModel.class);
    private Object target;

    public ChainingModel(Object modelObject) {
        if (modelObject instanceof Session) {
            LOG.warn("It is not a good idea to reference the Session instance in models directly as it may lead to serialization problems. If you need to access a property of the session via the model use the page instance as the model object and 'session.attribute' as the path.");
        } else if (modelObject != null && !(modelObject instanceof Serializable)) {
            LOG.warn("It is not a good idea to reference non-serializable {} in a model directly as it may lead to serialization problems.", modelObject.getClass());
        }

        this.target = modelObject;
    }

    public void detach() {
        if (this.target instanceof IDetachable) {
            ((IDetachable)this.target).detach();
        }

    }

    public void setObject(Object object) {
        if (this.target instanceof IModel) {
            ((IModel)this.target).setObject(object);
        } else {
            this.target = object;
        }

    }
//Cast to (T) added - FIXME?
    public T getObject() {
        return (T) (this.target instanceof IModel ? ((IModel)this.target).getObject() : this.target);
    }

    public final Object getTarget() {
        return this.target;
    }

    protected final ChainingModel<T> setTarget(Object modelObject) {
        this.target = modelObject;
        return this;
    }

    public IModel<?> getChainedModel() {
        return this.target instanceof IModel ? (IModel)this.target : null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Model:classname=[");
        sb.append(this.getClass().getName()).append(']');
        sb.append(":nestedModel=[").append(this.target).append(']');
        return sb.toString();
    }

    public final Object getInnermostModelOrObject() {
        Object object;
        Object tmp;
        for(object = this.getTarget(); object instanceof IModel; object = tmp) {
            tmp = ((IModel)object).getObject();
            if (tmp == object) {
                break;
            }
        }

        return object;
    }
}

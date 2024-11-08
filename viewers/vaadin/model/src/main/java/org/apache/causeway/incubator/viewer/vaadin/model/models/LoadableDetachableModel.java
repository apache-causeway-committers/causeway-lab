package org.apache.causeway.incubator.viewer.vaadin.model.models;

import java.util.Locale;

import com.vaadin.flow.function.SerializableSupplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.causeway.wicketstubs.api.IModel;

public abstract class LoadableDetachableModel<T> implements IModel<T> {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(LoadableDetachableModel.class);
    private transient InternalState state;
    private transient T transientModelObject;

    public LoadableDetachableModel() {
        this.state = LoadableDetachableModel.InternalState.DETACHED;
    }

    public LoadableDetachableModel(T object) {
        this.state = LoadableDetachableModel.InternalState.DETACHED;
        this.transientModelObject = object;
        this.state = LoadableDetachableModel.InternalState.ATTACHED;
    }

    public void detach() {
        if (this.state != null && this.state != LoadableDetachableModel.InternalState.DETACHED) {
            try {
                this.onDetach();
            } finally {
                this.state = LoadableDetachableModel.InternalState.DETACHED;
                this.transientModelObject = null;
                log.debug("removed transient object for '{}'", this);
            }
        }

    }

    public final T getObject() {
        if (this.state == null || this.state == LoadableDetachableModel.InternalState.DETACHED) {
            this.state = LoadableDetachableModel.InternalState.ATTACHING;
            this.transientModelObject = this.load();
            if (log.isDebugEnabled()) {
                log.debug("loaded transient object '{}' for '{}'", this.transientModelObject, this);
            }

            this.state = LoadableDetachableModel.InternalState.ATTACHED;
            this.onAttach();
        }

        return this.transientModelObject;
    }

    public final boolean isAttached() {
        return this.state == LoadableDetachableModel.InternalState.ATTACHED;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(":attached=").append(this.isAttached()).append(":transientModelObject=[").append(this.transientModelObject).append(']');
        return sb.toString();
    }

    protected abstract T load();

    protected void onAttach() {
    }

    protected void onDetach() {
    }

    public void setObject(Object object) {
        this.state = LoadableDetachableModel.InternalState.ATTACHED;
        this.transientModelObject = object;
    }

    public static <T> LoadableDetachableModel<T> of(final SerializableSupplier<T> getter) {
        return new LoadableDetachableModel<T>() {
            private static final long serialVersionUID = 1L;

            protected T load() {
                return getter.get();
            }
        };
    }

    private static enum InternalState {
        DETACHED,
        ATTACHING,
        ATTACHED;

        private InternalState() {
        }

        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}

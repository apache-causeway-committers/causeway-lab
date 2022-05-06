package org.apache.isis.lab.experiments.wktfcal.widgets.field.model;

import java.util.EnumSet;

import org.apache.wicket.model.IModel;
import org.springframework.lang.Nullable;

import org.apache.isis.lab.experiments.wktfcal.widgets.field.FieldPanel.FormatModifer;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;

public abstract class FieldModelAbstract<T>
extends FieldModelMappable<T> {

    private static final long serialVersionUID = 1L;

    protected FieldModelAbstract(
            @NonNull final Class<T> type,
            @Nullable final EnumSet<FormatModifer> formatModifers) {
        super(type, formatModifers!=null
                ? formatModifers
                : EnumSet.noneOf(FormatModifer.class));
    }

    protected abstract T getValueObject();
    protected abstract void setValueObject(T newValue);

    protected abstract T getPendingValueObject();
    protected abstract void setPendingValueObject(T newValue);

    protected abstract String validatePendingValue(T pendingValue);

    @Getter(lazy = true, onMethod_ = {@Override})
    private final IModel<T> value = new IModel<T>() {
        private static final long serialVersionUID = 1L;

        @Override
        public T getObject() {
            return getValueObject();
        }

        @Override
        public void setObject(final T object) {
            System.err.printf("value update %s%n", object);
            setValueObject(object);
        }

    };

    @Getter(lazy = true, onMethod_ = {@Override})
    private final IModel<T> pendingValue = new IModel<T>() {
        private static final long serialVersionUID = 1L;

        @Override
        public T getObject() {
            return getPendingValueObject();
        }

        @Override
        public void setObject(final T object) {
            System.err.printf("pendingValue update %s%n", object);
            setPendingValueObject(object);
        }

    };

    @Getter(lazy = true, onMethod_ = {@Override})
    private final IModel<String> validationFeedback = new IModel<String>() {
        private static final long serialVersionUID = 1L;
        @Override
        public String getObject() {
            val feedback = validatePendingValue(getPendingValueObject());
            System.err.printf("validate: %s%n", feedback);
            return feedback;
        }
    };

    @Override
    public void submitPendingValue() {
        getValue().setObject(getPendingValueObject());
    }

}

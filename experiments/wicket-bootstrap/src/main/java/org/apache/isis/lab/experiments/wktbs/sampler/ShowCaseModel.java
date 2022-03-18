package org.apache.isis.lab.experiments.wktbs.sampler;

import java.io.Serializable;
import java.util.EnumSet;

import org.apache.isis.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;
import org.apache.isis.lab.experiments.wktbs.widgets.field.model.FieldModel;
import org.apache.isis.lab.experiments.wktbs.widgets.field.model.FieldModelAbstract2;

import lombok.Data;
import lombok.Getter;

@Data
public class ShowCaseModel<T extends Serializable>
implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private final Class<T> type;
    private String title;
    private T valueObject;
    private EnumSet<FormatModifer> formatModifers;
    private boolean validationFeedbackEnabled;

    public void setValidationFeedbackEnabled(final boolean flag) {
        this.validationFeedbackEnabled = flag;
    }

    @Getter(lazy = true)
    private final FieldModel<T> fieldModel = createFieldModel();

    private FieldModel<T> createFieldModel() {
        return new FieldModelAbstract2<T>(getType(), formatModifers) {
            private static final long serialVersionUID = 1L;
            private T pendingValueObject = valueObject;

            @Override
            protected T getValueObject() {
                return valueObject;
            }

            @Override
            protected void setValueObject(final T newValue) {
                valueObject = newValue;
            }

            @Override
            protected T getPendingValueObject() {
                return pendingValueObject;
            }

            @Override
            protected void setPendingValueObject(final T newValue) {
                this.pendingValueObject = newValue;
            }

            @Override
            protected String validatePendingValue(final T pendingValue) {
                if(isValidationFeedbackEnabled()) {
                    return "invalid input";
                }
                return null;
            }

        };
    }
}

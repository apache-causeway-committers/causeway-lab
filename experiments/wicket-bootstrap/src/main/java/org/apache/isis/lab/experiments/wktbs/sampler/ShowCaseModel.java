package org.apache.isis.lab.experiments.wktbs.sampler;

import java.io.Serializable;
import java.util.EnumSet;

import org.apache.wicket.model.IModel;

import org.apache.isis.lab.experiments.wktbs.widgets.ScalarModel;
import org.apache.isis.lab.experiments.wktbs.widgets.ScalarModelAbstract;
import org.apache.isis.lab.experiments.wktbs.widgets.ScalarPanel.FormatModifer;

import lombok.Data;
import lombok.Getter;

@Data
public class ShowCaseModel<T extends Serializable>
implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<T> type;
    private String title;
    private T valueObject;
    private EnumSet<FormatModifer> formatModifers;
    private boolean validationFeedbackEnabled;

    public void setValidationFeedbackEnabled(final boolean flag) {
        this.validationFeedbackEnabled = flag;
    }

    public ScalarModel<T> getScalarModel() {

        return new ScalarModelAbstract<T>(type, formatModifers!=null
                ? formatModifers
                : EnumSet.noneOf(FormatModifer.class)) {

            private static final long serialVersionUID = 1L;
            private T pendingValueObject = valueObject;

            @Getter(lazy = true, onMethod_ = {@Override})
            private final IModel<T> value = new IModel<T>() {
                private static final long serialVersionUID = 1L;

                @Override
                public T getObject() {
                    return valueObject;
                }

                @Override
                public void setObject(final T object) {
                    System.err.printf("value update %s%n", object);
                    valueObject = object;
                }

            };

            @Getter(lazy = true, onMethod_ = {@Override})
            private final IModel<T> pendingValue = new IModel<T>() {
                private static final long serialVersionUID = 1L;

                @Override
                public T getObject() {
                    return pendingValueObject;
                }

                @Override
                public void setObject(final T object) {
                    System.err.printf("pendingValue update %s%n", object);
                    pendingValueObject = object;
                }

            };

            @Getter(lazy = true, onMethod_ = {@Override})
            private final IModel<String> validationFeedback = new IModel<String>() {
                private static final long serialVersionUID = 1L;
                @Override
                public String getObject() {
                    System.err.printf("validate: %s%n", validatePendingValue());
                    return validatePendingValue();
                }
            };

            @Override
            public String validatePendingValue() {
                if(pendingValueObject!=null
                        && isValidationFeedbackEnabled()) {
                    return "invalid input";
                }
                return null;
            }

            @Override
            public void submitPendingValue() {
                getValue().setObject(pendingValueObject);
            }

        };
    }



}

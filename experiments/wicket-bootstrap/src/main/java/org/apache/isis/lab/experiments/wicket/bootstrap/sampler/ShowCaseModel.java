package org.apache.isis.lab.experiments.wicket.bootstrap.sampler;

import java.io.Serializable;
import java.util.EnumSet;

import org.apache.wicket.model.IModel;

import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarModel;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarModelAbstract;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

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

    public ScalarModel<T> getScalarModel() {

        return new ScalarModelAbstract<T>(type, formatModifers!=null
                ? formatModifers
                : EnumSet.noneOf(FormatModifer.class)) {

            private static final long serialVersionUID = 1L;
            private T pendingValueObject = valueObject;
            private String validationFeedbackText = null;

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
                    return validationFeedbackText;
                }
            };

            @Override
            public String validatePendingValue() {
                if(pendingValueObject!=null
                        && pendingValueObject.toString().length()>5) {
                    return validationFeedbackText = "max char (5) violation";
                }
                return validationFeedbackText = null;
            }

            @Override
            public void submitPendingValue() {
                getValue().setObject(pendingValueObject);
            }

        };
    }

}

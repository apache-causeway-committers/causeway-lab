package org.apache.isis.lab.experiments.wicket.bootstrap.sampler;

import java.io.Serializable;

import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.FormFieldModel;

import lombok.Data;

@Data
public class ShowCaseModel<T extends Serializable>
implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<T> type;
    private String title;
    private T value;

    public FormFieldModel<T> getFormFieldModel() {

        return new FormFieldModel<T>() {


            private T pendingValue = value;

            @Override
            public T getValue() {
                return value;
            }

            @Override
            public T getPendingValue() {
                return pendingValue;
            }

            @Override
            public String validatePendingValue() {
                return null;
            }

            @Override
            public void submitPendingValue() {
                setValue(pendingValue);
            }
        };
    }

}

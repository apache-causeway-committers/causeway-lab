package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import java.io.Serializable;

public interface FormFieldModel<T> extends Serializable {

    T getValue();
    T getPendingValue();
    String validatePendingValue();
    void submitPendingValue();

}

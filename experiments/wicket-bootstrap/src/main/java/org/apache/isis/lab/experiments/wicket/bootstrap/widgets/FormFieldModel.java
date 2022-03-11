package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import java.io.Serializable;
import java.util.EnumSet;

import org.apache.wicket.model.IModel;

import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.FormFieldPanel.FormatModifer;

public interface FormFieldModel<T> extends Serializable {

    EnumSet<FormatModifer> getFormatModifers();

    IModel<T> getValue();
    IModel<T> getPendingValue();
    String validatePendingValue();
    IModel<String> getValidationFeedback();
    void submitPendingValue();

}

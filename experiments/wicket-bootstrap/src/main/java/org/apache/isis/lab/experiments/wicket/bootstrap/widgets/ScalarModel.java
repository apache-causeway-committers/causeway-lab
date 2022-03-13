package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import java.io.Serializable;
import java.util.EnumSet;

import org.apache.wicket.model.IModel;

import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

public interface ScalarModel<T> extends Serializable {

    EnumSet<FormatModifer> getFormatModifers();

    IModel<T> getValue();
    IModel<T> getPendingValue();
    String validatePendingValue();
    IModel<String> getValidationFeedback();
    void submitPendingValue();

}

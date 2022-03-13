package org.apache.isis.lab.experiments.wicket.bootstrap.sampler;

import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel;

import lombok.val;

public class ShowCasePanel extends Panel {

    private static final long serialVersionUID = 1L;

    public ShowCasePanel(
            final String id,
            final ShowCaseModel<?> model) {
        super(id, Model.of(model));

        add(new Label("showCaseTitle", model.getTitle()));
        addFormFieldPanel("scalarPanel", model);
    }

    protected ShowCaseModel<?> showCaseModel() {
        return (ShowCaseModel<?>) getDefaultModelObject();
    }

    private <T extends Serializable> void addFormFieldPanel(
            final String id,
            final ShowCaseModel<T> showCaseModel) {
        val formFieldPanel = new ScalarPanel<T>(id, showCaseModel.getScalarModel());
        add(formFieldPanel);
    }

}

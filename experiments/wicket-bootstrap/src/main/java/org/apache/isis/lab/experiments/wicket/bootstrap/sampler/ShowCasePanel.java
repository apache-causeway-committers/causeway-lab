package org.apache.isis.lab.experiments.wicket.bootstrap.sampler;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import org.apache.isis.lab.experiments.wicket.bootstrap.sampler.bool.BooleanDesign;
import org.apache.isis.lab.experiments.wicket.bootstrap.sampler.string.StringDesign;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel;

import lombok.val;

public class ShowCasePanel extends Panel {

    private static final long serialVersionUID = 1L;

    public ShowCasePanel(
            final String id,
            final ShowCaseModel<?> model) {
        super(id, Model.of(model));

        add(new Label("showCaseTitle", model.getTitle()));
        addScalarPanel("scalarPanel", model);
        addDesignPanel("designPanel", model);
    }

    protected ShowCaseModel<?> showCaseModel() {
        return (ShowCaseModel<?>) getDefaultModelObject();
    }

    private <T extends Serializable> void addScalarPanel(
            final String id,
            final ShowCaseModel<T> showCaseModel) {
        val scalarPanel = new ScalarPanel<T>(id, showCaseModel.getScalarModel());
        add(scalarPanel);
    }

    private <T extends Serializable> void addDesignPanel(
            final String id,
            final ShowCaseModel<T> showCaseModel) {

        switch (showCaseModel.getType().getSimpleName()) {
        case "String": {
            add(new StringDesign(id, showCaseModel.getScalarModel().getFormatModifers()));
            return;
        }
        case "boolean":
        case "Boolean": {
            add(new BooleanDesign(id, showCaseModel.getScalarModel().getFormatModifers()));
            return;
        }
        default:
            val container = new WebMarkupContainer(id);
            add(container);
        }

    }

}

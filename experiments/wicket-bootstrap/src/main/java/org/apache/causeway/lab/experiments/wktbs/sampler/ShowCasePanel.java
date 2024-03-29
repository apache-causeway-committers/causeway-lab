package org.apache.causeway.lab.experiments.wktbs.sampler;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;

import org.apache.causeway.lab.experiments.wktbs.sampler.bool.BooleanDesign;
import org.apache.causeway.lab.experiments.wktbs.sampler.datetime.DateTimeDesign;
import org.apache.causeway.lab.experiments.wktbs.sampler.file.FileDesign;
import org.apache.causeway.lab.experiments.wktbs.sampler.string.StringDesign;
import org.apache.causeway.lab.experiments.wktbs.widgets.field.FieldPanel;

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
        addActivateValidationFeedbackToggle("checkActivateValidationFeedback", model);
    }

    protected ShowCaseModel<?> showCaseModel() {
        return (ShowCaseModel<?>) getDefaultModelObject();
    }

    private <T extends Serializable> void addScalarPanel(
            final String id,
            final ShowCaseModel<T> showCaseModel) {
        val scalarPanel = new FieldPanel<T>(id, showCaseModel.getFieldModel());
        add(scalarPanel);
    }

    private <T extends Serializable> void addDesignPanel(
            final String id,
            final ShowCaseModel<T> showCaseModel) {

        switch (showCaseModel.getType().getSimpleName()) {
        case "String": {
            add(new StringDesign(id, showCaseModel.getFieldModel().getFormatModifers()));
            return;
        }
        case "boolean":
        case "Boolean": {
            add(new BooleanDesign(id, showCaseModel.getFieldModel().getFormatModifers()));
            return;
        }
        case "File": {
            add(new FileDesign(id, showCaseModel.getFieldModel().getFormatModifers()));
            return;
        }
        case "OffsetDateTime": {
            add(new DateTimeDesign(id, showCaseModel.getFieldModel().getFormatModifers()));
            return;
        }
        default:
            val container = new WebMarkupContainer(id);
            add(container);
        }

    }

    private void addActivateValidationFeedbackToggle(
            final String id,
            final ShowCaseModel<?> showCaseModel) {

        val cb = new AjaxCheckBox(id, LambdaModel.of(
                showCaseModel::isValidationFeedbackEnabled,
                showCaseModel::setValidationFeedbackEnabled)) {
            private static final long serialVersionUID = 1L;
            @Override protected void onUpdate(final AjaxRequestTarget target) {
                // no-op
            }
        };

        add(cb);
    }

}

package org.apache.isis.lab.experiments.wktbs.widgets;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.Strings;

import org.apache.isis.commons.internal.base._Casts;
import org.apache.isis.lab.experiments.wktbs.fragments.BootstrapFragment.ButtonGroupTemplate;
import org.apache.isis.lab.experiments.wktbs.fragments.BootstrapFragment.ButtonTemplate;
import org.apache.isis.lab.experiments.wktbs.fragments.BootstrapFragment.FeedbackTemplate;
import org.apache.isis.lab.experiments.wktbs.fragments.BootstrapFragment.InputTemplate;
import org.apache.isis.lab.experiments.wktbs.util.WktUtil;
import org.apache.isis.lab.experiments.wktbs.widgets.ScalarPanel.FormatModifer;

import lombok.val;

public class ScalarInputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    private FormComponent<T> formComponent;

    public ScalarInputPanel(
            final String id,
            final ScalarModel<T> scalarModel) {
        super(id, new ScalarModelHolder<>(scalarModel));

        val form = createForm("scalarInputForm");

        if(scalarModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
            formComponent = InputTemplate.TEXTAREA
                    .createComponent(form, this::createFormValueInputAsTextarea);
            val bg = ButtonGroupTemplate.RIGHT_BELOW_OUTSIDE.createRepeatingView(form);
            ButtonTemplate.SAVE_GROUPED.createFragment(bg);
            ButtonTemplate.CANCEL_GROUPED.createComponent(bg, this::createLinkToCancel);
        } else {
            formComponent = InputTemplate.TEXT
                    .createComponent(form, this::createFormValueInputAsText);
            val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(form);
            ButtonTemplate.SAVE_OUTLINED.createFragment(bg);
            ButtonTemplate.CANCEL_OUTLINED.createComponent(bg, this::createLinkToCancel);
        }

        FeedbackTemplate.DEFAULT.createComponent(form, this::createValidationFeedback);
    }

    @SuppressWarnings("unchecked")
    protected ScalarModel<T> scalarModel() {
        return (ScalarModel<T>) getDefaultModelObject();
    }

    protected ScalarPanel<T> scalarPanel() {
        return _Casts.uncheckedCast(getParent());
    }

    private MarkupContainer createForm(final String id) {
        val form = WktUtil.createForm(id, ScalarInputPanel.this::onInputSubmit);
        add(form);
        return form;
    }

    private FormComponent<T> createFormValueInputAsText(final String id) {
        val formComponent = new TextField<>(id, scalarModel().getPendingValue());
        return formComponent;
    }

    private FormComponent<T> createFormValueInputAsTextarea(final String id) {
        val formComponent = new TextArea<>(id, scalarModel().getPendingValue());
        return formComponent;
    }

    private Component createValidationFeedback(final String id) {
        val link = new Label(id, scalarModel().getValidationFeedback());
        return link;
    }

    private Component createLinkToCancel(final String id) {
        return WktUtil.createLink(id, this::switchToOutputFormat);
    }

    private void onInputSubmit() {
        val scalarModel = scalarModel();
        val feedback = scalarModel.validatePendingValue();
        if(Strings.isEmpty(feedback)) {
            scalarModel.submitPendingValue();
            switchToOutputFormat();
        } else {
            System.err.printf("submission vetoed %n");

            // show validation feedback
            formComponent.add(AttributeModifier.append("class", "is-invalid"));
        }
    }

    private void switchToOutputFormat() {
        val parent = ScalarInputPanel.this.scalarPanel();
        parent.setFormat(ScalarPanel.Format.OUTPUT);
    }

    private void switchToOutputFormat(final AjaxRequestTarget ajaxTarget) {
        val parent = ScalarInputPanel.this.scalarPanel();
        parent.setFormat(ScalarPanel.Format.OUTPUT);
        ajaxTarget.add(parent);
    }

}

package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.Strings;

import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.ButtonGroupTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.ButtonTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.FeedbackTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.InputTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

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
            val bg = ButtonGroupTemplate.RIGHT_BELOW.createFragment(form);
            ButtonTemplate.SAVE_GROUPED.createFragment(bg);
            ButtonTemplate.CANCEL_GROUPED.createComponent(bg, this::createLinkToCancel);
        } else {
            formComponent = InputTemplate.TEXT
                    .createComponent(form, this::createFormValueInputAsText);
            val bg = ButtonGroupTemplate.OUTLINE.createFragment(form);
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
        return (ScalarPanel<T>) getParent();
    }

    private MarkupContainer createForm(final String id) {
        val form = new Form<Void>(id){
            @Override
            protected void onSubmit() {
                final ScalarModel<T> scalarModel = scalarModel();
                val feedback = scalarModel.validatePendingValue();
                if(Strings.isEmpty(feedback)) {
                    scalarModel.submitPendingValue();
                    val parent = ScalarInputPanel.this.scalarPanel();
                    parent.setFormat(ScalarPanel.Format.OUTPUT);
                } else {
                    // TODO show validation feedback

                    formComponent.add(AttributeModifier.append("class", "is-invalid"));

                }

            }
        };
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

    private Component createLinkToCancel(final String id) {
        val link = new AjaxLink<Void>(id){
            @Override
            public void onClick(final AjaxRequestTarget target) {
                val parent = ScalarInputPanel.this.scalarPanel();
                parent.setFormat(ScalarPanel.Format.OUTPUT);
                target.add(parent);
            }
        };
        return link;
    }

    private Component createValidationFeedback(final String id) {
        val link = new Label(id, scalarModel().getValidationFeedback());
        return link;
    }
}

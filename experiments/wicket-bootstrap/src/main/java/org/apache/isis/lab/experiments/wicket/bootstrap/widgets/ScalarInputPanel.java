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

import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

public class ScalarInputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    @RequiredArgsConstructor
    static enum InputVariant implements FragmentHelper {
        FORM_VALUE_INPUT_AS_TEXT("formValueInput", "text"),
        FORM_VALUE_INPUT_AS_TEXTAREA("formValueInput", "textarea");
        @Getter private final String id;
        @Getter private final String variant;
    }

    @RequiredArgsConstructor
    static enum FragmentTemplate implements FragmentHelper {
        SUBMIT("linkToSave", "submit"),
        LINK_TO_SAVE("linkToSave", "default"),
        LINK_TO_CANCEL("linkToCancel", "default"),
        VALIDATION_FEEDBACK("validationFeedback", "default");
        @Getter private final String id;
        @Getter private final String variant;
    }

    private FormComponent<T> formComponent;

    public ScalarInputPanel(
            final String id,
            final ScalarModel<T> scalarModel) {
        super(id, new ScalarModelHolder<>(scalarModel));

        val form = createForm("scalarInputForm");

        val markupProvider = this;

        if(scalarModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
            formComponent = InputVariant.FORM_VALUE_INPUT_AS_TEXTAREA
                    .createComponent(markupProvider, form, this::createFormValueInputAsTextarea);
        } else {
            formComponent = InputVariant.FORM_VALUE_INPUT_AS_TEXT
                    .createComponent(markupProvider, form, this::createFormValueInputAsText);
        }

        FragmentTemplate.SUBMIT.createFragment(markupProvider, form);
        FragmentTemplate.LINK_TO_CANCEL.createComponent(markupProvider, form, this::createLinkToCancel);
        FragmentTemplate.VALIDATION_FEEDBACK.createComponent(markupProvider, form, this::createValidationFeedback);
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

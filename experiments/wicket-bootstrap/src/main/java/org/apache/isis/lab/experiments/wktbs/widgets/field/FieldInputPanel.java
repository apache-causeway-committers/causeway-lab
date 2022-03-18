package org.apache.isis.lab.experiments.wktbs.widgets.field;

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
import org.apache.isis.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;

import lombok.val;

public class FieldInputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    private FormComponent<T> formComponent;

    public FieldInputPanel(
            final String id,
            final FieldModel<T> fieldModel) {
        super(id, new FieldModelHolder<>(fieldModel));

        val form = createForm("scalarInputForm");

        if(fieldModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
            formComponent = InputTemplate.TEXTAREA
                    .createComponent(form, this::createFormValueInputAsTextarea);
            val bg = ButtonGroupTemplate.RIGHT_BELOW_OUTSIDE.createRepeatingView(form);
            ButtonTemplate.SAVE_GROUPED.createFragment(bg);
            ButtonTemplate.CANCEL_GROUPED.createComponent(bg, this::createLinkToCancel);
        } else {

            val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(form);
            ButtonTemplate.SAVE_OUTLINED.createFragment(bg);
            ButtonTemplate.CANCEL_OUTLINED.createComponent(bg, this::createLinkToCancel);

            if(fieldModel.isBoolean()){
                if(fieldModel.getFormatModifers().contains(FormatModifer.TRISTATE)) {
                    val triState = fieldModel().asTriState().getPendingValue().getObject();
                    if(triState==null) {
                        formComponent = InputTemplate.CHECK_INTERMEDIATE.createComponent(form, this::createInputAsCheckInactive);
                    } else if(triState) {
                        formComponent = InputTemplate.CHECK_CHECKED.createComponent(form, this::createInputAsCheckInactive);
                    } else {
                        formComponent = InputTemplate.CHECK_UNCHECKED.createComponent(form, this::createInputAsCheckInactive);
                    }
                } else {
                    val binaryState = fieldModel().asBinaryState().getPendingValue().getObject();
                    if(binaryState) {
                        formComponent = InputTemplate.CHECK_CHECKED.createComponent(form, this::createInputAsCheckInactive);
                    } else {
                        formComponent = InputTemplate.CHECK_UNCHECKED.createComponent(form, this::createInputAsCheckInactive);
                    }

                }
            } else {
                formComponent = InputTemplate.TEXT
                        .createComponent(form, this::createFormValueInputAsText);
            }
        }

        FeedbackTemplate.DEFAULT.createComponent(form, this::createValidationFeedback);
    }

    @SuppressWarnings("unchecked")
    protected FieldModel<T> fieldModel() {
        return (FieldModel<T>) getDefaultModelObject();
    }

    protected FieldPanel<T> fieldPanel() {
        return _Casts.uncheckedCast(getParent());
    }

    private MarkupContainer createForm(final String id) {
        val form = WktUtil.createForm(id, FieldInputPanel.this::onInputSubmit);
        add(form);
        return form;
    }

    private FormComponent<T> createFormValueInputAsText(final String id) {
        val formComponent = new TextField<>(id, fieldModel().getPendingValue());
        return formComponent;
    }

    private FormComponent<T> createFormValueInputAsTextarea(final String id) {
        val formComponent = new TextArea<>(id, fieldModel().getPendingValue());
        return formComponent;
    }

    private FormComponent<T> createInputAsCheckInactive(final String id) {
        val formComponent = new FormComponent<T>(id, fieldModel().getPendingValue()) {
            private static final long serialVersionUID = 1L;
        };
        return formComponent;
    }

    private Component createValidationFeedback(final String id) {
        val label = new Label(id, fieldModel().getValidationFeedback());
        return label;
    }

    private Component createLinkToCancel(final String id) {
        return WktUtil.createLink(id, this::switchToOutputFormat);
    }

    private void onInputSubmit() {
        val scalarModel = fieldModel();
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
        val parent = FieldInputPanel.this.fieldPanel();
        parent.setFormat(FieldPanel.Format.OUTPUT);
    }

    private void switchToOutputFormat(final AjaxRequestTarget ajaxTarget) {
        val parent = FieldInputPanel.this.fieldPanel();
        parent.setFormat(FieldPanel.Format.OUTPUT);
        ajaxTarget.add(parent);
    }

}

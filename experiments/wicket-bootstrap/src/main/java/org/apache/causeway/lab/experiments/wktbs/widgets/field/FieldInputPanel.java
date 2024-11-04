package org.apache.causeway.lab.experiments.wktbs.widgets.field;

import java.io.Serializable;

import org.apache.causeway.commons.internal.base._Casts;
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

import org.apache.causeway.lab.experiments.wktbs.fragments.BootstrapFragment.ButtonGroupTemplate;
import org.apache.causeway.lab.experiments.wktbs.fragments.BootstrapFragment.ButtonTemplate;
import org.apache.causeway.lab.experiments.wktbs.fragments.BootstrapFragment.FeedbackTemplate;
import org.apache.causeway.lab.experiments.wktbs.fragments.BootstrapFragment.InputTemplate;
import org.apache.causeway.lab.experiments.wktbs.fragments.FragmentMapper;
import org.apache.causeway.lab.experiments.wktbs.util.WktUtil;
import org.apache.causeway.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;
import org.apache.causeway.lab.experiments.wktbs.widgets.field.model.FieldModel;

import lombok.val;

public class FieldInputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    @FunctionalInterface
    public static interface FormComponentFactory<T> extends Serializable {
        FormComponent<T> createFormComponent(MarkupContainer container, FieldModel<T> fieldModel);
    }

    static class FormComponentFactoryBoolean implements FormComponentFactory<Boolean> {

        private static final long serialVersionUID = 1L;

        @Override
        public FormComponent<Boolean> createFormComponent(
                final MarkupContainer form,
                final FieldModel<Boolean> fieldModel) {

            val booleanModel = fieldModel.getFormatModifers().contains(FormatModifer.TRISTATE)
                    ? fieldModel.asTriState()
                    : fieldModel.asBinaryState();
            val booleanFixedPendingValue = booleanModel.getPendingValue().getObject();

            final FragmentMapper fragmentMapper;
            if(booleanFixedPendingValue==null) {
                fragmentMapper = InputTemplate.CHECK_INTERMEDIATE;
            } else if(booleanFixedPendingValue) {
                fragmentMapper = InputTemplate.CHECK_CHECKED;
            } else {
                fragmentMapper = InputTemplate.CHECK_UNCHECKED;
            }

            return fragmentMapper.createComponent(form,
                    id->WktUtil.createBooleanFormComponentWithFixedValue(id, booleanFixedPendingValue));
        }

    }

    static class FormComponentFactoryString implements FormComponentFactory<String> {

        private static final long serialVersionUID = 1L;

        @Override
        public FormComponent<String> createFormComponent(
                final MarkupContainer form,
                final FieldModel<String> fieldModel) {

            if(fieldModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
                return InputTemplate.TEXTAREA
                        .createComponent(form, id->new TextArea<>(id, fieldModel.getPendingValue()));
            } else {
                return InputTemplate.TEXT
                        .createComponent(form, id->new TextField<>(id, fieldModel.getPendingValue()));
            }

        }

    }

    private FormComponent<T> formComponent;

    public FieldInputPanel(
            final String id,
            final FieldModel<T> fieldModel) {
        super(id, new FieldModelHolder<>(fieldModel));

        val form = createForm("scalarInputForm");

        if(fieldModel.isBoolean()){
            formComponent = (FormComponent<T>) new FormComponentFactoryBoolean()
                    .createFormComponent(form, (FieldModel<Boolean>) fieldModel);
        } else {
            formComponent = (FormComponent<T>) new FormComponentFactoryString()
                    .createFormComponent(form, (FieldModel<String>) fieldModel);
        }

        FeedbackTemplate.DEFAULT.createComponent(form, this::createValidationFeedback);

        if(fieldModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
            val bg = ButtonGroupTemplate.RIGHT_BELOW_OUTSIDE.createRepeatingView(form);
            ButtonTemplate.SAVE_GROUPED.createFragment(bg);
            ButtonTemplate.CANCEL_GROUPED.createComponent(bg, this::createLinkToCancel);
        } else {
            val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(form);
            ButtonTemplate.SAVE_OUTLINED.createFragment(bg);
            ButtonTemplate.CANCEL_OUTLINED.createComponent(bg, this::createLinkToCancel);
        }

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

    private Component createValidationFeedback(final String id) {
        val label = new Label(id, fieldModel().getValidationFeedback());
        return label;
    }

    private Component createLinkToCancel(final String id) {
        return WktUtil.createLink(id, this::switchToOutputFormat);
    }

    private void onInputSubmit() {
        val fieldModel = fieldModel();
        val feedback = fieldModel.getValidationFeedback().getObject();
        if(Strings.isEmpty(feedback)) {
            fieldModel.submitPendingValue();
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

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

import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.TemplateMapper;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.ButtonGroupTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.ButtonTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.FeedbackTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.InputTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

public class ScalarInputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    @RequiredArgsConstructor
    static enum InputVariant implements TemplateMapper {
        TEXT(InputTemplate.TEXT),
        TEXTAREA(InputTemplate.TEXTAREA);
        @Getter private final InputTemplate template;
    }

    @RequiredArgsConstructor
    static enum FeedbackVariant implements TemplateMapper {
        DEFAULT(FeedbackTemplate.VALIDATION_FEEDBACK);
        @Getter private final FeedbackTemplate template;
    }

    @RequiredArgsConstructor
    static enum ButtonGroupVariant implements TemplateMapper {
        OUTLINE(ButtonGroupTemplate.OUTLINE),
        RIGHT_BELOW(ButtonGroupTemplate.RIGHT_BELOW);
        @Getter private final ButtonGroupTemplate template;
    }

    @RequiredArgsConstructor
    static enum SaveButtonVariant implements TemplateMapper {
        OUTLINE(ButtonTemplate.SAVE_OUTLINED),
        RIGHT_BELOW(ButtonTemplate.SAVE_GROUPED);
        @Getter private final ButtonTemplate template;
    }

    @RequiredArgsConstructor
    static enum CancelButtonVariant implements TemplateMapper {
        OUTLINE(ButtonTemplate.CANCEL_OUTLINED),
        RIGHT_BELOW(ButtonTemplate.CANCEL_GROUPED);
        @Getter private final ButtonTemplate template;
    }

    private FormComponent<T> formComponent;

    public ScalarInputPanel(
            final String id,
            final ScalarModel<T> scalarModel) {
        super(id, new ScalarModelHolder<>(scalarModel));

        val form = createForm("scalarInputForm");

        if(scalarModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
            formComponent = InputVariant.TEXTAREA
                    .createComponent(form, this::createFormValueInputAsTextarea);
            val bg = ButtonGroupVariant.RIGHT_BELOW.createFragment(form);
            SaveButtonVariant.RIGHT_BELOW.createFragment(bg);
            CancelButtonVariant.RIGHT_BELOW.createComponent(bg, this::createLinkToCancel);
        } else {
            formComponent = InputVariant.TEXT
                    .createComponent(form, this::createFormValueInputAsText);
            val bg = ButtonGroupVariant.OUTLINE.createFragment(form);
            SaveButtonVariant.OUTLINE.createFragment(bg);
            CancelButtonVariant.OUTLINE.createComponent(bg, this::createLinkToCancel);
        }

        FeedbackVariant.DEFAULT.createComponent(form, this::createValidationFeedback);
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

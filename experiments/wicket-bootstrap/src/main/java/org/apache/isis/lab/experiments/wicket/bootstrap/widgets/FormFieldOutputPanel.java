package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

public class FormFieldOutputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    @RequiredArgsConstructor
    static enum OutputVariant implements FragmentHelper {
        FORM_VALUE_OUTPUT_AS_LABEL("formValueOutput", "label");
        @Getter private final String id;
        @Getter private final String variant;
    }

    @RequiredArgsConstructor
    static enum FragmentTemplate implements FragmentHelper {
        LINK_TO_EDIT("linkToEdit", "default"),
        LINK_TO_COPY("linkToCopy", "default");
        @Getter private final String id;
        @Getter private final String variant;
    }


    public FormFieldOutputPanel(final String id, final FormFieldModel<T> formFieldModel) {
        super(id, new FormFieldModelHolder<>(formFieldModel));

        OutputVariant.FORM_VALUE_OUTPUT_AS_LABEL.createComponent(this, this::createFormValueOutputAsLabel);

        FragmentTemplate.LINK_TO_EDIT.createComponent(this, this::createLinkToEdit);
        FragmentTemplate.LINK_TO_COPY.createComponent(this, this::createLinkToCopy);
    }

    @SuppressWarnings("unchecked")
    protected FormFieldModel<T> formFieldModel() {
        return (FormFieldModel<T>) getDefaultModelObject();
    }

    protected FormFieldPanel<T> formFieldPanel() {
        return (FormFieldPanel<T>) getParent();
    }

    private Component createFormValueOutputAsLabel(final String id) {
        return new Label("formValueOutput", formFieldModel().getValue());
    }

    private Component createLinkToEdit(final String id) {
        val link = new AjaxLink<Void>(id){
            @Override
            public void onClick(final AjaxRequestTarget target) {
                val parent = FormFieldOutputPanel.this.formFieldPanel();
                parent.setFormat(FormFieldPanel.Format.INPUT);
                target.add(parent);
            }
        };
        return link;
    }

    private Component createLinkToCopy(final String id) {
        val link = new AjaxLink<Void>(id){
            @Override
            public void onClick(final AjaxRequestTarget target) {
                //TODO copy value to clipboard
            }
        };
        return link;
    }

}

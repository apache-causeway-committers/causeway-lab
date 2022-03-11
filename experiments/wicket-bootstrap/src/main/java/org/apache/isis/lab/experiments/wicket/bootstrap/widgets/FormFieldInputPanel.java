package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LambdaModel;

import lombok.val;

public class FormFieldInputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public FormFieldInputPanel(final String id, final FormFieldModel<T> formFieldModel) {
        super(id, new FormFieldModelHolder<>(formFieldModel));

        addFormValueInputContainer("container-formValueInput");
        addLinkToSaveContainer("container-linkToSave");
        addLinkToCancelContainer("container-linkToCancel");
    }

    @SuppressWarnings("unchecked")
    protected FormFieldModel<T> formFieldModel() {
        return (FormFieldModel<T>) getDefaultModelObject();
    }

    protected FormFieldPanel<T> formFieldPanel() {
        return (FormFieldPanel<T>) getParent();
    }

    private void addFormValueInputContainer(final String id) {

        val formValueFragment = new Fragment(id, "fragment-formValueAsInputText", this);
        add(formValueFragment);

        formValueFragment.add(new TextField("formValueInput", LambdaModel.of(()->{
            return formFieldModel().getValue();
        })));
    }

    private void addLinkToSaveContainer(final String id) {

        val linkToEditFragment = new Fragment(id, "fragment-linkToSave", this);
        add(linkToEditFragment);

        val link = new AjaxLink<Void>("linkToSave"){
            @Override
            public void onClick(final AjaxRequestTarget target) {
//                val parent = FormFieldInputPanel.this.formFieldPanel();
//                parent.setFormat(FormFieldPanel.Format.OUTPUT);
//                target.add(parent);
            }
        };
        linkToEditFragment.add(link);

    }

    private void addLinkToCancelContainer(final String id) {

        val linkToCopyFragment = new Fragment(id, "fragment-linkToCancel", this);
        add(linkToCopyFragment);

        val link = new AjaxLink<Void>("linkToCancel"){
            @Override
            public void onClick(final AjaxRequestTarget target) {
                val parent = FormFieldInputPanel.this.formFieldPanel();
                parent.setFormat(FormFieldPanel.Format.OUTPUT);
                target.add(parent);
            }
        };
        linkToCopyFragment.add(link);


    }

}

package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LambdaModel;

import lombok.val;

public class FormFieldOutputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public FormFieldOutputPanel(final String id, final FormFieldModel<T> formFieldModel) {
        super(id, new FormFieldModelHolder<>(formFieldModel));
        addFormValueOutputContainer("container-formValueOutput");
        addLinkToEditContainer("container-linkToEdit");
        addLinkToCopyContainer("container-linkToCopy");
    }

    @SuppressWarnings("unchecked")
    protected FormFieldModel<T> formFieldModel() {
        return (FormFieldModel<T>) getDefaultModelObject();
    }

    protected FormFieldPanel<T> formFieldPanel() {
        return (FormFieldPanel<T>) getParent();
    }

    private void addFormValueOutputContainer(final String id) {
        val formValueFragment = new Fragment(id, "fragment-formValueAsOutputLabel", this);
        add(formValueFragment);

        formValueFragment.add(new Label("formValueOutput", LambdaModel.of(()->{
            return formFieldModel().getPendingValue();
        })));
    }

    private void addLinkToEditContainer(final String id) {
        val linkToEditFragment = new Fragment(id, "fragment-linkToEdit", this);
        add(linkToEditFragment);

        val link = new AjaxLink<Void>("linkToEdit"){
            @Override
            public void onClick(final AjaxRequestTarget target) {
                val parent = FormFieldOutputPanel.this.formFieldPanel();
                parent.setFormat(FormFieldPanel.Format.INPUT);
                target.add(parent);
            }
        };
        linkToEditFragment.add(link);
    }

    private void addLinkToCopyContainer(final String id) {
        val linkToCopyFragment = new Fragment(id, "fragment-linkToCopy", this);
        add(linkToCopyFragment);

        val link = new AjaxLink<Void>("linkToCopy"){
            @Override
            public void onClick(final AjaxRequestTarget target) {
                //modify the model of a label and refresh it on browser
//                label.setDefaultModelObject("Another value 4 label.");
//                target.add(label);
            }
        };
        linkToCopyFragment.add(link);
    }

}

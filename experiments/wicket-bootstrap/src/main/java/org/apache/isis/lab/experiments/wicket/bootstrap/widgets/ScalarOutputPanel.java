package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.ButtonGroupTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.ButtonTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.OutputTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

import lombok.val;

public class ScalarOutputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public ScalarOutputPanel(final String id, final ScalarModel<T> scalarModel) {
        super(id, new ScalarModelHolder<>(scalarModel));

        OutputTemplate.LABEL.createComponent(this, this::createOutputAsLabel);

        if(scalarModel.getFormatModifers()!=null
                && scalarModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
            val bg = ButtonGroupTemplate.RIGHT_BELOW_INSIDE.createRepeatingView(this);
            ButtonTemplate.EDIT_GROUPED.createComponent(bg, this::createLinkToEdit);
            ButtonTemplate.COPY_GROUPED.createComponent(bg, this::createLinkToCopy);
        } else {
            val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(this);
            ButtonTemplate.EDIT_OUTLINED.createComponent(bg, this::createLinkToEdit);
            ButtonTemplate.COPY_OUTLINED.createComponent(bg, this::createLinkToCopy);
        }

    }

    @SuppressWarnings("unchecked")
    protected ScalarModel<T> scalarModel() {
        return (ScalarModel<T>) getDefaultModelObject();
    }

    @SuppressWarnings("unchecked")
    protected ScalarPanel<T> scalarPanel() {
        return (ScalarPanel<T>) getParent();
    }

    private Component createOutputAsLabel(final String id) {
        val label = new Label(id, scalarModel().getValue());

        label.add(new AjaxEventBehavior("click") {
            @Override
            protected void onEvent(final AjaxRequestTarget target) {
                ScalarOutputPanel.this.onEditClick(target);
            }
        });

        return label;
    }

    private Component createLinkToEdit(final String id) {
        val link = new AjaxLink<Void>(id){
            @Override
            public void onClick(final AjaxRequestTarget target) {
                ScalarOutputPanel.this.onEditClick(target);
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

    public void onEditClick(final AjaxRequestTarget target) {
        val parent = ScalarOutputPanel.this.scalarPanel();
        parent.setFormat(ScalarPanel.Format.INPUT);
        target.add(parent);
    }

}

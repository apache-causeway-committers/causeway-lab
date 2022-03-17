package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import org.apache.isis.commons.internal.functions._Functions.SerializableConsumer;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.ButtonGroupTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.ButtonTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.fragments.BootstrapFragment.OutputTemplate;
import org.apache.isis.lab.experiments.wicket.bootstrap.util.WktUtil;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

import lombok.val;

public class ScalarOutputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public ScalarOutputPanel(final String id, final ScalarModel<T> scalarModel) {
        super(id, new ScalarModelHolder<>(scalarModel));

        if(scalarModel.getFormatModifers().contains(FormatModifer.MARKUP)) {
            OutputTemplate.MARKUP.createComponent(this, this::createOutputAsHtml);
        } else {

            if(scalarModel.isBoolean()) {

                val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(this);

                if(scalarModel.getFormatModifers().contains(FormatModifer.TRISTATE)) {
                    val triState = scalarModel().asTriState().getValue().getObject();
                    if(triState==null) {
                        OutputTemplate.CHECK_INTERMEDIATE.createComponent(this, this::createOutputAsCheckInactive);
                        ButtonTemplate.CHECK_SET_OUTLINED.createComponent(bg, this::createLinkToCheckSet);
                        ButtonTemplate.CHECK_CLEAR_OUTLINED.createComponent(bg, this::createLinkToCheckClear);
                    } else if(triState) {
                        OutputTemplate.CHECK_CHECKED.createComponent(this, this::createOutputAsCheckInactive);
                        ButtonTemplate.CHECK_CLEAR_OUTLINED.createComponent(bg, this::createLinkToCheckClear);
                        ButtonTemplate.CHECK_INTERMEDIATE_OUTLINED.createComponent(bg, this::createLinkToCheckIntermediate);

                    } else {
                        OutputTemplate.CHECK_UNCHECKED.createComponent(this, this::createOutputAsCheckInactive);
                        ButtonTemplate.CHECK_SET_OUTLINED.createComponent(bg, this::createLinkToCheckSet);
                        ButtonTemplate.CHECK_INTERMEDIATE_OUTLINED.createComponent(bg, this::createLinkToCheckIntermediate);
                    }
                } else {
                    val binaryState = scalarModel().asBinaryState().getValue().getObject();
                    if(binaryState) {
                        OutputTemplate.CHECK_CHECKED.createComponent(this, this::createOutputAsCheckSet);
                    } else {
                        OutputTemplate.CHECK_UNCHECKED.createComponent(this, this::createOutputAsCheckClear);
                    }

                }

            } else {

                OutputTemplate.LABEL.createComponent(this, this::createOutputAsLabel);

            }
        }

        if(!scalarModel.isBoolean()) {

            // add default buttons

            if(scalarModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
                val bg = ButtonGroupTemplate.RIGHT_BELOW_INSIDE.createRepeatingView(this);
                ButtonTemplate.EDIT_GROUPED.createComponent(bg, this::createLinkToEdit);
                ButtonTemplate.COPY_GROUPED.createComponent(bg, this::createLinkToCopy);
            } else {
                val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(this);
                ButtonTemplate.EDIT_OUTLINED.createComponent(bg, this::createLinkToEdit);
                ButtonTemplate.COPY_OUTLINED.createComponent(bg, this::createLinkToCopy);
            }
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
        addOnClick(label, ScalarOutputPanel.this::onEditClick);
        return label;
    }

    private Component createOutputAsCheckInactive(final String id) {
        val label = new WebMarkupContainer(id);
        return label;
    }

    private Component createOutputAsCheckSet(final String id) {
        val label = createOutputAsCheckInactive(id);
        addOnClick(label, ajaxTarget->ScalarOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.FALSE));
        return label;
    }

    private Component createOutputAsCheckClear(final String id) {
        val label = createOutputAsCheckInactive(id);
        addOnClick(label, ajaxTarget->ScalarOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.TRUE));
        return label;
    }

    private Component createOutputAsHtml(final String id) {
        val markup = new PlainHtml(id, scalarModel().getValue());
        addOnClick(markup, ScalarOutputPanel.this::onEditClick);
        return markup;
    }

    private Component createLinkToEdit(final String id) {
        return WktUtil.createLink(id, ScalarOutputPanel.this::onEditClick);
    }

    private Component createLinkToCopy(final String id) {
        return WktUtil.createLink(id, ScalarOutputPanel.this::onCopyClick);
    }

    private Component createLinkToCheckSet(final String id) {
        return WktUtil.createLink(id, ajaxTarget->ScalarOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.TRUE));
    }

    private Component createLinkToCheckClear(final String id) {
        return WktUtil.createLink(id, ajaxTarget->ScalarOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.FALSE));
    }

    private Component createLinkToCheckIntermediate(final String id) {
        return WktUtil.createLink(id, ajaxTarget->ScalarOutputPanel.this.onCheckboxClick(ajaxTarget, (Boolean)null));
    }



    private void addOnClick(
            final Component component,
            final SerializableConsumer<AjaxRequestTarget> onClick) {
        component.add(new AjaxEventBehavior("click") {
            private static final long serialVersionUID = 1L;
            @Override protected void onEvent(final AjaxRequestTarget target) {
                onClick.accept(target);
            }
        });
    }

    public void onEditClick(final AjaxRequestTarget ajaxTarget) {
        switchToInputFormat(ajaxTarget);
    }

    public void onCopyClick(final AjaxRequestTarget ajaxTarget) {
        //TODO copy value to clipboard (use client side js)
    }

    public void onCheckboxClick(final AjaxRequestTarget ajaxTarget, final Boolean proposedValue) {
        // request the proper bi/tri state model based on semantics
        if(scalarModel().getFormatModifers().contains(FormatModifer.TRISTATE)) {
            scalarModel().asTriState().getPendingValue().setObject(proposedValue);
        } else {
            scalarModel().asBinaryState().getPendingValue().setObject(proposedValue);
        }
        switchToInputFormat(ajaxTarget);
    }

    private void switchToInputFormat(final AjaxRequestTarget ajaxTarget) {
        val parent = ScalarOutputPanel.this.scalarPanel();
        parent.setFormat(ScalarPanel.Format.INPUT);
        ajaxTarget.add(parent);
    }


}

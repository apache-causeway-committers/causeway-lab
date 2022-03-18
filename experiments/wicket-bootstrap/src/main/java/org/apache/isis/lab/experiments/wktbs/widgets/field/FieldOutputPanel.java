package org.apache.isis.lab.experiments.wktbs.widgets.field;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import org.apache.isis.commons.internal.functions._Functions.SerializableConsumer;
import org.apache.isis.lab.experiments.wktbs.fragments.BootstrapFragment.ButtonGroupTemplate;
import org.apache.isis.lab.experiments.wktbs.fragments.BootstrapFragment.ButtonTemplate;
import org.apache.isis.lab.experiments.wktbs.fragments.BootstrapFragment.OutputTemplate;
import org.apache.isis.lab.experiments.wktbs.util.WktUtil;
import org.apache.isis.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;
import org.apache.isis.lab.experiments.wktbs.widgets.markup.PlainHtml;

import lombok.val;

public class FieldOutputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public FieldOutputPanel(final String id, final FieldModel<T> scalarModel) {
        super(id, new FieldModelHolder<>(scalarModel));

        if(scalarModel.getFormatModifers().contains(FormatModifer.MARKUP)) {
            OutputTemplate.MARKUP.createComponent(this, this::createOutputAsPlainHtml);
        } else {

            if(scalarModel.isBoolean()) {

                val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(this);

                if(scalarModel.getFormatModifers().contains(FormatModifer.TRISTATE)) {
                    val triState = fieldModel().asTriState().getValue().getObject();
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
                    val binaryState = fieldModel().asBinaryState().getValue().getObject();
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
    protected FieldModel<T> fieldModel() {
        return (FieldModel<T>) getDefaultModelObject();
    }

    @SuppressWarnings("unchecked")
    protected FieldPanel<T> fieldPanel() {
        return (FieldPanel<T>) getParent();
    }

    private Component createOutputAsLabel(final String id) {
        val label = new Label(id, fieldModel().getValue());
        addOnClick(label, FieldOutputPanel.this::onEditClick);
        return label;
    }

    private Component createOutputAsCheckInactive(final String id) {
        val component = new WebMarkupContainer(id);
        component.add(AttributeModifier.append("class", "wv-cursor-default"));
        return component;
    }

    private Component createOutputAsCheckSet(final String id) {
        val component = new WebMarkupContainer(id);
        addOnClick(component, ajaxTarget->FieldOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.FALSE));
        component.add(AttributeModifier.append("class", "wv-cursor-pointer"));
        return component;
    }

    private Component createOutputAsCheckClear(final String id) {
        val component = new WebMarkupContainer(id);
        addOnClick(component, ajaxTarget->FieldOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.TRUE));
        component.add(AttributeModifier.append("class", "wv-cursor-pointer"));
        return component;
    }

    private Component createOutputAsPlainHtml(final String id) {
        val markup = new PlainHtml(id, fieldModel().getValue());
        addOnClick(markup, FieldOutputPanel.this::onEditClick);
        return markup;
    }

    private Component createLinkToEdit(final String id) {
        return WktUtil.createLink(id, FieldOutputPanel.this::onEditClick);
    }

    private Component createLinkToCopy(final String id) {
        return WktUtil.createLink(id, FieldOutputPanel.this::onCopyClick);
    }

    private Component createLinkToCheckSet(final String id) {
        return WktUtil.createLink(id, ajaxTarget->FieldOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.TRUE));
    }

    private Component createLinkToCheckClear(final String id) {
        return WktUtil.createLink(id, ajaxTarget->FieldOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.FALSE));
    }

    private Component createLinkToCheckIntermediate(final String id) {
        return WktUtil.createLink(id, ajaxTarget->FieldOutputPanel.this.onCheckboxClick(ajaxTarget, (Boolean)null));
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
        if(fieldModel().getFormatModifers().contains(FormatModifer.TRISTATE)) {
            fieldModel().asTriState().getPendingValue().setObject(proposedValue);
        } else {
            fieldModel().asBinaryState().getPendingValue().setObject(proposedValue);
        }
        switchToInputFormat(ajaxTarget);
    }

    private void switchToInputFormat(final AjaxRequestTarget ajaxTarget) {
        val parent = FieldOutputPanel.this.fieldPanel();
        parent.setFormat(FieldPanel.Format.INPUT);
        ajaxTarget.add(parent);
    }


}

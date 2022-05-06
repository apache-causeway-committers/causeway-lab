package org.apache.isis.lab.experiments.wktfcal.widgets.field;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import org.apache.isis.commons.internal.functions._Functions.SerializableConsumer;
import org.apache.isis.lab.experiments.wktfcal.fragments.BootstrapFragment.ButtonGroupTemplate;
import org.apache.isis.lab.experiments.wktfcal.fragments.BootstrapFragment.ButtonTemplate;
import org.apache.isis.lab.experiments.wktfcal.fragments.BootstrapFragment.OutputTemplate;
import org.apache.isis.lab.experiments.wktfcal.util.WktUtil;
import org.apache.isis.lab.experiments.wktfcal.widgets.field.FieldPanel.FormatModifer;
import org.apache.isis.lab.experiments.wktfcal.widgets.field.model.FieldModel;
import org.apache.isis.lab.experiments.wktfcal.widgets.markup.PlainHtml;

import lombok.val;

public class FieldOutputPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    @FunctionalInterface
    public static interface FieldOutputFactory<T> extends Serializable {
        Component createFieldOutput(MarkupContainer container, FieldModel<T> fieldModel);
    }

    class FieldOutputFactoryBoolean implements FieldOutputFactory<Boolean> {

        private static final long serialVersionUID = 1L;

        @Override
        public Component createFieldOutput(
                final MarkupContainer container,
                final FieldModel<Boolean> fieldModel) {

            val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(container);

            final Component component;

            if(fieldModel.getFormatModifers().contains(FormatModifer.TRISTATE)) {
                val triState = fieldModel().asTriState().getValue().getObject();
                if(triState==null) {
                    component = OutputTemplate.CHECK_INTERMEDIATE.createComponent(container, this::createOutputAsCheckInactive);
                    ButtonTemplate.CHECK_SET_OUTLINED.createComponent(bg, this::createLinkToCheckSet);
                    ButtonTemplate.CHECK_CLEAR_OUTLINED.createComponent(bg, this::createLinkToCheckClear);
                } else if(triState) {
                    component = OutputTemplate.CHECK_CHECKED.createComponent(container, this::createOutputAsCheckInactive);
                    ButtonTemplate.CHECK_CLEAR_OUTLINED.createComponent(bg, this::createLinkToCheckClear);
                    ButtonTemplate.CHECK_INTERMEDIATE_OUTLINED.createComponent(bg, this::createLinkToCheckIntermediate);
                } else {
                    component = OutputTemplate.CHECK_UNCHECKED.createComponent(container, this::createOutputAsCheckInactive);
                    ButtonTemplate.CHECK_SET_OUTLINED.createComponent(bg, this::createLinkToCheckSet);
                    ButtonTemplate.CHECK_INTERMEDIATE_OUTLINED.createComponent(bg, this::createLinkToCheckIntermediate);
                }
            } else {
                val binaryState = fieldModel().asBinaryState().getValue().getObject();
                if(binaryState) {
                    component = OutputTemplate.CHECK_CHECKED.createComponent(container, this::createOutputAsCheckSet);
                } else {
                    component = OutputTemplate.CHECK_UNCHECKED.createComponent(container, this::createOutputAsCheckClear);
                }
            }
            return component;
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

        private Component createLinkToCheckSet(final String id) {
            return WktUtil.createLink(id, ajaxTarget->FieldOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.TRUE));
        }

        private Component createLinkToCheckClear(final String id) {
            return WktUtil.createLink(id, ajaxTarget->FieldOutputPanel.this.onCheckboxClick(ajaxTarget, Boolean.FALSE));
        }

        private Component createLinkToCheckIntermediate(final String id) {
            return WktUtil.createLink(id, ajaxTarget->FieldOutputPanel.this.onCheckboxClick(ajaxTarget, (Boolean)null));
        }

    }

    class FieldOutputFactoryString implements FieldOutputFactory<String> {

        private static final long serialVersionUID = 1L;

        @Override
        public Component createFieldOutput(
                final MarkupContainer container,
                final FieldModel<String> fieldModel) {

            final Component component;

            if(fieldModel.getFormatModifers().contains(FormatModifer.MARKUP)) {
                component = OutputTemplate.MARKUP.createComponent(container, this::createOutputAsPlainHtml);
            } else {
                component = OutputTemplate.LABEL.createComponent(container, this::createOutputAsLabel);
            }

            // add default buttons

            if(fieldModel.getFormatModifers().contains(FormatModifer.MULITLINE)) {
                val bg = ButtonGroupTemplate.RIGHT_BELOW_INSIDE.createRepeatingView(container);
                ButtonTemplate.EDIT_GROUPED.createComponent(bg, FieldOutputPanel.this::createLinkToEdit);
                ButtonTemplate.COPY_GROUPED.createComponent(bg, FieldOutputPanel.this::createLinkToCopy);
            } else {
                val bg = ButtonGroupTemplate.OUTLINED.createRepeatingView(container);
                ButtonTemplate.EDIT_OUTLINED.createComponent(bg, FieldOutputPanel.this::createLinkToEdit);
                ButtonTemplate.COPY_OUTLINED.createComponent(bg, FieldOutputPanel.this::createLinkToCopy);
            }

            return component;
        }

        private Component createOutputAsLabel(final String id) {
            val label = new Label(id, fieldModel().getValue());
            addOnClick(label, FieldOutputPanel.this::onEditClick);
            return label;
        }

        private Component createOutputAsPlainHtml(final String id) {
            val markup = new PlainHtml(id, fieldModel().getValue());
            addOnClick(markup, FieldOutputPanel.this::onEditClick);
            return markup;
        }

    }


    public FieldOutputPanel(final String id, final FieldModel<T> fieldModel) {
        super(id, new FieldModelHolder<>(fieldModel));

        if(fieldModel.isBoolean()) {
            new FieldOutputFactoryBoolean().createFieldOutput(this, (FieldModel<Boolean>)fieldModel);
        } else {
            new FieldOutputFactoryString().createFieldOutput(this, (FieldModel<String>)fieldModel);
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

    private Component createLinkToEdit(final String id) {
        return WktUtil.createLink(id, FieldOutputPanel.this::onEditClick);
    }

    private Component createLinkToCopy(final String id) {
        return WktUtil.createLink(id, FieldOutputPanel.this::onCopyClick);
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

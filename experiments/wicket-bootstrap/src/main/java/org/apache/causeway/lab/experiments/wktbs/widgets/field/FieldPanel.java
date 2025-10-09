package org.apache.causeway.lab.experiments.wktbs.widgets.field;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.panel.Panel;

import org.apache.causeway.lab.experiments.wktbs.widgets.field.model.FieldModel;

import lombok.Getter;
import org.jspecify.annotations.NonNull;

public class FieldPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public static enum FormatModifer {
        MARKUP,
        MULITLINE,
        TRISTATE,
        FLEX
    }

    static enum Format {
        OUTPUT,
        INPUT;
        boolean isOutput(){return this == Format.OUTPUT;}
        boolean isInput(){return this == Format.INPUT;}
    }

    @Getter
    private Format format;

    public FieldPanel(
            final String id,
            final FieldModel<T> fieldModel) {
        super(id, new FieldModelHolder<>(fieldModel));
        setOutputMarkupId(true);

        if(fieldModel.getFormatModifers().contains(FormatModifer.FLEX)) {
            add(AttributeModifier.append("class", "d-inline-flex"));
        }

        setFormat(Format.OUTPUT); // initial, can be switched via ajax
    }

    @SuppressWarnings("unchecked")
    protected FieldModel<T> fieldModel() {
        return (FieldModel<T>) getDefaultModelObject();
    }

    public void setFormat(final @NonNull Format format) {
        this.format = format;
        setFormFieldSubPanelBasedOnFormat("container-formField");
    }

    private void setFormFieldSubPanelBasedOnFormat(final String id) {

        switch (format) {
        case OUTPUT:
            addOrReplace(new FieldOutputPanel<T>(id, fieldModel()));
            return;
        case INPUT: {
            addOrReplace(new FieldInputPanel<T>(id, fieldModel()));
            return;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + format);
        }
    }

}

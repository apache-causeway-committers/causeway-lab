package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.markup.html.panel.Panel;

import lombok.Getter;
import lombok.NonNull;

public class FormFieldPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public static enum FormatModifer {
        MULITLINE
    }

    static enum Format {
        OUTPUT,
        INPUT;
        boolean isOutput(){return this == Format.OUTPUT;}
        boolean isInput(){return this == Format.INPUT;}
    }

    @Getter
    private Format format;

    public FormFieldPanel(
            final String id,
            final FormFieldModel<T> formFieldModel) {
        super(id, new FormFieldModelHolder<>(formFieldModel));
        setOutputMarkupId(true);

        setFormat(Format.OUTPUT);
    }

    @SuppressWarnings("unchecked")
    protected FormFieldModel<T> formFieldModel() {
        return (FormFieldModel<T>) getDefaultModelObject();
    }

    public void setFormat(final @NonNull Format format) {
        this.format = format;
        setFormFieldSubPanelBasedOnFormat("container-formField");
    }

    private void setFormFieldSubPanelBasedOnFormat(final String id) {

        switch (format) {
        case OUTPUT:
            addOrReplace(new FormFieldOutputPanel<T>(id, formFieldModel()));
            return;
        case INPUT: {
            addOrReplace(new FormFieldInputPanel<T>(id, formFieldModel()));
            return;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + format);
        }
    }


}

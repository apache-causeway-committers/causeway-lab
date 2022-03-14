package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.markup.html.panel.Panel;

import lombok.Getter;
import lombok.NonNull;

public class ScalarPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public static enum FormatModifer {
        MARKUP,
        MULITLINE,
        TRISTATE,
    }

    static enum Format {
        OUTPUT,
        INPUT;
        boolean isOutput(){return this == Format.OUTPUT;}
        boolean isInput(){return this == Format.INPUT;}
    }

    @Getter
    private Format format;

    public ScalarPanel(
            final String id,
            final ScalarModel<T> formFieldModel) {
        super(id, new ScalarModelHolder<>(formFieldModel));
        setOutputMarkupId(true);

        setFormat(Format.OUTPUT);
    }

    @SuppressWarnings("unchecked")
    protected ScalarModel<T> scalarModel() {
        return (ScalarModel<T>) getDefaultModelObject();
    }

    public void setFormat(final @NonNull Format format) {
        this.format = format;
        setFormFieldSubPanelBasedOnFormat("container-formField");
    }

    private void setFormFieldSubPanelBasedOnFormat(final String id) {

        switch (format) {
        case OUTPUT:
            addOrReplace(new ScalarOutputPanel<T>(id, scalarModel()));
            return;
        case INPUT: {
            addOrReplace(new ScalarInputPanel<T>(id, scalarModel()));
            return;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + format);
        }
    }


}

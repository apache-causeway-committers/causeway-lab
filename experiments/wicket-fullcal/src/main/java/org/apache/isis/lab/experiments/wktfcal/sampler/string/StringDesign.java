package org.apache.isis.lab.experiments.wktfcal.sampler.string;

import java.util.EnumSet;

import org.apache.wicket.markup.html.panel.Panel;

import org.apache.isis.lab.experiments.wktfcal.widgets.field.FieldPanel.FormatModifer;

public class StringDesign extends Panel {

    private static final long serialVersionUID = 1L;

    private final EnumSet<FormatModifer> formatModifers;

    public StringDesign(final String id, final EnumSet<FormatModifer> formatModifers) {
        super(id);
        this.formatModifers = formatModifers;
    }

    @Override
    public String getVariation() {
        return formatModifers.contains(FormatModifer.MARKUP)
                ? "markup"
                : formatModifers.contains(FormatModifer.MULITLINE)
                    ? "multiline"
                    : null;
    }

}

package org.apache.causeway.lab.experiments.wktbs.sampler.bool;

import java.util.EnumSet;

import org.apache.wicket.markup.html.panel.Panel;

import org.apache.causeway.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;

public class BooleanDesign extends Panel {

    private static final long serialVersionUID = 1L;

    private final EnumSet<FormatModifer> formatModifers;

    public BooleanDesign(final String id, final EnumSet<FormatModifer> formatModifers) {
        super(id);
        this.formatModifers = formatModifers;
    }

    @Override
    public String getVariation() {
        return formatModifers.contains(FormatModifer.TRISTATE)
                ? "tristate"
                : null;
    }

}

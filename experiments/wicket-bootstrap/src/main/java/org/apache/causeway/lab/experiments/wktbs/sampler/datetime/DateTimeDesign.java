package org.apache.causeway.lab.experiments.wktbs.sampler.datetime;

import java.util.EnumSet;

import org.apache.wicket.markup.html.panel.Panel;

import org.apache.causeway.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;

public class DateTimeDesign extends Panel {

    private static final long serialVersionUID = 1L;

    private final EnumSet<FormatModifer> formatModifers;

    public DateTimeDesign(final String id, final EnumSet<FormatModifer> formatModifers) {
        super(id);
        this.formatModifers = formatModifers;
    }

}

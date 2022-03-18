package org.apache.isis.lab.experiments.wktbs.sampler.file;

import java.util.EnumSet;

import org.apache.wicket.markup.html.panel.Panel;

import org.apache.isis.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;

public class FileDesign extends Panel {

    private static final long serialVersionUID = 1L;

    private final EnumSet<FormatModifer> formatModifers;

    public FileDesign(final String id, final EnumSet<FormatModifer> formatModifers) {
        super(id);
        this.formatModifers = formatModifers;
    }

}

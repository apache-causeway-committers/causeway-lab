package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.IModel;

import java.io.Serializable;

public class Label extends WebComponent {
    private static final long serialVersionUID = 1L;

    public Label(String id) {
        super(id);
    }

    public Label(String id, Serializable label) {
        this(id, (IModel)Model.of(label));
    }

    public Label(String id, IModel<?> model) {
        super(id, model);
    }

    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
    }

    protected void onComponentTag(ComponentTag tag) {
    }

    public void setDefaultModelObject(String s) {
    }

    public void setEscapeModelStrings(boolean b) {

    }
}

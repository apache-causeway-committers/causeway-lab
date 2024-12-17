package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.MarkupContainer;
import org.apache.causeway.wicketstubs.api.WebMarkupContainer;

public class TransparentWebMarkupContainer
        extends WebMarkupContainer
        implements IComponentResolver {

    public TransparentWebMarkupContainer(String id) {
        super(id);
    }

    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {
        return null;
    }

}

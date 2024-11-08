package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.MarkupContainer;

//FIXME
public interface IMarkupSourcingStrategy {
    IMarkupFragment getMarkup(MarkupContainer components, Component child);

    void renderHead(Component component, HtmlHeaderContainer container);

    void onComponentTagBody(Component component, MarkupStream markupStream, ComponentTag tag);

    void onComponentTag(Component component, ComponentTag tag);
}

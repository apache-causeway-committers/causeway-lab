package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.MarkupContainer;

//FIXME
public class DequeueContext {
    public DequeueContext(IMarkupFragment markup, MarkupContainer components, boolean b) {
    }

    public boolean peekTag() {
        return false;
    }

    public boolean isAtOpenOrOpenCloseTag() {
        return false;
    }

    public ComponentTag takeTag() {
        return null;
    }

    public Component findComponentToDequeue(ComponentTag tag) {
        return null;
    }

    public void skipToCloseTag() {
    }

    public void pushContainer(MarkupContainer child) {
    }

    public void popContainer() {

    }
}

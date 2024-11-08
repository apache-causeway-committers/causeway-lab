package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.ComponentTag;

//FIXME
public class MarkupElement {
    public byte[] toCharSequence() {
        return toString().getBytes();
    }

    public boolean closes(ComponentTag openTag) {
        return false;
    }
}

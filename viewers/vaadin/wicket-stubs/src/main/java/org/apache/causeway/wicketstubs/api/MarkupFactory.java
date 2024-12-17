package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IMarkupSourcingStrategy;

public class MarkupFactory {
    public static IMarkupSourcingStrategy get() {
        return new MarkupFactory().get();
    }
}

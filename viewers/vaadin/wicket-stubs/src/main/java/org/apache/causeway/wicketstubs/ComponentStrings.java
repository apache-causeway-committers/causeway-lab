package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;

public class ComponentStrings {
    public static Object toString(Component component, MarkupException constructed) {
        return constructed != null ? constructed.toString() : component.toString();//FIXME
    }
}

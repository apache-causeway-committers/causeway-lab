package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.MarkupContainer;

//FIXME
public class Classes {
    public static String simpleName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public static String name(Class<? extends MarkupContainer> aClass) {
        return aClass.getSimpleName();
    }
}

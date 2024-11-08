package org.apache.causeway.wicketstubs;

//import org.apache.wicket.util.lang.EnumeratedType;
//import org.apache.wicket.util.string.Strings;

import org.apache.causeway.wicketstubs.api.Strings;

public class Action
//        extends EnumeratedType
{
    public static final String RENDER = "RENDER";
    public static final String ENABLE = "ENABLE";
    private static final long serialVersionUID = -1L;
    private final String name;

    public Action(String name) {
//        super(name);
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException("Name argument may not be null, whitespace or the empty string");
        } else {
            this.name = name;
        }
    }

    public String getName() {
        return this.name;
    }
}

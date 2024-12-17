package org.apache.causeway.wicketstubs;

import java.util.Locale;

public abstract class Session {
    public static Session get() {
        return Session.get();
    }

    public Locale getLocale() {
        return Session.get().getLocale();
    }

    public String getStyle() {
        return null;
    }
}

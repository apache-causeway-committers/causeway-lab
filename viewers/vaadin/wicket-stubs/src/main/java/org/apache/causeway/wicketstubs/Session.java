package org.apache.causeway.wicketstubs;

import java.util.Locale;

public abstract class Session {
    public static Session get() {
        return Session.get();//FIXME
    }

    public Locale getLocale() {
        return Session.get().getLocale();//FIXME
    }

    public IAuthorizationStrategy getAuthorizationStrategy() {
        return null;//FIXME
    }

    public String getStyle() {
        return null;//FIXME
    }
}

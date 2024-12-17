package org.apache.causeway.wicketstubs.api;

import java.util.Locale;

import org.apache.causeway.wicketstubs.StringValueConversionException;

public class StringValue implements IClusterable {

    protected StringValue(String text) {
        this(text, Locale.getDefault());
    }

    protected StringValue(String text, Locale locale) {
    }

    public final CharSequence replaceAll(CharSequence searchFor, CharSequence replaceWith) {
        return null;
    }

    public final <T> T to(Class<T> type) throws StringValueConversionException {
        return null;
    }

    public final String toString() {
        return null;
    }

    public final String toString(String defaultValue) {
        return null;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public boolean equals(Object obj) {
        return false;
    }

}
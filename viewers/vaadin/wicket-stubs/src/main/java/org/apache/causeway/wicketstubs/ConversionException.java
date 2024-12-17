package org.apache.causeway.wicketstubs;

import java.text.Format;
import java.util.Locale;
import java.util.Map;

public class ConversionException extends RuntimeException {
    private IConverter<?> converter;
    private Format format;
    private Locale locale;
    private Object sourceValue;
    private Class<?> targetType;
    private String resourceKey;
    private Map<String, Object> vars;

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public final IConverter<?> getConverter() {
        return this.converter;
    }

    public final Format getFormat() {
        return this.format;
    }

    public final Locale getLocale() {
        return this.locale;
    }

    public final ConversionException setFormat(Format format) {
        return null;
    }

    public final ConversionException setLocale(Locale locale) {
        return null;
    }

    public Map<String, Object> getVariables() {
        return this.vars;
    }
}

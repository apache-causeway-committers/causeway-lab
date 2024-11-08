package org.apache.causeway.wicketstubs;

import java.text.Format;
import java.util.Locale;
import java.util.Map;

public class ConversionException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private IConverter<?> converter;
    private Format format;
    private Locale locale;
    private Object sourceValue;
    private Class<?> targetType;
    private String resourceKey;
    private Map<String, Object> vars;

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionException(Throwable cause) {
        super(cause);
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

    public final Object getSourceValue() {
        return this.sourceValue;
    }

    public final Class<?> getTargetType() {
        return this.targetType;
    }

    public final ConversionException setConverter(IConverter<?> converter) {
        this.converter = converter;
        return this;
    }

    public final ConversionException setFormat(Format format) {
        this.format = format;
        return this;
    }

    public final ConversionException setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public final ConversionException setSourceValue(Object sourceValue) {
        this.sourceValue = sourceValue;
        return this;
    }

    public final ConversionException setTargetType(Class<?> targetType) {
        this.targetType = targetType;
        return this;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    public ConversionException setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
        return this;
    }

  /*  public ConversionException setVariable(String name, Object value) {
        Args.notEmpty(name, "name");
        Args.notNull(value, "value");
        if (this.vars == null) {
            this.vars = Generics.newHashMap();
        }

        this.vars.put(name, value);
        return this;
    }*/

    public Map<String, Object> getVariables() {
        return this.vars;
    }
}

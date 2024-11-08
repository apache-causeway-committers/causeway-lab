package org.apache.causeway.wicketstubs;

import java.util.Locale;

public interface IConverter<T> {
    String convertToString(T value, Locale locale);
}

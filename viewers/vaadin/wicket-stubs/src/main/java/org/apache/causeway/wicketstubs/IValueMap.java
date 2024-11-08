package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.StringValue;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public interface IValueMap extends Map<String, Object> {
    boolean getBoolean(String var1) throws StringValueConversionException;

    double getDouble(String var1) throws StringValueConversionException;

    double getDouble(String var1, double var2) throws StringValueConversionException;

    Duration getDuration(String var1) throws StringValueConversionException;

    int getInt(String var1) throws StringValueConversionException;

    int getInt(String var1, int var2) throws StringValueConversionException;

    long getLong(String var1) throws StringValueConversionException;

    long getLong(String var1, long var2) throws StringValueConversionException;

    String getString(String var1, String var2);

    String getString(String var1);

    CharSequence getCharSequence(String var1);

    String[] getStringArray(String var1);

    StringValue getStringValue(String var1);

    Instant getInstant(String var1) throws StringValueConversionException;

    boolean isImmutable();

    IValueMap makeImmutable();

    String getKey(String var1);

    Boolean getAsBoolean(String var1);

    boolean getAsBoolean(String var1, boolean var2);

    Integer getAsInteger(String var1);

    int getAsInteger(String var1, int var2);

    Long getAsLong(String var1);

    long getAsLong(String var1, long var2);

    Double getAsDouble(String var1);

    double getAsDouble(String var1, double var2);

    Duration getAsDuration(String var1);

    Duration getAsDuration(String var1, Duration var2);

    Instant getAsInstant(String var1);

    Instant getAsTime(String var1, Instant var2);

    <T extends Enum<T>> T getAsEnum(String var1, Class<T> var2);

    <T extends Enum<T>> T getAsEnum(String var1, T var2);

    <T extends Enum<T>> T getAsEnum(String var1, Class<T> var2, T var3);
}


package org.apache.causeway.wicketstubs;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.causeway.wicketstubs.api.StringValue;

public class ValueMap extends LinkedHashMap<String, Object> implements IValueMap {
    public static final ValueMap EMPTY_MAP = new ValueMap();
    private boolean immutable;

    public ValueMap() {
        this.immutable = false;
    }


    public final void clear() {
    }

    @Override
    public boolean getBoolean(String var1) throws StringValueConversionException {
        return false;
    }

    @Override
    public double getDouble(String var1) throws StringValueConversionException {
        return 0;
    }

    @Override
    public double getDouble(String var1, double var2) throws StringValueConversionException {
        return 0;
    }

    @Override
    public Duration getDuration(String var1) throws StringValueConversionException {
        return null;
    }

    @Override
    public int getInt(String var1) throws StringValueConversionException {
        return 0;
    }

    @Override
    public int getInt(String var1, int var2) throws StringValueConversionException {
        return 0;
    }

    @Override
    public long getLong(String var1) throws StringValueConversionException {
        return 0;
    }

    @Override
    public long getLong(String var1, long var2) throws StringValueConversionException {
        return 0;
    }

    @Override
    public String getString(String var1, String var2) {
        return "";
    }

    @Override
    public String getString(String var1) {
        return "";
    }

    @Override
    public CharSequence getCharSequence(String var1) {
        return null;
    }

    @Override
    public String[] getStringArray(String var1) {
        return new String[0];
    }

    @Override
    public StringValue getStringValue(String var1) {
        return null;
    }

    @Override
    public Instant getInstant(String var1) throws StringValueConversionException {
        return null;
    }

    public final boolean isImmutable() {
        return this.immutable;
    }

    public final IValueMap makeImmutable() {
        return this;
    }

    @Override
    public String getKey(String var1) {
        return "";
    }

    @Override
    public Boolean getAsBoolean(String var1) {
        return null;
    }

    @Override
    public boolean getAsBoolean(String var1, boolean var2) {
        return false;
    }

    @Override
    public Integer getAsInteger(String var1) {
        return 0;
    }

    @Override
    public int getAsInteger(String var1, int var2) {
        return 0;
    }

    @Override
    public Long getAsLong(String var1) {
        return 0L;
    }

    @Override
    public long getAsLong(String var1, long var2) {
        return 0;
    }

    @Override
    public Double getAsDouble(String var1) {
        return 0.0;
    }

    @Override
    public double getAsDouble(String var1, double var2) {
        return 0;
    }

    @Override
    public Duration getAsDuration(String var1) {
        return null;
    }

    @Override
    public Duration getAsDuration(String var1, Duration var2) {
        return null;
    }

    @Override
    public Instant getAsInstant(String var1) {
        return null;
    }

    @Override
    public Instant getAsTime(String var1, Instant var2) {
        return null;
    }

    @Override
    public <T extends Enum<T>> T getAsEnum(String var1, Class<T> var2) {
        return null;
    }

    @Override
    public <T extends Enum<T>> T getAsEnum(String var1, T var2) {
        return null;
    }

    @Override
    public <T extends Enum<T>> T getAsEnum(String var1, Class<T> var2, T var3) {
        return null;
    }

    public Object put(String key, Object value) {
        this.checkMutability();
        return super.put(key, value);
    }

    public final Object add(String key, String value) {
        return put(key, value);
    }

    public void putAll(Map<? extends String, ?> map) {
    }

    public Object remove(Object key) {
        return null;
    }

    public String toString() {
        return null;
    }

    private void checkMutability() {
    }

    static {
        EMPTY_MAP.makeImmutable();
    }
}


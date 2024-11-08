package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.StringValue;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValueMap extends LinkedHashMap<String, Object> implements IValueMap {
    public static final ValueMap EMPTY_MAP = new ValueMap();
    private static final long serialVersionUID = 1L;
    private boolean immutable;

    public ValueMap() {
        this.immutable = false;
    }

    public ValueMap(Map<? extends String, ?> map) {
        this.immutable = false;
        super.putAll(map);
    }

    public ValueMap(String keyValuePairs) {
        this(keyValuePairs, ",");
    }

    public ValueMap(String keyValuePairs, String delimiter) {
        this.immutable = false;
        int start = 0;
        int equalsIndex = keyValuePairs.indexOf(61);
        int delimiterIndex = keyValuePairs.indexOf(delimiter, equalsIndex);
        if (delimiterIndex == -1) {
            delimiterIndex = keyValuePairs.length();
        }

        while (equalsIndex != -1) {
            if (delimiterIndex < keyValuePairs.length()) {
                int equalsIndex2 = keyValuePairs.indexOf(61, delimiterIndex + 1);
                if (equalsIndex2 != -1) {
                    delimiterIndex = keyValuePairs.lastIndexOf(delimiter, equalsIndex2);
                } else {
                    delimiterIndex = keyValuePairs.length();
                }
            }

            String key = keyValuePairs.substring(start, equalsIndex);
            String value = keyValuePairs.substring(equalsIndex + 1, delimiterIndex);
            this.add(key, value);
            if (delimiterIndex < keyValuePairs.length()) {
                start = delimiterIndex + 1;
                equalsIndex = keyValuePairs.indexOf(61, start);
                if (equalsIndex != -1) {
                    delimiterIndex = keyValuePairs.indexOf(delimiter, equalsIndex);
                    if (delimiterIndex == -1) {
                        delimiterIndex = keyValuePairs.length();
                    }
                }
            } else {
                equalsIndex = -1;
            }
        }

    }

    public ValueMap(String keyValuePairs, String delimiter, MetaPattern valuePattern) {
        this.immutable = false;
        StringList pairs = StringList.tokenize(keyValuePairs, delimiter);
        IStringIterator iterator = pairs.iterator();

        while (iterator.hasNext()) {
            String pair = iterator.next();
            VariableAssignmentParser parser = new VariableAssignmentParser(pair, valuePattern);
            if (!parser.matches()) {
                throw new IllegalArgumentException("Invalid key value list: '" + keyValuePairs + "'");
            }

            this.put((String) parser.getKey(), parser.getValue());
        }

    }

    public final void clear() {
        this.checkMutability();
        super.clear();
    }

    public final boolean getBoolean(String key) throws StringValueConversionException {
        return this.getStringValue(key).toBoolean();
    }

    public final double getDouble(String key) throws StringValueConversionException {
        return this.getStringValue(key).toDouble();
    }

    public final double getDouble(String key, double defaultValue) {
        return this.getStringValue(key).toDouble(defaultValue);
    }

    public final Duration getDuration(String key) throws StringValueConversionException {
        return this.getStringValue(key).toDuration();
    }

    public final int getInt(String key) throws StringValueConversionException {
        return this.getStringValue(key).toInt();
    }

    public final int getInt(String key, int defaultValue) {
        return this.getStringValue(key).toInt(defaultValue);
    }

    public final long getLong(String key) throws StringValueConversionException {
        return this.getStringValue(key).toLong();
    }

    public final long getLong(String key, long defaultValue) {
        return this.getStringValue(key).toLong(defaultValue);
    }

    public final String getString(String key, String defaultValue) {
        String value = this.getString(key);
        return value != null ? value : defaultValue;
    }

    public final String getString(String key) {
        Object o = this.get(key);
        if (o == null) {
            return null;
        } else if (o.getClass().isArray() && Array.getLength(o) > 0) {
            Object arrayValue = Array.get(o, 0);
            return arrayValue == null ? null : arrayValue.toString();
        } else {
            return o.toString();
        }
    }

    public final CharSequence getCharSequence(String key) {
        Object o = this.get(key);
        if (o == null) {
            return null;
        } else if (o.getClass().isArray() && Array.getLength(o) > 0) {
            Object arrayValue = Array.get(o, 0);
            if (arrayValue == null) {
                return null;
            } else {
                return (CharSequence) (arrayValue instanceof CharSequence ? (CharSequence) arrayValue : arrayValue.toString());
            }
        } else {
            return (CharSequence) (o instanceof CharSequence ? (CharSequence) o : o.toString());
        }
    }

    public String[] getStringArray(String key) {
        Object o = this.get(key);
        if (o == null) {
            return null;
        } else if (o instanceof String[]) {
            return (String[]) o;
        } else if (o.getClass().isArray()) {
            int length = Array.getLength(o);
            String[] array = new String[length];

            for (int i = 0; i < length; ++i) {
                Object arrayValue = Array.get(o, i);
                if (arrayValue != null) {
                    array[i] = arrayValue.toString();
                }
            }

            return array;
        } else {
            return new String[]{o.toString()};
        }
    }

    public StringValue getStringValue(String key) {
        return StringValue.valueOf(this.getString(key));
    }

    public final Instant getInstant(String key) throws StringValueConversionException {
        return this.getStringValue(key).toInstant();
    }

    public final boolean isImmutable() {
        return this.immutable;
    }

    public final IValueMap makeImmutable() {
        this.immutable = true;
        return this;
    }

    public Object put(String key, Object value) {
        this.checkMutability();
        return super.put(key, value);
    }

    public final Object add(String key, String value) {
        this.checkMutability();
        Object o = this.get(key);
        if (o == null) {
            return this.put((String) key, value);
        } else if (o.getClass().isArray()) {
            int length = Array.getLength(o);
            String[] destArray = new String[length + 1];

            for (int i = 0; i < length; ++i) {
                Object arrayValue = Array.get(o, i);
                if (arrayValue != null) {
                    destArray[i] = arrayValue.toString();
                }
            }

            destArray[length] = value;
            return this.put((String) key, destArray);
        } else {
            return this.put((String) key, new String[]{o.toString(), value});
        }
    }

    public void putAll(Map<? extends String, ?> map) {
        this.checkMutability();
        super.putAll(map);
    }

    public Object remove(Object key) {
        this.checkMutability();
        return super.remove(key);
    }

    public String getKey(String key) {
        Iterator var2 = this.keySet().iterator();

        String other;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            other = (String) var2.next();
        } while (!other.equalsIgnoreCase(key));

        return other;
    }

    public String toString() {
        AppendingStringBuffer buffer = new AppendingStringBuffer();
        boolean first = true;

        for (Iterator var3 = this.entrySet().iterator(); var3.hasNext(); buffer.append('"')) {
            Map.Entry<String, Object> entry = (Map.Entry) var3.next();
            if (!first) {
                buffer.append(' ');
            }

            first = false;
            buffer.append((String) entry.getKey());
            buffer.append(" = \"");
            Object value = entry.getValue();
            if (value == null) {
                buffer.append("null");
            } else if (value.getClass().isArray()) {
                buffer.append(Arrays.asList((Object[]) value));
            } else {
                buffer.append(value);
            }
        }

        return buffer.toString();
    }

    private void checkMutability() {
        if (this.immutable) {
            throw new UnsupportedOperationException("Map is immutable");
        }
    }

    public Boolean getAsBoolean(String key) {
        if (!this.containsKey(key)) {
            return null;
        } else {
            try {
                return this.getBoolean(key);
            } catch (StringValueConversionException var3) {
                return null;
            }
        }
    }

    public boolean getAsBoolean(String key, boolean defaultValue) {
        if (!this.containsKey(key)) {
            return defaultValue;
        } else {
            try {
                return this.getBoolean(key);
            } catch (StringValueConversionException var4) {
                return defaultValue;
            }
        }
    }

    public Integer getAsInteger(String key) {
        if (!this.containsKey(key)) {
            return null;
        } else {
            try {
                return this.getInt(key);
            } catch (StringValueConversionException var3) {
                return null;
            }
        }
    }

    public int getAsInteger(String key, int defaultValue) {
        return this.getInt(key, defaultValue);
    }

    public Long getAsLong(String key) {
        if (!this.containsKey(key)) {
            return null;
        } else {
            try {
                return this.getLong(key);
            } catch (StringValueConversionException var3) {
                return null;
            }
        }
    }

    public long getAsLong(String key, long defaultValue) {
        return this.getLong(key, defaultValue);
    }

    public Double getAsDouble(String key) {
        if (!this.containsKey(key)) {
            return null;
        } else {
            try {
                return this.getDouble(key);
            } catch (StringValueConversionException var3) {
                return null;
            }
        }
    }

    public double getAsDouble(String key, double defaultValue) {
        return this.getDouble(key, defaultValue);
    }

    public Duration getAsDuration(String key) {
        return this.getAsDuration(key, (Duration) null);
    }

    public Duration getAsDuration(String key, Duration defaultValue) {
        if (!this.containsKey(key)) {
            return defaultValue;
        } else {
            try {
                return this.getDuration(key);
            } catch (StringValueConversionException var4) {
                return defaultValue;
            }
        }
    }

    public Instant getAsInstant(String key) {
        return this.getAsTime(key, (Instant) null);
    }

    public Instant getAsTime(String key, Instant defaultValue) {
        if (!this.containsKey(key)) {
            return defaultValue;
        } else {
            try {
                return this.getInstant(key);
            } catch (StringValueConversionException var4) {
                return defaultValue;
            }
        }
    }

    public <T extends Enum<T>> T getAsEnum(String key, Class<T> eClass) {
        return this.getEnumImpl(key, eClass, (Enum) null);
    }

    public <T extends Enum<T>> T getAsEnum(String key, T defaultValue) {
        if (defaultValue == null) {
            throw new IllegalArgumentException("Default value cannot be null");
        } else {
            return this.getEnumImpl(key, defaultValue.getClass(), defaultValue);
        }
    }

    public <T extends Enum<T>> T getAsEnum(String key, Class<T> eClass, T defaultValue) {
        return this.getEnumImpl(key, eClass, defaultValue);
    }

    private <T extends Enum<T>> T getEnumImpl(String key, Class<?> eClass, T defaultValue) {
        if (eClass == null) {
            throw new IllegalArgumentException("eClass value cannot be null");
        } else {
            String value = this.getString(key);
            if (value == null) {
                return defaultValue;
            } else {
                Method valueOf = null;

                try {
                    valueOf = eClass.getMethod("valueOf", String.class);
                } catch (NoSuchMethodException var7) {
                    NoSuchMethodException e = var7;
                    throw new RuntimeException("Could not find method valueOf(String s) for " + eClass.getName(), e);
                }

                try {
                    return (Enum) valueOf.invoke(eClass, value);
                } catch (IllegalAccessException var8) {
                    IllegalAccessException e = var8;
                    throw new RuntimeException("Could not invoke method valueOf(String s) on " + eClass.getName(), e);
                } catch (InvocationTargetException var9) {
                    InvocationTargetException e = var9;
                    if (e.getCause() instanceof IllegalArgumentException) {
                        return defaultValue;
                    } else {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    static {
        EMPTY_MAP.makeImmutable();
    }
}


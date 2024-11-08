package org.apache.causeway.wicketstubs.api;

import java.util.List;
import java.util.Set;

public interface INamedParameters {
    Set<String> getNamedKeys();

    StringValue get(String var1);

    List<StringValue> getValues(String var1);

    List<NamedPair> getAllNamed();

    List<NamedPair> getAllNamedByType(Type var1);

    int getPosition(String var1);

    INamedParameters remove(String var1, String... var2);

    INamedParameters add(String var1, Object var2, Type var3);

    INamedParameters add(String var1, Object var2, int var3, Type var4);

    INamedParameters set(String var1, Object var2, int var3, Type var4);

    INamedParameters set(String var1, Object var2, Type var3);

    INamedParameters clearNamed();

    public static class NamedPair implements IClusterable {
        private final String key;
        private final String value;
        private final Type type;

        public NamedPair(String key, String value) {
            this(key, value, INamedParameters.Type.MANUAL);
        }

        public NamedPair(String key, String value, Type type) {
            this.key = Args.notEmpty(key, "key");
            this.value = (String) Args.notNull(value, "value");
            this.type = (Type)Args.notNull(type, "type");
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        public Type getType() {
            return this.type;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                NamedPair namedPair = (NamedPair)o;
                if (this.key != null) {
                    if (!this.key.equals(namedPair.key)) {
                        return false;
                    }
                } else if (namedPair.key != null) {
                    return false;
                }

                if (this.value != null) {
                    if (this.value.equals(namedPair.value)) {
                        return true;
                    }
                } else if (namedPair.value == null) {
                    return true;
                }

                return false;
            } else {
                return false;
            }
        }

        public int hashCode() {
            int result = this.key != null ? this.key.hashCode() : 0;
            result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
            return result;
        }
    }

    public static enum Type {
        MANUAL,
        QUERY_STRING,
        PATH;

        private Type() {
        }
    }

}


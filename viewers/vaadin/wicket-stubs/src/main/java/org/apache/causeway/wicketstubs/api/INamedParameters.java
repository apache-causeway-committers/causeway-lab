package org.apache.causeway.wicketstubs.api;

import java.util.List;

public interface INamedParameters {

    StringValue get(String var1);

    List<StringValue> getValues(String var1);

    List<NamedPair> getAllNamed();

    int getPosition(String var1);

    INamedParameters remove(String var1, String... var2);

    INamedParameters add(String var1, Object var2, Type var3);

    INamedParameters add(String var1, Object var2, int var3, Type var4);

    INamedParameters set(String var1, Object var2, int var3, Type var4);

    INamedParameters set(String var1, Object var2, Type var3);

    class NamedPair implements IClusterable {
        private final String key = "";
        private final String value = "";
        private final Type type = Type.MANUAL;

        public NamedPair(String key, String value, Type type) {
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
            return false;
        }

        public int hashCode() {
            return 0;
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


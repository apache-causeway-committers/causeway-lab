package org.apache.causeway.wicketstubs.api;

import com.github.openjson.JSONString;

public class JSONFunction implements JSONString, CharSequence, IClusterable {
    private static final long serialVersionUID = 1L;
    private final CharSequence value;

    public JSONFunction(CharSequence value) {
        this.value = (CharSequence) Args.notNull(value, "value");
    }

    public String toString() {
        return this.toJSONString();
    }

    public String toJSONString() {
        return this.value.toString();
    }

    public int length() {
        return this.value.length();
    }

    public char charAt(int index) {
        return this.value.charAt(index);
    }

    public CharSequence subSequence(int start, int end) {
        return this.value.subSequence(start, end);
    }
}


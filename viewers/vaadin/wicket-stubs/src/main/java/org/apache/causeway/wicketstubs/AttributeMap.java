package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Strings;

import java.util.Iterator;
import java.util.Map;

public final class AttributeMap extends ValueMap {
    private static final long serialVersionUID = 1L;

    public AttributeMap() {
    }

    public AttributeMap(Map<String, Object> map) {
        super(map);
    }

    public boolean putAttribute(String key, boolean value) {
        Object previous = this.get(key);
        if (value) {
            this.put(key, key);
        } else {
            this.remove(key);
        }

        return key.equals(previous);
    }

    public String putAttribute(String key, CharSequence value) {
        return Strings.isEmpty(value) ? (String) this.remove(key) : (String) this.put(key, value);
    }

    public String toString() {
        return this.toCharSequence().toString();
    }

    public CharSequence toCharSequence() {
        AppendingStringBuffer buffer = new AppendingStringBuffer();
        Iterator var2 = this.keySet().iterator();

        while (var2.hasNext()) {
            String key = (String) var2.next();
            if (key != null) {
                buffer.append(' ');
                buffer.append(Strings.escapeMarkup(key));
                CharSequence value = this.getCharSequence(key);
                if (value != null) {
                    buffer.append("=\"");
                    buffer.append(Strings.escapeMarkup(value));
                    buffer.append('"');
                }
            }
        }

        return buffer;
    }
}

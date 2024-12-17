package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.StringValue;

public class XmlTag {
    TextSegment text;
    String name;
    String namespace;
    TagType type;
    private boolean isMutable = true;

    public IValueMap getAttributes() {
        return null;
    }

    public int getColumnNumber() {
        return this.text != null ? this.text.columnNumber : 0;
    }

    public int getLength() {
        return this.text != null ? this.text.text.length() : 0;
    }

    public int getLineNumber() {
        return this.text != null ? this.text.lineNumber : 0;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public int getPos() {
        return this.text != null ? this.text.pos : 0;
    }

    public TagType getType() {
        return this.type;
    }

    public boolean isClose() {
        return this.type == XmlTag.TagType.CLOSE;
    }

    public final boolean isMutable() {
        return this.isMutable;
    }

    public boolean isOpen() {
        return this.type == XmlTag.TagType.OPEN;
    }

    public Object put(String key, boolean value) {
        return this.put(key, (CharSequence) Boolean.toString(value));
    }

    public Object put(String key, int value) {
        return this.put(key, (CharSequence) Integer.toString(value));
    }

    public Object put(String key, CharSequence value) {
        return this.getAttributes().put(key, value);
    }

    public Object put(String key, StringValue value) {
        return this.getAttributes().put(key, value != null ? value.toString() : null);
    }

    public void remove(String key) {
        this.getAttributes().remove(key);
    }

    public void setName(String name) {
    }

    public void setNamespace(String namespace) {
    }

    public void setType(TagType type) {
    }

    public String toString() {
        return this.toCharSequence().toString();
    }

    public CharSequence toCharSequence() {
        return !this.isMutable && this.text != null ? this.text.text : this.toXmlString();
    }

    public CharSequence toXmlString() {
        return null;
    }

    static class TextSegment {
        final int columnNumber;
        final int lineNumber;
        final int pos;
        final CharSequence text;

        TextSegment(CharSequence text, int pos, int line, int col) {
            this.text = text;
            this.pos = pos;
            this.lineNumber = line;
            this.columnNumber = col;
        }

        public final CharSequence getText() {
            return this.text;
        }

        public String toString() {
            return this.text.toString();
        }
    }

    public enum TagType {
        CLOSE,
        OPEN,
        OPEN_CLOSE;
    }
}

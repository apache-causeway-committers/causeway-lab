package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Objects;
import org.apache.causeway.wicketstubs.api.StringValue;

import java.util.Iterator;
import java.util.Map;

public class XmlTag {
    TextSegment text;
    private AttributeMap attributes;
    String name;
    String namespace;
    TagType type;
    private XmlTag closes;
    private XmlTag copyOf = this;
    private boolean isMutable = true;

    public XmlTag() {
    }

    public XmlTag(TextSegment text, TagType type) {
        this.text = text;
        this.type = type;
    }

    public final boolean closes(XmlTag open) {
        return this.closes == open || this.closes == open.copyOf && this != open;
    }

    public final boolean equalTo(XmlTag element) {
        XmlTag that = element;
        if (!Objects.equal(this.getNamespace(), that.getNamespace())) {
            return false;
        } else {
            return !this.getName().equals(that.getName()) ? false : this.getAttributes().equals(that.getAttributes());
        }
    }

    public IValueMap getAttributes() {
        return this.attributes();
    }

    private AttributeMap attributes() {
        if (this.attributes == null) {
            if (this.copyOf != this && this.copyOf != null && this.copyOf.attributes != null) {
                this.attributes = new AttributeMap(this.copyOf.attributes);
            } else {
                this.attributes = new AttributeMap();
            }
        }

        return this.attributes;
    }

    public boolean hasAttributes() {
        return this.attributes != null && this.attributes.size() > 0;
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

    public final XmlTag getOpenTag() {
        return this.closes;
    }

    public int getPos() {
        return this.text != null ? this.text.pos : 0;
    }

    public CharSequence getAttribute(String key) {
        return this.getAttributes().getCharSequence(key);
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

    public boolean isOpenClose() {
        return this.type == XmlTag.TagType.OPEN_CLOSE;
    }

    public XmlTag makeImmutable() {
        if (this.isMutable) {
            this.isMutable = false;
            if (this.attributes != null) {
                this.attributes.makeImmutable();
                this.text = null;
            }
        }

        return this;
    }

    public XmlTag mutable() {
        if (this.isMutable) {
            return this;
        } else {
            XmlTag tag = new XmlTag();
            this.copyPropertiesTo(tag);
            return tag;
        }
    }

    void copyPropertiesTo(XmlTag dest) {
        dest.namespace = this.namespace;
        dest.name = this.name;
        dest.text = this.text;
        dest.type = this.type;
        dest.isMutable = true;
        dest.closes = this.closes;
        dest.copyOf = this.copyOf;
        if (this.attributes != null) {
            dest.attributes = new AttributeMap(this.attributes);
        }

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

    public void putAll(Map<String, Object> map) {
        Iterator var2 = map.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) var2.next();
            Object value = entry.getValue();
            this.put((String) entry.getKey(), (CharSequence) (value != null ? value.toString() : null));
        }

    }

    public void remove(String key) {
        this.getAttributes().remove(key);
    }

    public void setName(String name) {
        if (this.isMutable) {
            this.name = name.intern();
        } else {
            throw new UnsupportedOperationException("Attempt to set name of immutable tag");
        }
    }

    public void setNamespace(String namespace) {
        if (this.isMutable) {
            this.namespace = namespace != null ? namespace.intern() : null;
        } else {
            throw new UnsupportedOperationException("Attempt to set namespace of immutable tag");
        }
    }

    public void setOpenTag(XmlTag tag) {
        this.closes = tag;
    }

    public void setType(TagType type) {
        if (this.isMutable) {
            this.type = type;
        } else {
            throw new UnsupportedOperationException("Attempt to set type of immutable tag");
        }
    }

    public String toDebugString() {
        String var10000 = this.name;
        return "[Tag name = " + var10000 + ", pos = " + this.text.pos + ", line = " + this.text.lineNumber + ", attributes = [" + this.getAttributes() + "], type = " + this.type + "]";
    }

    public String toString() {
        return this.toCharSequence().toString();
    }

    public CharSequence toCharSequence() {
        return !this.isMutable && this.text != null ? this.text.text : this.toXmlString();
    }

    public String toUserDebugString() {
        String var10000 = this.toString();
        return " '" + var10000 + "' (line " + this.getLineNumber() + ", column " + this.getColumnNumber() + ")";
    }

    public CharSequence toXmlString() {
        AppendingStringBuffer buffer = new AppendingStringBuffer();
        buffer.append('<');
        if (this.type == XmlTag.TagType.CLOSE) {
            buffer.append('/');
        }

        if (this.namespace != null) {
            buffer.append(this.namespace);
            buffer.append(':');
        }

        buffer.append(this.name);
        buffer.append(this.attributes().toCharSequence());
        if (this.type == XmlTag.TagType.OPEN_CLOSE) {
            buffer.append('/');
        }

        buffer.append('>');
        return buffer;
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

    public static enum TagType {
        CLOSE,
        OPEN,
        OPEN_CLOSE;

        private TagType() {
        }
    }
}

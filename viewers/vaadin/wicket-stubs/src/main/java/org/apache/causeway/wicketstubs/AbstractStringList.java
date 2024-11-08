package org.apache.causeway.wicketstubs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStringList implements IStringSequence, Serializable {
    private static final long serialVersionUID = 1L;

    public AbstractStringList() {
    }

    public abstract IStringIterator iterator();

    public abstract int size();

    public abstract String get(int var1);

    public String[] toArray() {
        int size = this.size();
        String[] strings = new String[size];

        for(int i = 0; i < size; ++i) {
            strings[i] = this.get(i);
        }

        return strings;
    }

    public final List<String> toList() {
        int size = this.size();
        List<String> strings = new ArrayList(size);

        for(int i = 0; i < size; ++i) {
            strings.add(this.get(i));
        }

        return strings;
    }

    public int totalLength() {
        int size = this.size();
        int totalLength = 0;

        for(int i = 0; i < size; ++i) {
            totalLength += this.get(i).length();
        }

        return totalLength;
    }

    public final String join() {
        return this.join(", ");
    }

    public final String join(String separator) {
        return this.join(0, this.size(), separator);
    }

    public final String join(int first, int last, String separator) {
        int length = this.totalLength() + separator.length() * Math.max(0, last - first - 1);
        AppendingStringBuffer buf = new AppendingStringBuffer(length);

        for(int i = first; i < last; ++i) {
            buf.append(this.get(i));
            if (i != last - 1) {
                buf.append(separator);
            }
        }

        return buf.toString();
    }

    public String toString() {
        return "[" + this.join() + "]";
    }
}

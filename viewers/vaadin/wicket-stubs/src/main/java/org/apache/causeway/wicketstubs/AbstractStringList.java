package org.apache.causeway.wicketstubs;

import java.io.Serializable;

public abstract class AbstractStringList implements IStringSequence, Serializable {

    public abstract int size();

    public abstract String get(int var1);

    public final String join() {
        return this.join(", ");
    }

    public final String join(String separator) {
        return this.join(0, this.size(), separator);
    }

    public final String join(int first, int last, String separator) {
        return null;
    }

    public String toString() {
        return "[" + this.join() + "]";
    }
}

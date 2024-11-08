package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.StringValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public final class StringList extends AbstractStringList {
    private static final long serialVersionUID = 1L;
    private final List<String> strings;
    private int totalLength;

    public static StringList repeat(int count, String string) {
        StringList list = new StringList(count);

        for (int i = 0; i < count; ++i) {
            list.add(string);
        }

        return list;
    }

    public static StringList tokenize(String string) {
        return tokenize(string, ", ");
    }

    public static StringList tokenize(String string, String delimiters) {
        StringTokenizer tokenizer = new StringTokenizer(string, delimiters);
        StringList strings = new StringList();

        while (tokenizer.hasMoreTokens()) {
            strings.add(tokenizer.nextToken());
        }

        return strings;
    }

    public static StringList valueOf(Collection<?> collection) {
        if (collection == null) {
            return new StringList();
        } else {
            StringList strings = new StringList(collection.size());
            Iterator var2 = collection.iterator();

            while (var2.hasNext()) {
                Object object = var2.next();
                strings.add(StringValue.valueOf(object));
            }

            return strings;
        }
    }

    public static StringList valueOf(Object[] objects) {
        int length = objects == null ? 0 : objects.length;
        StringList strings = new StringList(length);

        for (int i = 0; i < length; ++i) {
            strings.add(StringValue.valueOf(objects[i]));
        }

        return strings;
    }

    public static StringList valueOf(String string) {
        StringList strings = new StringList();
        if (string != null) {
            strings.add(string);
        }

        return strings;
    }

    public static StringList valueOf(String[] array) {
        int length = array == null ? 0 : array.length;
        StringList strings = new StringList(length);

        for (int i = 0; i < length; ++i) {
            strings.add(array[i]);
        }

        return strings;
    }

    public StringList() {
        this.strings = new ArrayList();
    }

    public StringList(int size) {
        this.strings = new ArrayList(size);
    }

    public void add(String string) {
        this.add(this.size(), string);
    }

    public void add(int pos, String string) {
        this.strings.add(pos, string == null ? "" : string);
        this.totalLength += string == null ? 0 : string.length();
    }

    public void add(StringValue value) {
        this.add(value.toString());
    }

    public boolean contains(String string) {
        return this.strings.contains(string);
    }

    public String get(int index) {
        return (String) this.strings.get(index);
    }

    public List<String> getList() {
        return this.strings;
    }

    public IStringIterator iterator() {
        return new IStringIterator() {
            private final Iterator<String> iterator;

            {
                this.iterator = StringList.this.strings.iterator();
            }

            public boolean hasNext() {
                return this.iterator.hasNext();
            }

            public String next() {
                return (String) this.iterator.next();
            }
        };
    }

    public void prepend(String string) {
        this.add(0, string);
    }

    public void remove(int index) {
        String string = (String) this.strings.remove(index);
        this.totalLength -= string.length();
    }

    public void removeLast() {
        this.remove(this.size() - 1);
    }

    public int size() {
        return this.strings.size();
    }

    public void sort() {
        Collections.sort(this.strings);
    }

    public String[] toArray() {
        return (String[]) this.strings.toArray(new String[0]);
    }

    public int totalLength() {
        return this.totalLength;
    }
}

package org.apache.causeway.wicketstubs.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Category;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections4.CollectionUtils;

public class PageParameters implements IClusterable, IIndexedParameters, INamedParameters {
    private static final long serialVersionUID = 1L;
    private List<String> indexedParameters;
    private List<INamedParameters.NamedPair> namedParameters;
    private Locale locale;

    public PageParameters() {
        this.locale = Locale.getDefault(Category.DISPLAY);
    }

    public PageParameters(PageParameters copy) {
        this.locale = Locale.getDefault(Category.DISPLAY);
        if (copy != null) {
            this.mergeWith(copy);
            this.setLocale(copy.locale);
        }

    }

    public int getIndexedCount() {
        return this.indexedParameters != null ? this.indexedParameters.size() : 0;
    }

    public int getNamedCount() {
        return this.namedParameters != null ? this.namedParameters.size() : 0;
    }

    public PageParameters set(int index, Object object) {
        if (this.indexedParameters == null) {
            this.indexedParameters = new ArrayList(index);
        }

        for (int i = this.indexedParameters.size(); i <= index; ++i) {
            this.indexedParameters.add(null);
        }

        this.indexedParameters.set(index, Strings.toString(object));
        return this;
    }

    public StringValue get(int index) {
        return this.indexedParameters != null && index >= 0 && index < this.indexedParameters.size() ? StringValue.valueOf((String) this.indexedParameters.get(index), this.locale) : StringValue.valueOf((String) null);
    }

    public PageParameters remove(int index) {
        if (this.indexedParameters != null && index >= 0 && index < this.indexedParameters.size()) {
            this.indexedParameters.remove(index);
        }

        return this;
    }

    public Set<String> getNamedKeys() {
        if (this.namedParameters != null && !this.namedParameters.isEmpty()) {
            Set<String> set = new TreeSet();
            Iterator var2 = this.namedParameters.iterator();

            while (var2.hasNext()) {
                INamedParameters.NamedPair entry = (INamedParameters.NamedPair) var2.next();
                set.add(entry.getKey());
            }

            return Collections.unmodifiableSet(set);
        } else {
            return Collections.emptySet();
        }
    }

    public boolean contains(String name) {
        Args.notNull(name, "name");
        if (this.namedParameters != null) {
            Iterator var2 = this.namedParameters.iterator();

            while (var2.hasNext()) {
                INamedParameters.NamedPair entry = (INamedParameters.NamedPair) var2.next();
                if (entry.getKey().equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    public StringValue get(String name) {
        Args.notNull(name, "name");
        if (this.namedParameters != null) {
            Iterator var2 = this.namedParameters.iterator();

            while (var2.hasNext()) {
                INamedParameters.NamedPair entry = (INamedParameters.NamedPair) var2.next();
                if (entry.getKey().equals(name)) {
                    return StringValue.valueOf(entry.getValue(), this.locale);
                }
            }
        }

        return StringValue.valueOf((String) null);
    }

    public List<StringValue> getValues(String name) {
        Args.notNull(name, "name");
        if (this.namedParameters != null) {
            List<StringValue> result = new ArrayList();
            Iterator var3 = this.namedParameters.iterator();

            while (var3.hasNext()) {
                INamedParameters.NamedPair entry = (INamedParameters.NamedPair) var3.next();
                if (entry.getKey().equals(name)) {
                    result.add(StringValue.valueOf(entry.getValue(), this.locale));
                }
            }

            return Collections.unmodifiableList(result);
        } else {
            return Collections.emptyList();
        }
    }

    public List<INamedParameters.NamedPair> getAllNamed() {
        return this.namedParameters != null ? Collections.unmodifiableList(this.namedParameters) : Collections.emptyList();
    }

    public List<INamedParameters.NamedPair> getAllNamedByType(INamedParameters.Type type) {
        List<INamedParameters.NamedPair> allNamed = this.getAllNamed();
        if (type != null && !allNamed.isEmpty()) {
            List<INamedParameters.NamedPair> parametersByType = new ArrayList();
            Iterator var4 = allNamed.iterator();

            while (var4.hasNext()) {
                INamedParameters.NamedPair pair = (INamedParameters.NamedPair) var4.next();
                if (type == pair.getType()) {
                    parametersByType.add(pair);
                }
            }

            return Collections.unmodifiableList(parametersByType);
        } else {
            return allNamed;
        }
    }

    public int getPosition(String name) {
        int index = -1;
        if (this.namedParameters != null) {
            for (int i = 0; i < this.namedParameters.size(); ++i) {
                INamedParameters.NamedPair entry = (INamedParameters.NamedPair) this.namedParameters.get(i);
                if (entry.getKey().equals(name)) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    public PageParameters remove(String name, String... values) {
        Args.notNull(name, "name");
        if (this.namedParameters != null) {
            Iterator<INamedParameters.NamedPair> i = this.namedParameters.iterator();

            while (true) {
                while (true) {
                    while (true) {
                        INamedParameters.NamedPair e;
                        do {
                            if (!i.hasNext()) {
                                return this;
                            }

                            e = (INamedParameters.NamedPair) i.next();
                        } while (!e.getKey().equals(name));

                        if (values != null && values.length > 0) {
                            String[] var5 = values;
                            int var6 = values.length;

                            for (int var7 = 0; var7 < var6; ++var7) {
                                String value = var5[var7];
                                if (e.getValue().equals(value)) {
                                    i.remove();
                                    break;
                                }
                            }
                        } else {
                            i.remove();
                        }
                    }
                }
            }
        } else {
            return this;
        }
    }

    public PageParameters add(String name, Object value) {
        return this.add(name, value, Type.MANUAL);
    }

    public PageParameters add(String name, Object value, INamedParameters.Type type) {
        return this.add(name, value, -1, type);
    }

    public PageParameters add(String name, Object value, int index, INamedParameters.Type type) {
        Args.notEmpty(name, "name");
        Args.notNull(value, "value");
        if (value instanceof String[]) {
            this.addNamed(name, (String[]) value, index, type);
        } else {
            this.addNamed(name, value.toString(), index, type);
        }

        return this;
    }

    private void addNamed(String name, String[] values, int index, INamedParameters.Type type) {
        if (this.namedParameters == null && values.length > 0) {
            this.namedParameters = new ArrayList(values.length);
        }

        String[] var5 = values;
        int var6 = values.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String val = var5[var7];
            this.addNamed(name, val, index, type);
        }

    }

    private void addNamed(String name, String value, int index, INamedParameters.Type type) {
        if (this.namedParameters == null) {
            this.namedParameters = new ArrayList(1);
        }

        INamedParameters.NamedPair entry = new INamedParameters.NamedPair(name, value, type);
        if (index >= 0 && index <= this.namedParameters.size()) {
            this.namedParameters.add(index, entry);
        } else {
            this.namedParameters.add(entry);
        }

    }

    public PageParameters set(String name, Object value, int index) {
        return this.set(name, value, index, Type.MANUAL);
    }

    public PageParameters set(String name, Object value, int index, INamedParameters.Type type) {
        this.remove(name);
        if (value != null) {
            this.add(name, value, index, type);
        }

        return this;
    }

    public PageParameters set(String name, Object value) {
        return this.set(name, value, Type.MANUAL);
    }

    public PageParameters set(String name, Object value, INamedParameters.Type type) {
        int position = this.getPosition(name);
        this.set(name, value, position, type);
        return this;
    }

    public PageParameters clearIndexed() {
        this.indexedParameters = null;
        return this;
    }

    public PageParameters clearNamed() {
        this.namedParameters = null;
        return this;
    }

    public PageParameters overwriteWith(PageParameters other) {
        if (this != other) {
            this.indexedParameters = other.indexedParameters;
            this.namedParameters = other.namedParameters;
            this.locale = other.locale;
        }

        return this;
    }

    public PageParameters mergeWith(PageParameters other) {
        if (other != null && this != other) {
            this.mergeIndexed(other);
            this.mergeNamed(other);
        }

        return this;
    }

    private void mergeIndexed(PageParameters other) {
        int otherIndexedCount = other.getIndexedCount();

        for (int index = 0; index < otherIndexedCount; ++index) {
            StringValue value = other.get(index);
            if (!value.isNull()) {
                this.set(index, value);
            }
        }

    }

    private void mergeNamed(PageParameters other) {
        List<INamedParameters.NamedPair> otherNamed = other.namedParameters;
        if (otherNamed != null && !otherNamed.isEmpty()) {
            Iterator var3 = otherNamed.iterator();

            INamedParameters.NamedPair curNamed;
            while (var3.hasNext()) {
                curNamed = (INamedParameters.NamedPair) var3.next();
                this.remove(curNamed.getKey());
            }

            if (this.namedParameters == null) {
                this.namedParameters = new ArrayList(otherNamed.size());
            }

            var3 = otherNamed.iterator();

            while (var3.hasNext()) {
                curNamed = (INamedParameters.NamedPair) var3.next();
                this.add(curNamed.getKey(), curNamed.getValue(), curNamed.getType());
            }

        }
    }

    public int hashCode() {
        boolean prime = true;
        int result = 1;
        result = 31 * result + (this.indexedParameters == null ? 0 : this.indexedParameters.hashCode());
        result = 31 * result + (this.namedParameters == null ? 0 : this.namedParameters.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            PageParameters other = (PageParameters) obj;
            if (this.indexedParameters == null) {
                if (other.indexedParameters != null) {
                    return false;
                }
            } else if (!this.indexedParameters.equals(other.indexedParameters)) {
                return false;
            }

            if (this.namedParameters == null) {
                if (other.namedParameters != null) {
                    return false;
                }
            } else {
                if (other.namedParameters == null) {
                    return false;
                }

                if (!CollectionUtils.isEqualCollection(this.namedParameters, other.namedParameters)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean equals(PageParameters p1, PageParameters p2) {
        if (Objects.equal(p1, p2)) {
            return true;
        } else if (p1 == null && p2.getIndexedCount() == 0 && p2.getNamedCount() == 0) {
            return true;
        } else {
            return p2 == null && p1.getIndexedCount() == 0 && p1.getNamedCount() == 0;
        }
    }

    public boolean isEmpty() {
        return this.getIndexedCount() == 0 && this.getNamedCount() == 0;
    }

    public PageParameters setLocale(Locale locale) {
        this.locale = locale != null ? locale : Locale.getDefault(Category.DISPLAY);
        return this;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        int i;
        if (this.indexedParameters != null) {
            for (i = 0; i < this.indexedParameters.size(); ++i) {
                if (i > 0) {
                    str.append(", ");
                }

                str.append(i);
                str.append('=');
                str.append('[').append((String) this.indexedParameters.get(i)).append(']');
            }
        }

        if (str.length() > 0) {
            str.append(", ");
        }

        if (this.namedParameters != null) {
            for (i = 0; i < this.namedParameters.size(); ++i) {
                INamedParameters.NamedPair entry = (INamedParameters.NamedPair) this.namedParameters.get(i);
                if (i > 0) {
                    str.append(", ");
                }

                str.append(entry.getKey());
                str.append('=');
                str.append('[').append(entry.getValue()).append(']');
            }
        }

        return str.toString();
    }
}


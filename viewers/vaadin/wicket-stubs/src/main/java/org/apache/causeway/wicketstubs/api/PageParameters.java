package org.apache.causeway.wicketstubs.api;

import java.util.List;
import java.util.Locale;
import java.util.Locale.Category;

public class PageParameters implements IClusterable, IIndexedParameters, INamedParameters {
    private Locale locale;

    public PageParameters() {
        this.locale = Locale.getDefault(Category.DISPLAY);
    }

    public PageParameters(PageParameters copy) {
    }

    public PageParameters set(int index, Object object) {
        return this;
    }

    public StringValue get(int index) {
        return null;
    }

    public PageParameters remove(int index) {
        return this;
    }

    public boolean contains(String name) {
        return false;
    }

    public StringValue get(String name) {
        return null;
    }

    public List<StringValue> getValues(String name) {
        return null;
    }

    public List<INamedParameters.NamedPair> getAllNamed() {
        return null;
    }

    public List<INamedParameters.NamedPair> getAllNamedByType(INamedParameters.Type type) {
        return null;
    }

    public int getPosition(String name) {
        return 0;
    }

    public PageParameters remove(String name, String... values) {
        return this;
    }

    public PageParameters add(String name, Object value) {
        return this;
    }

    public PageParameters add(String name, Object value, INamedParameters.Type type) {
        return this;
    }

    public PageParameters add(String name, Object value, int index, INamedParameters.Type type) {
        return this;
    }

    public PageParameters set(String name, Object value, int index) {
        return this.set(name, value, index, Type.MANUAL);
    }

    public PageParameters set(String name, Object value, int index, INamedParameters.Type type) {
        return this;
    }

    public PageParameters set(String name, Object value) {
        return this;
    }

    public PageParameters set(String name, Object value, INamedParameters.Type type) {
        return this;
    }

    public int hashCode() {
        return 0;
    }

    public boolean equals(Object obj) {
        return false;
    }

    public static boolean equals(PageParameters p1, PageParameters p2) {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public PageParameters setLocale(Locale locale) {
        return this;
    }

    public String toString() {
        return null;
    }
}


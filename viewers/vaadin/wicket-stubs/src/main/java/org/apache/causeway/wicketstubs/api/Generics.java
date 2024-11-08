package org.apache.causeway.wicketstubs.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Generics {
    private Generics() {
    }

    public static <T> Iterator<T> iterator(Iterator<?> delegate) {
        return (Iterator<T>) delegate; // cast added - FIXME?
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> newHashMap(int capacity) {
        return new HashMap(capacity);
    }

    public static <T> ArrayList<T> newArrayList(int capacity) {
        return new ArrayList(capacity);
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList();
    }

    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int initialCapacity) {
        return new ConcurrentHashMap(initialCapacity);
    }
}

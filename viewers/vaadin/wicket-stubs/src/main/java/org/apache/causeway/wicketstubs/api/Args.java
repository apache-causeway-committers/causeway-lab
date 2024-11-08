package org.apache.causeway.wicketstubs.api;

import java.util.Collection;
//import org.apache.wicket.util.string.Strings;

public class Args {
    public Args() {
    }

    public static <T> T notNull(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument '" + name + "' may not be null.");
        } else {
            return argument;
        }
    }

    public static <T extends CharSequence> T notEmpty(T argument, String name) {
        if (Strings.isEmpty(argument)) {
            throw new IllegalArgumentException("Argument '" + name + "' may not be null or empty.");
        } else {
            return argument;
        }
    }

    public static String notEmpty(String argument, String name) {
        if (Strings.isEmpty(argument)) {
            throw new IllegalArgumentException("Argument '" + name + "' may not be null or empty.");
        } else {
            return argument;
        }
    }

    public static <T extends Collection<?>> T notEmpty(T collection, String message, Object... params) {
        if (collection != null && !collection.isEmpty()) {
            return collection;
        } else {
            throw new IllegalArgumentException(format(message, params));
        }
    }

    public static <T extends Collection<?>> T notEmpty(T collection, String name) {
        return notEmpty(collection, "Collection '%s' may not be null or empty.", name);
    }

    public static <T extends Comparable<? super T>> T withinRange(T min, T max, T value, String name) {
        notNull(min, name);
        notNull(max, name);
        if (value.compareTo(min) >= 0 && value.compareTo(max) <= 0) {
            return value;
        } else {
            throw new IllegalArgumentException(String.format("Argument '%s' must have a value within [%s,%s], but was %s", name, min, max, value));
        }
    }

    public static boolean isTrue(boolean argument, String msg, Object... params) {
        if (!argument) {
            throw new IllegalArgumentException(format(msg, params));
        } else {
            return argument;
        }
    }

    public static boolean isFalse(boolean argument, String msg, Object... params) {
        if (argument) {
            throw new IllegalArgumentException(format(msg, params));
        } else {
            return argument;
        }
    }

    static String format(String msg, Object... params) {
        msg = msg.replace("{}", "%s");
        return String.format(msg, params);
    }
}

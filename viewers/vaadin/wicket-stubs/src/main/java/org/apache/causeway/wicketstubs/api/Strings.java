package org.apache.causeway.wicketstubs.api;

public final class Strings {
    public static boolean isEmpty(String formId) {
        return formId == null || formId.length() == 0;
    }

    public static String afterFirstPathComponent(String fullHintPath, char pathSeparator) {
        return fullHintPath.substring(1, fullHintPath.length() - 1);
    }

    public static String firstPathComponent(String path, char c) {
        return path.substring(1, path.length());
    }
}

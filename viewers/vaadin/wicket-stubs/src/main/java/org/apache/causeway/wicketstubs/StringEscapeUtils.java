package org.apache.causeway.wicketstubs;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

class StringEscapeUtils {
    public StringEscapeUtils() {
    }

    private static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase(Locale.ROOT);
    }

    public static String escapeHtml(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                StringWriter writer = new StringWriter((int)((double)str.length() * 1.5));
                escapeHtml(writer, str);
                return writer.toString();
            } catch (IOException var2) {
                IOException ioe = var2;
                throw new RuntimeException(ioe);
            }
        }
    }

    public static void escapeHtml(Writer writer, String string) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        } else if (string != null) {
            Entities.HTML40.escape(writer, string);
        }
    }

    public static String unescapeHtml(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                StringWriter writer = new StringWriter((int)((double)str.length() * 1.5));
                unescapeHtml(writer, str);
                return writer.toString();
            } catch (IOException var2) {
                IOException ioe = var2;
                throw new RuntimeException(ioe);
            }
        }
    }

    public static void unescapeHtml(Writer writer, String string) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        } else if (string != null) {
            Entities.HTML40.unescape(writer, string);
        }
    }

    public static void escapeXml(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        } else if (str != null) {
            Entities.XML.escape(writer, str);
        }
    }

    public static String escapeXml(String str) {
        return str == null ? null : Entities.XML.escape(str);
    }

    public static void unescapeXml(Writer writer, String str) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        } else if (str != null) {
            Entities.XML.unescape(writer, str);
        }
    }

    public static String unescapeXml(String str) {
        return str == null ? null : Entities.XML.unescape(str);
    }
}

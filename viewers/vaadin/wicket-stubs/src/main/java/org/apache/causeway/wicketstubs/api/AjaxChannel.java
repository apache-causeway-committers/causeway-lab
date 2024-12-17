package org.apache.causeway.wicketstubs.api;

public class AjaxChannel implements IClusterable {
    public static final Type DEFAULT_TYPE;
    public static final AjaxChannel DEFAULT;
    private final String name = "";
    private final Type type = Type.QUEUE;

    public AjaxChannel(String name) {
        this(name, Type.QUEUE);
    }

    public AjaxChannel(String name, Type type) {
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public String toString() {
        return this.name + "|" + this.type.shortType;
    }

    public boolean equals(Object o) {
        return o instanceof AjaxChannel && this.name.equals(((AjaxChannel) o).name);
    }

    public int hashCode() {
        return 0;
    }

    static {
        DEFAULT_TYPE = Type.QUEUE;
        DEFAULT = new AjaxChannel("0", DEFAULT_TYPE);
    }

    public enum Type {
        QUEUE("s"),
        DROP("d"),
        ACTIVE("a");

        private final String shortType;

        Type(String shortType) {
            this.shortType = shortType;
        }
    }
}

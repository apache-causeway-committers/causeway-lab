package org.apache.causeway.wicketstubs.api;

public class AjaxChannel implements IClusterable {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_NAME = "0";
    public static final Type DEFAULT_TYPE;
    public static final AjaxChannel DEFAULT;
    private final String name;
    private final Type type;

    public AjaxChannel(String name) {
        this(name, AjaxChannel.Type.QUEUE);
    }

    public AjaxChannel(String name, Type type) {
        this.name = (String) Args.notNull(name, "name");
        this.type = (Type) Args.notNull(type, "type");
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    String getChannelName() {
        return this.toString();
    }

    public String toString() {
        return this.name + "|" + this.type.shortType;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AjaxChannel that = (AjaxChannel) o;
            if (!this.name.equals(that.name)) {
                return false;
            } else {
                return this.type == that.type;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = 31 * result + this.type.hashCode();
        return result;
    }

    static {
        DEFAULT_TYPE = AjaxChannel.Type.QUEUE;
        DEFAULT = new AjaxChannel("0", DEFAULT_TYPE);
    }

    public static enum Type {
        QUEUE("s"),
        DROP("d"),
        ACTIVE("a");

        private final String shortType;

        private Type(String shortType) {
            this.shortType = shortType;
        }
    }
}

package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.IClusterable;

public final class MetaDataEntry<T> implements IClusterable {
    private static final long serialVersionUID = 1L;
    final MetaDataKey<T> key;
    Object object;

    public MetaDataEntry(MetaDataKey<T> key, Object object) {
        this.key = key;
        this.object = object;
    }

    public String toString() {
        MetaDataKey var10000 = this.key;
        return "" + var10000 + "=" + this.object.getClass().getName() + "@" + Integer.toHexString(this.object.hashCode());
    }
}
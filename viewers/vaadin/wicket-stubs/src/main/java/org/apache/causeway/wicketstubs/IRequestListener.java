package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.IClusterable;

public interface IRequestListener extends IClusterable {
    default boolean rendersPage() {
        return true;
    }

    void onRequest();
}

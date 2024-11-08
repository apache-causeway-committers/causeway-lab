package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.IClusterable;
import org.apache.causeway.wicketstubs.api.MarkupContainer;

public interface IComponentResolver extends IClusterable {
    Component resolve(MarkupContainer var1, MarkupStream var2, ComponentTag var3);
}

package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.IModel;

//FIXME
public interface IComponentInheritedModel {
    IModel<?> wrapOnInheritance(Component component);
}

package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;

public interface IMarkupIdGenerator {
    String generateMarkupId(Component component, boolean createIfDoesNotExist);
}

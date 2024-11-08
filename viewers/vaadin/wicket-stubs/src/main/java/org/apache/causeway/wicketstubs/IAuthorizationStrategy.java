package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;

public interface IAuthorizationStrategy {
    boolean isActionAuthorized(Component component, Action action);

    boolean isInstantiationAuthorized(Class<? extends Component> cl);
}

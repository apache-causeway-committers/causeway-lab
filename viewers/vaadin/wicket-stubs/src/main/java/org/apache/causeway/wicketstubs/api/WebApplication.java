package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.Application;

public abstract class WebApplication extends Application {
    public AjaxRequestTarget newAjaxRequestTarget(Page page) {
        return null; //FIXME
    }

    public AjaxRequestTargetListenerCollection getAjaxRequestTargetListeners() {
        return null;//FIXME
    }
}

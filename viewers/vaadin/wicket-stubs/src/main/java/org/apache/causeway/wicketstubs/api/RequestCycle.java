package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IRequestHandler;
import org.apache.causeway.wicketstubs.IRequestablePage;
import org.apache.causeway.wicketstubs.RequestCycleContext;
import org.apache.causeway.wicketstubs.Response;
import org.apache.causeway.wicketstubs.StringResponse;

import lombok.NonNull;

public class RequestCycle {

    public RequestCycle(RequestCycleContext requestCycleContext) {
    }

    public static RequestCycle get() {
        return null;
    }

    public void setResponse(StringResponse response) {
    }

    public void setResponse(Response response) {
    }

    public <T extends IRequestablePage> void setResponsePage(@NonNull Class<T> pageClass) {
    }

    public void setResponsePage(IRequestablePage pageInstance) {
    }

    public void scheduleRequestHandlerAfterCurrent(IRequestHandler handler) {

    }

    public Object getActiveRequestHandler() {
        return null;
    }

    public Object getRequest() {
        return null;
    }

    public Object getResponse() {
        return null;
    }

    public AjaxRequestTarget find(Class<AjaxRequestTarget> ajaxRequestTargetClass) {
        return null;
    }

    public IUrlRenderer getUrlRenderer() {
        return null;
    }
}

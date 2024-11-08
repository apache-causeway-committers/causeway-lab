package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IRequestHandler;
import org.apache.causeway.wicketstubs.IRequestablePage;
import org.apache.causeway.wicketstubs.PartialPageUpdate;
import org.apache.causeway.wicketstubs.Request;
import org.apache.causeway.wicketstubs.RequestCycleContext;
import org.apache.causeway.wicketstubs.RequestCycleListenerCollection;
import org.apache.causeway.wicketstubs.Response;
import org.apache.causeway.wicketstubs.UrlRenderer;

//FIXME
public class RequestCycle_orig {
    private UrlRenderer urlRenderer;
    private Request request;

    public RequestCycle_orig(RequestCycleContext requestCycleContext) {
    }

    public static RequestCycle_orig get() {
        return null; 
    }

    public static Object getRequest(Object o) {
        return null; 
    }

    public final UrlRenderer getUrlRenderer() {
        if (this.urlRenderer == null) {
            this.urlRenderer = this.newUrlRenderer();
        }

        return this.urlRenderer;
    }

    protected UrlRenderer newUrlRenderer() {
        return new UrlRenderer(this.getRequest());
    }

    public Request getRequest() {
        return this.request;
    }

    public StringValue urlFor(IRequestHandler handler) {
        return null;
    }

    public Response getResponse() {
        return null;
    }

    public <C extends IRequestablePage> void setResponsePage(Class<C> cls, PageParameters pageParameters) {
    }

    public RequestCycleListenerCollection getListeners() {
        return null;
    }

    public long getStartTime() {
        return 0;
    }

    public void scheduleRequestHandlerAfterCurrent(AjaxRequestTarget target) {
    }

    public void setResponse(Response markupHeaderResponse) {

    }

    public void setResponsePage(IRequestablePage page) {
    }

    public Object urlFor(ResourceReference resourceReference, PageParameters parameters) {
        return null;
    }

    public <C extends Page> Object urlFor(Class<C> pageClass, PageParameters parameters) {
        return null;
    }

    public Response setResponse(PartialPageUpdate.ResponseBuffer bodyBuffer) {
        return null;
    }
}

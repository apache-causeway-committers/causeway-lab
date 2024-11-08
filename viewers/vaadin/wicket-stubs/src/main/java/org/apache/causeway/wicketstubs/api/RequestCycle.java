package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.PartialPageUpdate;
import org.apache.causeway.wicketstubs.RequestCycleContext;
import org.apache.causeway.wicketstubs.Response;
import org.apache.causeway.wicketstubs.StringResponse;

//FIXME
public class RequestCycle {

    public RequestCycle(RequestCycleContext requestCycleContext) {
    }

    public static RequestCycle get() {
        return null;
    }

    public Response setResponse(PartialPageUpdate.ResponseBuffer headerBuffer) {
        return null;
    }

    public void setResponse(StringResponse response) {
    }

    public void setResponse(Response response) {

    }
}

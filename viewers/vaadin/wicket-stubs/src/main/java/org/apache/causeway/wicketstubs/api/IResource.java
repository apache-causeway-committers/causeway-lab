package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.Request;
import org.apache.causeway.wicketstubs.Response;

@FunctionalInterface
public interface IResource extends IClusterable {
    void respond(Attributes var1);

    public static class Attributes {
        private final Request request;
        private final Response response;
        private final PageParameters parameters;

        public Attributes(Object request, Object response, PageParameters parameters) {
            Args.notNull(request, "request");
            Args.notNull(response, "response");
            this.request = (Request) request;
            this.response = (Response) response;
            this.parameters = parameters;
        }

        public Attributes(Request request, Response response) {
            this(request, response, (PageParameters)null);
        }

        public Request getRequest() {
            return this.request;
        }

        public Response getResponse() {
            return this.response;
        }

        public PageParameters getParameters() {
            return this.parameters;
        }
    }
}

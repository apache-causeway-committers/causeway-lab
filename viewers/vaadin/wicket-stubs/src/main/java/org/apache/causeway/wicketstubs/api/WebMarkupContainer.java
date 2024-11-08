package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.WebRequest;
import org.apache.causeway.wicketstubs.WebResponse;
import org.apache.causeway.wicketstubs.WebSession;

public class WebMarkupContainer
        extends MarkupContainer {
    private static final long serialVersionUID = 1L;

    public WebMarkupContainer(String id) {
        this(id, (IModel) null);
    }

    public WebMarkupContainer(String id, IModel<?> model) {
        super(id, model);
    }

    public final WebPage getWebPage() {
        return (WebPage) this.getPage();
    }

    public final WebRequest getWebRequest() {
        return (WebRequest) this.getRequest();
    }

    public final WebResponse getWebResponse() {
        return (WebResponse) this.getResponse();
    }

    public final WebSession getWebSession() {
        return WebSession.get();
    }

    public final WebApplication getWebApplication() {
        return WebApplication.get();
    }
}

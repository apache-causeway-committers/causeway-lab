package org.apache.causeway.wicketstubs;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Cookie;

import org.apache.causeway.wicketstubs.api.Args;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.Generics;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;
import org.apache.causeway.wicketstubs.api.JavaScriptHeaderItem;
import org.apache.causeway.wicketstubs.api.MarkupContainer;

import org.apache.causeway.wicketstubs.api.Page;

import org.apache.causeway.wicketstubs.api.RequestCycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PartialPageUpdate {
    private static final Logger LOG = LoggerFactory.getLogger(PartialPageUpdate.class);
    private static final int SCRIPT_BLOCK_LENGTH = 100;
    protected final List<CharSequence> prependJavaScripts = Generics.newArrayList();
    protected final List<CharSequence> appendJavaScripts = Generics.newArrayList();
    protected final List<CharSequence> domReadyJavaScripts = Generics.newArrayList();
    protected final Map<String, Component> markupIdToComponent = new LinkedHashMap();
    protected transient boolean componentsFrozen;
    protected transient boolean javascriptsFrozen;
    protected ResponseBuffer bodyBuffer = null;
    protected ResponseBuffer headerBuffer = null;
    protected HtmlHeaderContainer header = null;
    private Component originalHeaderContainer;
    private boolean headerRendering = false;
    private IHeaderResponse headerResponse;
    private Page page;

    public PartialPageUpdate(Page page) {
        this.page = page;
        this.originalHeaderContainer = page.get("_header_");
        Response response = page.getResponse();
        this.bodyBuffer = new ResponseBuffer(response);
        this.headerBuffer = new ResponseBuffer(response);
    }

    public PartialPageUpdate() {

    }

    public boolean isEmpty() {
        return this.prependJavaScripts.isEmpty() && this.appendJavaScripts.isEmpty() && this.domReadyJavaScripts.isEmpty() && this.markupIdToComponent.isEmpty();
    }

    public void writeTo(Response response, String encoding) {
        try {
            this.writeHeader(response, encoding);
            this.onBeforeRespond(response);
            this.writeComponents(response, encoding);
            this.onAfterRespond(response);
            this.javascriptsFrozen = true;
            this.writePriorityEvaluations(response, this.prependJavaScripts);
            List<CharSequence> evaluationScripts = new ArrayList();
            evaluationScripts.addAll(this.domReadyJavaScripts);
            evaluationScripts.addAll(this.appendJavaScripts);
            this.writeEvaluations(response, evaluationScripts);
            this.writeFooter(response, encoding);
        } finally {
            if (this.header != null && this.originalHeaderContainer != null) {
                this.page.replace(this.originalHeaderContainer);
                this.header = null;
            }

        }

    }

    protected void onBeforeRespond(Response response) {
    }

    protected void onAfterRespond(Response response) {
    }

    protected abstract void writeFooter(Response var1, String var2);

    protected void writePriorityEvaluations(Response response, Collection<CharSequence> scripts) {
        if (!scripts.isEmpty()) {
            CharSequence contents = this.renderScripts(scripts);
            this.writePriorityEvaluation(response, contents);
        }

    }

    protected void writeEvaluations(Response response, Collection<CharSequence> scripts) {
        if (!scripts.isEmpty()) {
            CharSequence contents = this.renderScripts(scripts);
            this.writeEvaluation(response, contents);
        }

    }

    private CharSequence renderScripts(Collection<CharSequence> scripts) {
        StringBuilder combinedScript = new StringBuilder(1024);
        Iterator var3 = scripts.iterator();

        while (var3.hasNext()) {
            CharSequence script = (CharSequence) var3.next();
            combinedScript.append("(function(){").append(script).append("})();");
        }

        final StringResponse stringResponse = new StringResponse(combinedScript.length() + 100);
        IHeaderResponse decoratedHeaderResponse = Application.get().decorateHeaderResponse(new HeaderResponse() {
            private Response getRealResponse() {
                return stringResponse;
            }
        });
        decoratedHeaderResponse.render(JavaScriptHeaderItem.forScript(String.valueOf(combinedScript), null));
        decoratedHeaderResponse.close();
        return stringResponse.getBuffer();
    }

    private void writeComponents(Response response, String encoding) {
        this.componentsFrozen = true;
        List<Component> toBeWritten = new ArrayList(this.markupIdToComponent.size());
        FeedbackDelay delay = new FeedbackDelay(RequestCycle.get());

        try {
            Iterator var5 = this.markupIdToComponent.values().iterator();

            while (true) {
                if (!var5.hasNext()) {
                    delay.beforeRender();
                    break;
                }

                Component component = (Component) var5.next();
                if (!this.containsAncestorFor(component) && this.prepareComponent(component)) {
                    toBeWritten.add(component);
                }
            }
        } catch (Throwable var8) {
            try {
                delay.close();
            } catch (Throwable var7) {
                var8.addSuppressed(var7);
            }

            throw var8;
        }

        delay.close();
        Iterator var9 = toBeWritten.iterator();

        while (var9.hasNext()) {
            Component component = (Component) var9.next();
            this.writeComponent(response, component.getAjaxRegionMarkupId(), component, encoding);
        }

        if (this.header != null) {
            RequestCycle cycle = RequestCycle.get();
            this.headerRendering = true;
            Response oldResponse = cycle.setResponse(this.headerBuffer);
            this.headerBuffer.reset();
            this.header.getHeaderResponse().close();
            cycle.setResponse(oldResponse);
            this.writeHeaderContribution(response, this.headerBuffer.getContents());
            this.headerRendering = false;
        }

    }

    protected boolean prepareComponent(Component component) {
        if (component.getRenderBodyOnly()) {
            throw new IllegalStateException("A partial update is not possible for a component that has renderBodyOnly enabled. Component: " + component);
        } else {
            component.setOutputMarkupId(true);
            Page parentPage = component.findParent(Page.class);
            if (parentPage == null) {
                LOG.warn("Component '{}' not rendered because it was already removed from page", component);
                return false;
            } else {
                try {
                    component.beforeRender();
                    return true;
                } catch (RuntimeException var4) {
                    RuntimeException e = var4;
                    this.bodyBuffer.reset();
                    throw e;
                }
            }
        }
    }

    protected void writeComponent(Response response, String markupId, Component component, String encoding) {
        Response oldResponse = RequestCycle.get().setResponse(this.bodyBuffer);

        try {
            this.writeHeaderContribution(response, component);
            this.bodyBuffer.reset();

            try {
                component.renderPart();
            } catch (RuntimeException var10) {
                RuntimeException e = var10;
                this.bodyBuffer.reset();
                throw e;
            }
        } finally {
            RequestCycle.get().setResponse(oldResponse);
        }

        this.writeComponent(response, markupId, this.bodyBuffer.getContents());
        this.bodyBuffer.reset();
    }

    protected abstract void writeHeader(Response var1, String var2);

    protected abstract void writeComponent(Response var1, String var2, CharSequence var3);

    protected abstract void writePriorityEvaluation(Response var1, CharSequence var2);

    protected abstract void writeHeaderContribution(Response var1, CharSequence var2);

    protected abstract void writeEvaluation(Response var1, CharSequence var2);

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            PartialPageUpdate that = (PartialPageUpdate) o;
            if (!this.appendJavaScripts.equals(that.appendJavaScripts)) {
                return false;
            } else {
                return this.domReadyJavaScripts.equals(that.domReadyJavaScripts) && this.prependJavaScripts.equals(that.prependJavaScripts);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.prependJavaScripts.hashCode();
        result = 31 * result + this.appendJavaScripts.hashCode();
        result = 31 * result + this.domReadyJavaScripts.hashCode();
        return result;
    }

    public final void appendJavaScript(CharSequence javascript) {
        Args.notNull(javascript, "javascript");
        if (this.javascriptsFrozen) {
            throw new IllegalStateException("A partial update of the page is being rendered, JavaScript can no longer be added");
        } else {
            this.appendJavaScripts.add(javascript);
        }
    }

    public final void prependJavaScript(CharSequence javascript) {
        Args.notNull(javascript, "javascript");
        if (this.javascriptsFrozen) {
            throw new IllegalStateException("A partial update of the page is being rendered, JavaScript can no longer be added");
        } else {
            this.prependJavaScripts.add(javascript);
        }
    }

    public final void add(Component component, String markupId) {
        Args.notEmpty(markupId, "markupId");
        Args.notNull(component, "component");
        if (component instanceof Page) {
            if (component != this.page) {
                throw new IllegalArgumentException("Cannot add another page");
            }
        } else {
            Page pageOfComponent = component.findParent(Page.class);
            if (pageOfComponent == null) {
                LOG.warn("Component '{}' not cannot be updated because it was already removed from page", component);
                return;
            }

            if (pageOfComponent != this.page) {
                throw new IllegalArgumentException("Component " + component + " cannot be updated because it is on another page.");
            }

            if (component instanceof AbstractRepeater) {
                throw new IllegalArgumentException("Component " + Classes.name((Class<? extends MarkupContainer>) component.getClass()) + " is a repeater and cannot be added to a partial page update directly. Instead add its parent or another markup container higher in the hierarchy.");
            }
        }

        if (this.componentsFrozen) {
            throw new IllegalStateException("A partial update of the page is being rendered, component " + component + " can no longer be added");
        } else {
            component.setMarkupId(markupId);
            this.markupIdToComponent.put(markupId, component);
        }
    }

    public final Collection<? extends Component> getComponents() {
        return Collections.unmodifiableCollection(this.markupIdToComponent.values());
    }

    public void detach(IRequestCycle requestCycle) {
        Iterator var2 = this.markupIdToComponent.values().iterator();

        while (var2.hasNext()) {
            Component component = (Component) var2.next();
            Page parentPage = component.findParent(Page.class);
            if (parentPage != null) {
                parentPage.detach();
                break;
            }
        }

    }

    protected boolean containsAncestorFor(Component component) {
        for (Component cursor = component.getParent(); cursor != null; cursor = cursor.getParent()) {
            if (this.markupIdToComponent.containsValue(cursor)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsPage() {
        return this.markupIdToComponent.containsValue(this.page);
    }

    public IHeaderResponse getHeaderResponse() {
        if (this.headerResponse == null) {
            this.headerResponse = new PartialHeaderResponse();
        }

        return this.headerResponse;
    }

    protected void writeHeaderContribution(Response response, Component component) {

    }

    public abstract void setContentType(WebResponse var1, String var2);

    public static final class ResponseBuffer extends WebResponse {
        private final AppendingStringBuffer buffer = new AppendingStringBuffer(256);
        private final WebResponse originalResponse;

        private ResponseBuffer(WebResponse originalResponse) {
            this.originalResponse = originalResponse;
        }

        public ResponseBuffer(Response response) {
            super();
        }

        public String encodeURL(CharSequence url) {
            return this.originalResponse.encodeURL(url);
        }

        public CharSequence getContents() {
            return this.buffer;
        }

        public void write(CharSequence cs) {
            this.buffer.append(cs);
        }

        public void reset() {
            this.buffer.clear();
        }

        public void write(byte[] array) {
            throw new UnsupportedOperationException("Cannot write binary data.");
        }

        public void write(byte[] array, int offset, int length) {
            throw new UnsupportedOperationException("Cannot write binary data.");
        }

        public Object getContainerResponse() {
            return this.originalResponse.getContainerResponse();
        }

        public void addCookie(Cookie cookie) {
            this.originalResponse.addCookie(cookie);
        }

        public void clearCookie(Cookie cookie) {
            this.originalResponse.clearCookie(cookie);
        }

        public boolean isHeaderSupported() {
            return this.originalResponse.isHeaderSupported();
        }

        public void setHeader(String name, String value) {
            this.originalResponse.setHeader(name, value);
        }

        public void addHeader(String name, String value) {
            this.originalResponse.addHeader(name, value);
        }

        public void setDateHeader(String name, Instant date) {
            this.originalResponse.setDateHeader(name, date);
        }

        public void setContentLength(long length) {
            this.originalResponse.setContentLength(length);
        }

        public void setContentType(String mimeType) {
            this.originalResponse.setContentType(mimeType);
        }

        public void setStatus(int sc) {
            this.originalResponse.setStatus(sc);
        }

        public void sendError(int sc, String msg) {
            this.originalResponse.sendError(sc, msg);
        }

        public String encodeRedirectURL(CharSequence url) {
            return this.originalResponse.encodeRedirectURL(url);
        }

        public void sendRedirect(String url) {
            this.originalResponse.sendRedirect(url);
        }

        public boolean isRedirect() {
            return false;
        }

        public void flush() {
            this.originalResponse.flush();
        }
    }

    private class PartialHeaderResponse extends HeaderResponse {
        private PartialHeaderResponse() {
        }

        public void render(HeaderItem item) {
            while (item instanceof IWrappedHeaderItem) {
                item = (HeaderItem) ((IWrappedHeaderItem) item).getWrapped();
            }

            if (item instanceof OnLoadHeaderItem) {
                if (!this.wasItemRendered(item)) {
                    PartialPageUpdate.this.appendJavaScript(((OnLoadHeaderItem) item).getJavaScript());
                    this.markItemRendered(item);
                }
            } else if (item instanceof OnEventHeaderItem) {
                if (!this.wasItemRendered(item)) {
                    PartialPageUpdate.this.appendJavaScript(((OnEventHeaderItem) item).getCompleteJavaScript());
                    this.markItemRendered(item);
                }
            } else if (item instanceof OnDomReadyHeaderItem) {
                if (!this.wasItemRendered(item)) {
                    PartialPageUpdate.this.domReadyJavaScripts.add(((OnDomReadyHeaderItem) item).getJavaScript());
                    this.markItemRendered(item);
                }
            } else if (PartialPageUpdate.this.headerRendering) {
                super.render(item);
            } else {
                PartialPageUpdate.LOG.debug("Only methods that can be called on IHeaderResponse outside renderHead() are #render(OnLoadHeaderItem) and #render(OnDomReadyHeaderItem)");
            }

        }

        private void markItemRendered(HeaderItem item) {
        }

        private boolean wasItemRendered(HeaderItem item) {
            return false;
        }

        protected Response getRealResponse() {
            return RequestCycle.get().getResponse();
        }
    }

    private static class PartialHtmlHeaderContainer extends HtmlHeaderContainer {
        private static final long serialVersionUID = 1L;
        private final transient PartialPageUpdate pageUpdate;

        public PartialHtmlHeaderContainer(PartialPageUpdate pageUpdate) {
            super("_header_");
            this.pageUpdate = pageUpdate;
        }

        protected IHeaderResponse newHeaderResponse() {
            if (this.pageUpdate == null) {
                throw new IllegalStateException("disconnected from pageUpdate after serialization");
            } else {
                return this.pageUpdate.getHeaderResponse();
            }
        }
    }
}

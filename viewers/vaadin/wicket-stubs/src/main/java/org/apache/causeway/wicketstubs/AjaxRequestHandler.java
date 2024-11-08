package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.Args;
import org.apache.causeway.wicketstubs.api.Broadcast;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;
import org.apache.causeway.wicketstubs.api.MarkupContainer;
import org.apache.causeway.wicketstubs.api.Page;
import org.apache.causeway.wicketstubs.api.RequestCycle;
import org.apache.causeway.wicketstubs.api.Strings;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AjaxRequestHandler
        extends AbstractPartialPageRequestHandler
        implements AjaxRequestTarget {
    private final PartialPageUpdate update;
    private Set<AjaxRequestTarget.IListener> listeners = null;
    private final Set<AjaxRequestTarget.ITargetRespondListener> respondListeners = new HashSet();
    protected transient boolean respondersFrozen;
    protected transient boolean listenersFrozen;
    private PageLogData logData;

    public AjaxRequestHandler(Page page) {
        super(page);
        this.update = new XmlPartialPageUpdate(page) {
            protected void onBeforeRespond(Response response) {
                AjaxRequestHandler.this.listenersFrozen = true;
                if (AjaxRequestHandler.this.listeners != null) {
                    Iterator var2 = AjaxRequestHandler.this.listeners.iterator();

                    while(var2.hasNext()) {
                        AjaxRequestTarget.IListener listener = (AjaxRequestTarget.IListener)var2.next();
                        listener.onBeforeRespond(this.markupIdToComponent, AjaxRequestHandler.this);
                    }
                }

                AjaxRequestHandler.this.listenersFrozen = false;
            }

            protected void onAfterRespond(Response response) {
                AjaxRequestHandler.this.listenersFrozen = true;
                if (AjaxRequestHandler.this.listeners != null) {
                    Map<String, Component> components = Collections.unmodifiableMap(this.markupIdToComponent);
                    Iterator var3 = AjaxRequestHandler.this.listeners.iterator();

                    while(var3.hasNext()) {
                        AjaxRequestTarget.IListener listener = (AjaxRequestTarget.IListener)var3.next();
                        listener.onAfterRespond(components, AjaxRequestHandler.this);
                    }
                }

            }
        };
    }

    public void addListener(AjaxRequestTarget.IListener listener) throws IllegalStateException {
        Args.notNull(listener, "listener");
        this.assertListenersNotFrozen();
        if (this.listeners == null) {
            this.listeners = new LinkedHashSet();
        }

        if (!this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }

    }

    public PartialPageUpdate getUpdate() {
        return this.update;
    }

    @Override
    public void add(Component var1, String var2) {
        //FIXME
    }

    @Override
    public void add(Component... var1) {

    }

    @Override
    public void addChildren(MarkupContainer var1, Class<?> var2) {

    }

    @Override
    public void appendJavaScript(CharSequence var1) {

    }

    @Override
    public void prependJavaScript(CharSequence var1) {

    }

    @Override
    public void focusComponent(Component var1) {

    }

    public final Collection<? extends Component> getComponents() {
        return this.update.getComponents();
    }

    @Override
    public IHeaderResponse getHeaderResponse() {
        return null;
    }

    public void detach(IRequestCycle requestCycle) {
        if (this.logData == null) {
            this.logData = new PageLogData(this.getPage());
        }

        this.update.detach(requestCycle);
    }

    public boolean equals(Object obj) {
        if (obj instanceof AjaxRequestHandler that) {
            return this.update.equals(that.update);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = "AjaxRequestHandler".hashCode();
        result += this.update.hashCode() * 17;
        return result;
    }

    public void registerRespondListener(AjaxRequestTarget.ITargetRespondListener listener) {
        this.assertRespondersNotFrozen();
        this.respondListeners.add(listener);
    }

    public final void respond(IRequestCycle requestCycle) {
        RequestCycle rc = (RequestCycle)requestCycle;
        WebResponse response = (WebResponse)requestCycle.getResponse();
        Page page = this.getPage();
        String encoding;
        if (this.shouldRedirectToPage(requestCycle)) {
            IRequestHandler handler = new RenderPageRequestHandler(new PageProvider(page));
            encoding = rc.urlFor(handler).toString();
            response.sendRedirect(encoding);
        } else {
            this.respondersFrozen = true;
            Iterator var5 = this.respondListeners.iterator();

            while(var5.hasNext()) {
                AjaxRequestTarget.ITargetRespondListener listener = (AjaxRequestTarget.ITargetRespondListener)var5.next();
                listener.onTargetRespond(this);
            }

            Application app = page.getApplication();
            page.send(app, Broadcast.BREADTH, this);
            encoding = app.getRequestCycleSettings().getResponseRequestEncoding();
            this.update.setContentType(response, encoding);
            response.disableCaching();
            List<IResponseFilter> filters = Application.get().getRequestCycleSettings().getResponseFilters();
            StringResponse bodyResponse = new StringResponse();
            this.update.writeTo(bodyResponse, encoding);
            if (filters != null && !filters.isEmpty()) {
                CharSequence filteredResponse = this.invokeResponseFilters(bodyResponse, filters);
                response.write(filteredResponse);
            } else {
                response.write(bodyResponse.getBuffer());
            }

        }
    }

    private boolean shouldRedirectToPage(IRequestCycle requestCycle) {
        if (this.update.containsPage()) {
            return true;
        } else {
            return !((WebRequest)requestCycle.getRequest()).isAjax();
        }
    }

    private CharSequence invokeResponseFilters(StringResponse contentResponse, List<IResponseFilter> responseFilters) {
        AppendingStringBuffer responseBuffer = new AppendingStringBuffer(contentResponse.getBuffer());

        IResponseFilter filter;
        for(Iterator var4 = responseFilters.iterator(); var4.hasNext(); responseBuffer = filter.filter(responseBuffer)) {
            filter = (IResponseFilter)var4.next();
        }

        return responseBuffer;
    }

    public String toString() {
        int var10000 = this.hashCode();
        return "[AjaxRequestHandler@" + var10000 + " responseObject [" + this.update + "]";
    }

    public String getLastFocusedElementId() {
        WebRequest request = (WebRequest)this.getPage().getRequest();
        String id = request.getHeader("Wicket-FocusedElementId");
        return Strings.isEmpty(id) ? null : UrlDecoder.QUERY_INSTANCE.decode(id, request.getCharset());
    }

    @Override
    public Page getPage() {
        return null;
    }

    public PageLogData getLogData() {
        return this.logData;
    }

    private void assertNotFrozen(boolean frozen, Class<?> clazz) {
        if (frozen) {
            throw new IllegalStateException(Classes.simpleName(clazz) + "s can no longer be added");
        }
    }

    private void assertRespondersNotFrozen() {
        this.assertNotFrozen(this.respondersFrozen, AjaxRequestTarget.ITargetRespondListener.class);
    }

    private void assertListenersNotFrozen() {
        this.assertNotFrozen(this.listenersFrozen, AjaxRequestTarget.IListener.class);
    }
}


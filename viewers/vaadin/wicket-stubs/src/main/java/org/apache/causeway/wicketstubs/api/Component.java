package org.apache.causeway.wicketstubs.api;

import java.io.Serializable;
import java.util.Locale;

import org.apache.causeway.wicketstubs.Application;
import org.apache.causeway.wicketstubs.IConverterLocator;
import org.apache.causeway.wicketstubs.IEventSink;
import org.apache.causeway.wicketstubs.IEventSource;
import org.apache.causeway.wicketstubs.IFeedbackContributor;
import org.apache.causeway.wicketstubs.IHeaderContributor;
import org.apache.causeway.wicketstubs.IHierarchical;
import org.apache.causeway.wicketstubs.IMetadataContext;
import org.apache.causeway.wicketstubs.IRequestableComponent;
import org.apache.causeway.wicketstubs.Request;
import org.apache.causeway.wicketstubs.Response;
import org.apache.causeway.wicketstubs.Session;

public abstract class Component
        implements IClusterable, IConverterLocator, IRequestableComponent, IHeaderContributor, IHierarchical<Component>, IEventSink, IEventSource, IMetadataContext<Serializable, Component>, IFeedbackContributor {
    public static final char PATH_SEPARATOR = ':';
    private final String id = "";

    public Component(String id) {
        this(id, (IModel) null);
    }

    public Component(String id, IModel<?> model) {
    }

    public final boolean isInitialized() {
        return this.getFlag(131072);
    }

    public final void continueToOriginalDestination() {
        RestartResponseAtInterceptPageException.continueToOriginalDestination();
    }

    public final void debug(Serializable message) {
    }

    public final void detach() {
    }

    public final void error(Serializable message) {
    }

    public Application getApplication() {
        return Application.get();
    }

    public String getId() {
        return this.id;
    }

    public Locale getLocale() {
        return Locale.getDefault();
    }

    public final IModel<?> getDefaultModel() {
        return null;
    }

    public final Page getPage() {
        return null;
    }

    public final MarkupContainer getParent() {
        return null;
    }

    public final String getPath() {
        return null;
    }

    public final Request getRequest() {
        return null;
    }

    public final Response getResponse() {
        return null;
    }

    public Session getSession() {
        return Session.get();
    }

    public final String getStyle() {
        return null;
    }

    public String getVariation() {
        return null;
    }

    public final void info(Serializable message) {
    }

    public boolean isEnabled() {
        return this.getFlag(128);
    }

    public boolean isVisible() {
        return this.getFlag(16);
    }

    public final void remove() {
    }

    public final void render() {
    }

    public final Component setEnabled(boolean enabled) {
        return this;
    }

    public Component setMarkupId(String markupId) {
        return null;
    }

    public Component setDefaultModel(IModel<?> model) {
        return null;
    }

    public final Component setVisible(boolean visible) {
        return null;
    }

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean detailed) {
        return null;
    }

    protected final boolean getFlag(int flag) {
        return false;
    }

    protected final <V> IModel<V> wrap(IModel<V> model) {
        return null;
    }

    public Component get(String path) {
        return null;
    }

    public final boolean isAuto() {
        return false;
    }

    public final void setParent(MarkupContainer parent) {
    }

    public final boolean isRendering() {
        return false;
    }

    public final <T> void send(IEventSink sink, Broadcast type, T payload) {
    }

    public Component remove(Behavior... behaviors) {
        return null;
    }

    public Component add(Behavior behaviors) {
        return null;
    }

    public boolean getOutputMarkupId() {
        return false;
    }

    public boolean findParent(Class<Page> pageClass) {
        return false;
    }

    public RequestCycle getRequestCycle() {
        return null;
    }

    public void setEscapeModelStrings(boolean b) {

    }

    public Iterable<Object> getBehaviors(Class<TooltipBehavior> tooltipBehaviorClass) {
        return null;
    }

    public void remove(Object o) {
    }

    public void closePrompt(AjaxRequestTarget target) {

    }
}

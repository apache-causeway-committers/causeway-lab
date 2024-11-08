package org.apache.causeway.wicketstubs.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import lombok.SneakyThrows;

import org.apache.causeway.wicketstubs.Action;
import org.apache.causeway.wicketstubs.Application;
import org.apache.causeway.wicketstubs.Behaviors;
import org.apache.causeway.wicketstubs.BookmarkableListenerRequestHandler;
import org.apache.causeway.wicketstubs.Classes;
import org.apache.causeway.wicketstubs.ComponentEventSender;
import org.apache.causeway.wicketstubs.DebugSettings;
import org.apache.causeway.wicketstubs.DefaultMarkupSourcingStrategy;
import org.apache.causeway.wicketstubs.ExceptionSettings;
import org.apache.causeway.wicketstubs.FeedbackDelay;
import org.apache.causeway.wicketstubs.FeedbackMessages;
import org.apache.causeway.wicketstubs.HtmlHeaderContainer;
import org.apache.causeway.wicketstubs.IAjaxRegionMarkupIdProvider;
import org.apache.causeway.wicketstubs.IAuthorizationStrategy;
import org.apache.causeway.wicketstubs.IComponentAssignedModel;
import org.apache.causeway.wicketstubs.IComponentInheritedModel;
import org.apache.causeway.wicketstubs.IConverter;
import org.apache.causeway.wicketstubs.IConverterLocator;
import org.apache.causeway.wicketstubs.IDetachListener;
import org.apache.causeway.wicketstubs.IEventSink;
import org.apache.causeway.wicketstubs.IEventSource;
import org.apache.causeway.wicketstubs.IFeedback;
import org.apache.causeway.wicketstubs.IFeedbackContributor;
import org.apache.causeway.wicketstubs.IHeaderContributor;
import org.apache.causeway.wicketstubs.IHierarchical;
import org.apache.causeway.wicketstubs.IMarkupFragment;
import org.apache.causeway.wicketstubs.IMarkupIdGenerator;
import org.apache.causeway.wicketstubs.IMarkupSourcingStrategy;
import org.apache.causeway.wicketstubs.IMetadataContext;
import org.apache.causeway.wicketstubs.IModelComparator;
import org.apache.causeway.wicketstubs.IRequestHandler;
import org.apache.causeway.wicketstubs.IRequestableComponent;
import org.apache.causeway.wicketstubs.IRequestablePage;
import org.apache.causeway.wicketstubs.IVisitFilter;
import org.apache.causeway.wicketstubs.IVisitor;
import org.apache.causeway.wicketstubs.IWrapModel;
import org.apache.causeway.wicketstubs.ListenerRequestHandler;
import org.apache.causeway.wicketstubs.Localizer;
import org.apache.causeway.wicketstubs.MarkupElement;
import org.apache.causeway.wicketstubs.MarkupException;
import org.apache.causeway.wicketstubs.MarkupNotFoundException;
import org.apache.causeway.wicketstubs.MarkupStream;
import org.apache.causeway.wicketstubs.MetaDataEntry;
import org.apache.causeway.wicketstubs.MetaDataKey;
import org.apache.causeway.wicketstubs.PageAndComponentProvider;
import org.apache.causeway.wicketstubs.Request;
import org.apache.causeway.wicketstubs.Response;
import org.apache.causeway.wicketstubs.Session;
import org.apache.causeway.wicketstubs.StringHeaderItem;
import org.apache.causeway.wicketstubs.StringResponse;
import org.apache.causeway.wicketstubs.UnauthorizedActionException;
import org.apache.causeway.wicketstubs.ValueMap;
import org.apache.causeway.wicketstubs.Visit;
import org.apache.causeway.wicketstubs.WicketObjects;
import org.apache.causeway.wicketstubs.WicketTag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.causeway.applib.value.Markup;

public abstract class Component
        implements IClusterable, IConverterLocator, IRequestableComponent, IHeaderContributor, IHierarchical<Component>, IEventSink, IEventSource, IMetadataContext<Serializable, Component>, IFeedbackContributor {
    private static final Logger log = LoggerFactory.getLogger(Component.class);
    private static final long serialVersionUID = 1L;
    public static final Action ENABLE = new Action("ENABLE");
    public static final char PATH_SEPARATOR = ':';
    public static final String PARENT_PATH = "..";
    public static final Action RENDER = new Action("RENDER");
    private static final MetaDataKey<FeedbackMessages> MARKUP_ID_KEY = new MetaDataKey<FeedbackMessages>() {
        private static final long serialVersionUID = 1L;
    };
    private static final MetaDataKey<FeedbackMessages> FEEDBACK_KEY = new MetaDataKey<FeedbackMessages>() {
        private static final long serialVersionUID = 1L;
    };
    private static final IModelComparator defaultModelComparator = new IModelComparator() {
        private static final long serialVersionUID = 1L;

        public boolean compare(Component component, Object b) {
            Object a = component.getDefaultModelObject();
            if (a == null && b == null) {
                return true;
            } else {
                return a != null && b != null ? a.equals(b) : false;
            }
        }
    };
    private static final int FLAG_AUTO = 1;
    private static final int FLAG_ESCAPE_MODEL_STRINGS = 2;
    static final int FLAG_INHERITABLE_MODEL = 4;
    private static final int FLAG_VERSIONED = 8;
    private static final int FLAG_VISIBLE = 16;
    private static final int FLAG_RENDER_BODY_ONLY = 32;
    private static final int FLAG_IGNORE_ATTRIBUTE_MODIFIER = 64;
    private static final int FLAG_ENABLED = 128;
    protected static final int FLAG_RESERVED1 = 256;
    protected static final int FLAG_RESERVED2 = 512;
    protected static final int FLAG_RESERVED3 = 1024;
    protected static final int FLAG_RESERVED4 = 2048;
    private static final int FLAG_HAS_BEEN_RENDERED = 4096;
    private static final int FLAG_IS_RENDER_ALLOWED = 8192;
    private static final int FLAG_OUTPUT_MARKUP_ID = 16384;
    private static final int FLAG_PLACEHOLDER = 32768;
    protected static final int FLAG_RESERVED5 = 65536;
    protected static final int FLAG_INITIALIZED = 131072;
    private static final int FLAG_REMOVED = 262144;
    protected static final int FLAG_RESERVED8 = 524288;
    private static final int FLAG_MODEL_SET = 1048576;
    private static final int FLAG_VISIBILITY_ALLOWED = 1073741824;
    private static final String MARKUP_ID_ATTR_NAME = "id";
    static final MetaDataKey<String> ADDED_AT_KEY = new MetaDataKey<String>() {
        private static final long serialVersionUID = 1L;
    };
    static final MetaDataKey<String> CONSTRUCTED_AT_KEY = new MetaDataKey<String>() {
        private static final long serialVersionUID = 1L;
    };
    private int flags;
    private static final short RFLAG_ENABLED_IN_HIERARCHY_VALUE = 1;
    private static final short RFLAG_ENABLED_IN_HIERARCHY_SET = 2;
    private static final short RFLAG_VISIBLE_IN_HIERARCHY_VALUE = 4;
    private static final short RFLAG_VISIBLE_IN_HIERARCHY_SET = 8;
    private static final short RFLAG_CONFIGURED = 16;
    private static final short RFLAG_BEFORE_RENDER_SUPER_CALL_VERIFIED = 32;
    private static final short RFLAG_INITIALIZE_SUPER_CALL_VERIFIED = 64;
    protected static final short RFLAG_CONTAINER_DEQUEING = 128;
    private static final short RFLAG_ON_RE_ADD_SUPER_CALL_VERIFIED = 256;
    private static final short RFLAG_RENDERING = 512;
    private static final short RFLAG_PREPARED_FOR_RENDER = 1024;
    private static final short RFLAG_AFTER_RENDER_SUPER_CALL_VERIFIED = 2048;
    private static final short RFLAG_DETACHING = 4096;
    private static final short RFLAG_REMOVING_FROM_HIERARCHY = 8192;
    protected static final short RFLAG_CONTAINER_HAS_REMOVALS = 16384;
    private static final short RFLAG_ON_CONFIGURE_SUPER_CALL_VERIFIED = Short.MIN_VALUE;
    private transient short requestFlags;
    private final String id;
    private MarkupContainer parent;
    int generatedMarkupId;
    private transient IMarkupFragment markup;
    private transient IMarkupSourcingStrategy markupSourcingStrategy;
    Object data;
    private String defaultValue;

    final int data_start() {
        return this.getFlag(1048576) ? 1 : 0;
    }

    final int data_length() {
        if (this.data == null) {
            return 0;
        } else {
            return this.data instanceof Object[] && !(this.data instanceof MetaDataEntry[]) ? ((Object[]) this.data).length : 1;
        }
    }

    final Object data_get(int index) {
        if (this.data == null) {
            return null;
        } else if (this.data instanceof Object[] && !(this.data instanceof MetaDataEntry[])) {
            Object[] array = (Object[]) this.data;
            return index < array.length ? array[index] : null;
        } else {
            return index == 0 ? this.data : null;
        }
    }

    final void data_set(int index, Object object) {
        if (index > this.data_length() - 1) {
            throw new IndexOutOfBoundsException("can not set data at " + index + " when data_length() is " + this.data_length());
        } else {
            if (index != 0 || this.data instanceof Object[] && !(this.data instanceof MetaDataEntry[])) {
                Object[] array = (Object[]) this.data;
                array[index] = object;
            } else {
                this.data = object;
            }

        }
    }

    final void data_add(Object object) {
        this.data_insert(-1, object);
    }

    final void data_insert(int position, Object object) {
        int currentLength = this.data_length();
        if (position == -1) {
            position = currentLength;
        }

        if (position > currentLength) {
            throw new IndexOutOfBoundsException("can not insert data at " + position + " when data_length() is " + currentLength);
        } else {
            if (currentLength == 0) {
                this.data = object;
            } else {
                Object[] array;
                if (currentLength == 1) {
                    array = new Object[2];
                    if (position == 0) {
                        array[0] = object;
                        array[1] = this.data;
                    } else {
                        array[0] = this.data;
                        array[1] = object;
                    }

                    this.data = array;
                } else {
                    array = new Object[currentLength + 1];
                    Object[] current = (Object[]) this.data;
                    int after = currentLength - position;
                    if (position > 0) {
                        System.arraycopy(current, 0, array, 0, position);
                    }

                    array[position] = object;
                    if (after > 0) {
                        System.arraycopy(current, position, array, position + 1, after);
                    }

                    this.data = array;
                }
            }

        }
    }

    final void data_remove(int position) {
        int currentLength = this.data_length();
        if (position > currentLength - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            if (currentLength == 1) {
                this.data = null;
            } else {
                Object[] current;
                if (currentLength == 2) {
                    current = (Object[]) this.data;
                    if (position == 0) {
                        this.data = current[1];
                    } else {
                        this.data = current[0];
                    }
                } else {
                    current = (Object[]) this.data;
                    this.data = new Object[currentLength - 1];
                    if (position > 0) {
                        System.arraycopy(current, 0, this.data, 0, position);
                    }

                    if (position != currentLength - 1) {
                        int left = currentLength - position - 1;
                        System.arraycopy(current, position + 1, this.data, position, left);
                    }
                }
            }

        }
    }

    public Component(String id) {
        this(id, (IModel) null);
    }

    public Component(String id, IModel<?> model) {
        this.flags = 1073815706;
        this.requestFlags = 0;
        this.generatedMarkupId = -1;
        this.data = null;
        this.checkId(id);
        this.id = id;
        this.init();
        Application application = this.getApplication();
        application.getComponentInstantiationListeners().onInstantiation(this);
        DebugSettings debugSettings = application.getDebugSettings();
        if (debugSettings.isLinePreciseReportingOnNewComponentEnabled() && debugSettings.getComponentUseCheck()) {
     //FIXME       this.setMetaData(CONSTRUCTED_AT_KEY, (Serializable) ComponentStrings.toString(this, new MarkupException("constructed")));
        }

        if (model != null) {
            this.setModelImpl(this.wrap(model));
        }

    }

    void init() {
    }

    @SneakyThrows
    public IMarkupFragment getMarkup() {
        if (this.markup != null) {
            return this.markup;
        } else if (this.parent == null) {
            if (this instanceof MarkupContainer) {
                MarkupContainer container = (MarkupContainer) this;
                Markup associatedMarkup = container.getAssociatedMarkup();
                if (associatedMarkup != null) {
//FIXME                    this.markup = associatedMarkup;
                    return this.markup;
                }
            }

            throw new MarkupNotFoundException("Can not determine Markup. Component is not yet connected to a parent. " + this.toString());
        } else {
            this.markup = this.parent.getMarkup(this);
            return this.markup;
        }
    }

    public final String getMarkupIdFromMarkup() {
        ComponentTag tag = this.getMarkupTag();
        if (tag != null) {
            String id = tag.getAttribute("id");
            if (!Strings.isEmpty(id)) {
                return id.trim();
            }
        }

        return null;
    }

    public final Component setMarkup(IMarkupFragment markup) {
        this.markup = markup;
        return this;
    }

    protected void onConfigure() {
        this.setRequestFlag((short) Short.MIN_VALUE, true);
    }

    protected void onInitialize() {
        this.setRequestFlag((short) 64, true);
    }

    public final boolean isInitialized() {
        return this.getFlag(131072);
    }

    public void internalInitialize() {
        this.fireInitialize();
    }

    final void fireInitialize() {
        if (!this.getFlag(131072)) {
            this.setFlag(131072, true);
            this.setRequestFlag((short) 64, false);
            this.onInitialize();
            this.verifySuperCall("onInitialize", (short) 64);
            this.getApplication().getComponentInitializationListeners().onInitialize(this);
        } else if (this.getFlag(262144)) {
            this.setFlag(262144, false);
            this.setRequestFlag((short) 256, false);
            this.onReAdd();
            this.verifySuperCall("onReAdd", (short) 256);
        }

    }

    final void afterRender() {
        this.setRequestFlag((short) 1024, false);

        try {
            this.setRequestFlag((short) 2048, false);
            this.onAfterRender();
            this.getApplication().getComponentOnAfterRenderListeners().onAfterRender(this);
            this.verifySuperCall("onAfterRender", (short) 2048);
        } finally {
            this.setRequestFlag((short) 512, false);
        }

    }

    public final void beforeRender() {
        if (this instanceof IFeedback) {
            Optional<FeedbackDelay> delay = FeedbackDelay.get(this.getRequestCycle());
            if (delay.isPresent()) {
                ((FeedbackDelay) delay.get()).postpone((IFeedback) this);
                return;
            }
        }

        this.configure();
        if (this.determineVisibility() && !this.getRequestFlag((short) 512) && !this.getRequestFlag((short) 1024)) {
            try {
                this.setRequestFlag((short) 32, false);
                Application application = this.getApplication();
                application.getComponentPreOnBeforeRenderListeners().onBeforeRender(this);
                this.onBeforeRender();
                application.getComponentPostOnBeforeRenderListeners().onBeforeRender(this);
                this.verifySuperCall("onBeforeRender", (short) 32);
            } catch (RuntimeException var2) {
                RuntimeException ex = var2;
                this.setRequestFlag((short) 1024, false);
                throw ex;
            }
        }

    }

    public final void configure() {
        if (!this.getRequestFlag((short) 16)) {
            this.clearEnabledInHierarchyCache();
            this.clearVisibleInHierarchyCache();
            this.setRequestFlag((short) Short.MIN_VALUE, false);
            this.onConfigure();
            this.verifySuperCall("onConfigure", (short) Short.MIN_VALUE);
            Iterator var1 = this.getBehaviors().iterator();

            while (var1.hasNext()) {
                Behavior behavior = (Behavior) var1.next();
                if (this.isBehaviorAccepted(behavior)) {
                    behavior.onConfigure(this);
                }
            }

            this.setRenderAllowed();
            this.internalOnAfterConfigure();
            this.getApplication().getComponentOnConfigureListeners().onConfigure(this);
            this.setRequestFlag((short) 16, true);
        }

    }

    private final void verifySuperCall(String method, short flag) {
        if (!this.getRequestFlag(flag)) {
            throw new IllegalStateException(String.format("%s() in the hierarchy of %s has not called super.%s()", method, this.getClass().getName(), method));
        } else {
            this.setRequestFlag(flag, false);
        }
    }

    void internalOnAfterConfigure() {
    }

    public final void continueToOriginalDestination() {
        RestartResponseAtInterceptPageException.continueToOriginalDestination();
    }

    public final void clearOriginalDestination() {
        RestartResponseAtInterceptPageException.clearOriginalDestination();
    }

    public final void debug(Serializable message) {
        this.getFeedbackMessages().debug(this, message);
        this.addStateChange();
    }

    final void internalOnRemove() {
        this.setRequestFlag((short) 8192, true);
        this.onRemove();
        this.setFlag(262144, true);
        if (this.getRequestFlag((short) 8192)) {
            String var10002 = Component.class.getName();
            throw new IllegalStateException(var10002 + " has not been properly removed from hierarchy. Something in the hierarchy of " + this.getClass().getName() + " has not called super.onRemove() in the override of onRemove() method");
        } else {
            Behaviors.onRemove(this);
            this.removeChildren();
        }
    }

    @SneakyThrows
    public final void detach() {
        try {
            this.setRequestFlag((short) 4096, true);
            this.onDetach();
            if (this.getRequestFlag((short) 4096)) {
                String var10002 = Component.class.getName();
                throw new IllegalStateException(var10002 + " has not been properly detached. Something in the hierarchy of " + this.getClass().getName() + " has not called super.onDetach() in the override of onDetach() method");
            }

            this.detachModels();
            Behaviors.detach(this);
        } catch (Exception var2) {
            Exception x = var2;
            throw new WicketRuntimeException("An error occurred while detaching component: " + this.toString(true), x);
        }

        this.detachChildren();
        if (this.getFlag(4)) {
            this.setModelImpl((IModel) null);
            this.setFlag(4, false);
        }

        this.clearEnabledInHierarchyCache();
        this.clearVisibleInHierarchyCache();
        this.requestFlags &= -32672;
        this.detachFeedback();
        this.internalDetach();
        IDetachListener detachListener = this.getApplication().getFrameworkSettings().getDetachListener();
        if (detachListener != null) {
            detachListener.onDetach(this);
        }

    }

    private void detachFeedback() {
        FeedbackMessages feedback = (FeedbackMessages) this.getMetaData(FEEDBACK_KEY);
        if (feedback != null) {
            feedback.clear(this.getApplication().getApplicationSettings().getFeedbackMessageCleanupFilter());
            if (feedback.isEmpty()) {
                this.setMetaData(FEEDBACK_KEY, (Serializable) null);
            } else {
                feedback.detach();
            }
        }

    }

    private void internalDetach() {
        this.markup = null;
    }

    public void detachModels() {
        this.detachModel();
    }

    public final void error(Serializable message) {
        this.getFeedbackMessages().error(this, message);
        this.addStateChange();
    }

    public final void fatal(Serializable message) {
        this.getFeedbackMessages().fatal(this, message);
        this.addStateChange();
    }

    public final <Z> Z findParent(Class<Z> c) {
        for (MarkupContainer current = this.parent; current != null; current = current.getParent()) {
            if (c.isInstance(current)) {
                return c.cast(current);
            }
        }

        return null;
    }

    @SneakyThrows
    public final MarkupContainer findParentWithAssociatedMarkup() {
        for (MarkupContainer container = this.parent; container != null; container = container.getParent()) {
            if (container.getAssociatedMarkup() != null) {
                return container;
            }
        }

        throw new WicketRuntimeException("Unable to find parent with associated markup");
    }

    public final Application getApplication() {
        return Application.get();
    }

    public final String getClassRelativePath() {
        String var10000 = this.getClass().getName();
        return var10000 + ":" + this.getPageRelativePath();
    }

    public <C> IConverter<C> getConverter(Class<C> type) {
        IConverter<?> converter = this.createConverter(type);
        return converter != null ? (IConverter<C>) converter : this.getApplication().getConverterLocator().getConverter(type);
    }

    protected IConverter<?> createConverter(Class<?> type) {
        return null;
    }

    public final boolean getEscapeModelStrings() {
        return this.getFlag(2);
    }

    public String getId() {
        return this.id;
    }

    public final IModel<?> getInnermostModel() {
        return this.getInnermostModel(this.getDefaultModel());
    }

    public Locale getLocale() {
        return this.parent != null ? this.parent.getLocale() : this.getSession().getLocale();
    }

    public final Localizer getLocalizer() {
        return this.getApplication().getResourceSettings().getLocalizer();
    }

    protected final ComponentTag getMarkupTag() {
        IMarkupFragment markup = this.getMarkup();
        if (markup != null) {
            for (int i = 0; i < markup.size(); ++i) {
                MarkupElement elem = markup.get(i);
                if (elem instanceof ComponentTag) {
                    return (ComponentTag) elem;
                }
            }
        }

        return null;
    }

    public final ValueMap getMarkupAttributes() {
        ComponentTag tag = this.getMarkupTag();
        if (tag != null) {
            ValueMap attrs = new ValueMap(tag.getAttributes());
            attrs.makeImmutable();
            return attrs;
        } else {
            return ValueMap.EMPTY_MAP;
        }
    }

    public final Object getMarkupIdImpl() {
        if (this.generatedMarkupId != -1) {
            return this.generatedMarkupId;
        } else {
            String id = (String) this.getMetaData(MARKUP_ID_KEY);
            if (id == null && this.findPage() != null) {
                id = this.getMarkupIdFromMarkup();
            }

            return id;
        }
    }

    public String getMarkupId(boolean createIfDoesNotExist) {
        IMarkupIdGenerator markupIdGenerator = this.getApplication().getMarkupSettings().getMarkupIdGenerator();
        String markupId = markupIdGenerator.generateMarkupId(this, createIfDoesNotExist);
        return markupId;
    }

    public String getMarkupId() {
        return this.getMarkupId(true);
    }

    public final <M extends Serializable> M getMetaData(MetaDataKey<FeedbackMessages> key) {
        return (M) key.get(this.getMetaData());
    }

    private MetaDataEntry<?>[] getMetaData() {
        MetaDataEntry<?>[] metaData = null;
        int index = this.getFlag(1048576) ? 1 : 0;
        int length = this.data_length();
        if (index < length) {
            Object object = this.data_get(index);
            if (object instanceof MetaDataEntry[]) {
                metaData = (MetaDataEntry[]) object;
            } else if (object instanceof MetaDataEntry) {
                metaData = new MetaDataEntry[]{(MetaDataEntry) object};
            }
        }

        return metaData;
    }

    public final IModel<?> getDefaultModel() {
        IModel<?> model = this.getModelImpl();
        if (model == null) {
            model = this.initModel();
            this.setModelImpl(model);
        }

        return model;
    }

    @SneakyThrows
    public final Object getDefaultModelObject() {
        IModel<?> model = this.getDefaultModel();
        if (model != null) {
            try {
                return model.getObject();
            } catch (Exception var4) {
                Exception ex = var4;
                WicketRuntimeException rex = new WicketRuntimeException("An error occurred while getting the model object for Component: " + this.toString(true));
                throw rex;
            }
        } else {
            return null;
        }
    }

    public final String getDefaultModelObjectAsString() {
        return this.getDefaultModelObjectAsString(this.getDefaultModelObject());
    }

    public final String getDefaultModelObjectAsString(Object modelObject) {
        if (modelObject != null) {
            Class<?> objectClass = modelObject.getClass();
            IConverter converter = this.getConverter(objectClass);
            String modelString = converter.convertToString(modelObject, this.getLocale());
            if (modelString != null) {
                if (this.getFlag(2)) {
                    return Strings.escapeMarkup(modelString, false, false).toString();
                }

                return modelString;
            }
        }

        return "";
    }

    public final boolean getOutputMarkupId() {
        return this.getFlag(16384);
    }

    public final boolean getOutputMarkupPlaceholderTag() {
        return this.getFlag(32768);
    }

    @SneakyThrows
    public final Page getPage() {
        Page page = this.findPage();
        if (page == null) {
            throw new WicketRuntimeException("No Page found for component: " + this.toString(true) + ". You probably forgot to add it to its parent component.");
        } else {
            return page;
        }
    }

    public final String getPageRelativePath() {
        return Strings.afterFirstPathComponent(this.getPath(), ':');
    }

    public final MarkupContainer getParent() {
        return this.parent;
    }

    public final String getPath() {
        PrependingStringBuffer buffer = new PrependingStringBuffer(32);

        for (Component c = this; c != null; c = ((Component) c).getParent()) {
            if (buffer.length() > 0) {
                buffer.prepend(':');
            }

            buffer.prepend(((Component) c).getId());
        }

        return buffer.toString();
    }

    public final boolean getRenderBodyOnly() {
        return this.getFlag(32);
    }

    @SneakyThrows
    public final Request getRequest() {
        RequestCycle requestCycle = this.getRequestCycle();
        if (requestCycle == null) {
            throw new WicketRuntimeException("No RequestCycle is currently set!");
        } else {
            return requestCycle.getRequest();
        }
    }

    public final RequestCycle getRequestCycle() {
        return RequestCycle.get();
    }

    public final Response getResponse() {
        return this.getRequestCycle().getResponse();
    }

    public Session getSession() {
        return Session.get();
    }

    public long getSizeInBytes() {
        MarkupContainer originalParent = this.parent;
        this.parent = null;
        long size = 0L;

        try {
            size = WicketObjects.sizeof(this);
        } catch (Exception var5) {
            Exception e = var5;
            log.error("Exception getting size for component " + this, e);
        }

        this.parent = originalParent;
        return size;
    }

    public final String getString(String key) {
        return this.getString(key, (IModel) null);
    }

    public final String getString(String key, IModel<?> model) {
        return this.getLocalizer().getString(key, this, model, defaultValue);
    }

    public final String getString(String key, IModel<?> model, String defaultValue) {
        return this.getLocalizer().getString(key, this, model, defaultValue);
    }

    @SneakyThrows
    public final String getStyle() {
        Session session = this.getSession();
        if (session == null) {
            throw new WicketRuntimeException("Wicket Session object not available");
        } else {
            return session.getStyle();
        }
    }

    public String getVariation() {
        return this.parent != null ? this.parent.getVariation() : null;
    }

    public final boolean hasBeenRendered() {
        return this.getFlag(4096);
    }

    public FeedbackMessages getFeedbackMessages() {
        FeedbackMessages messages = (FeedbackMessages) this.getMetaData(FEEDBACK_KEY);
        if (messages == null) {
            messages = new FeedbackMessages();
            this.setMetaData(FEEDBACK_KEY, (Serializable) messages);
        }

        return messages;
    }

    public final boolean hasErrorMessage() {
        FeedbackMessages messages = (FeedbackMessages) this.getMetaData(FEEDBACK_KEY);
        return messages == null ? false : messages.hasMessage(400);
    }

    public final boolean hasFeedbackMessage() {
        FeedbackMessages messages = (FeedbackMessages) this.getMetaData(FEEDBACK_KEY);
        if (messages == null) {
            return false;
        } else {
            return messages.size() > 0;
        }
    }

    public final void info(Serializable message) {
        this.getFeedbackMessages().info(this, message);
        this.addStateChange();
    }

    public final void success(Serializable message) {
        this.getFeedbackMessages().success(this, message);
        this.addStateChange();
    }

    public final boolean isActionAuthorized(Action action) {
        IAuthorizationStrategy authorizationStrategy = this.getSession().getAuthorizationStrategy();
        return authorizationStrategy != null ? authorizationStrategy.isActionAuthorized(this, action) : true;
    }

    public final boolean isEnableAllowed() {
        return this.isActionAuthorized(ENABLE);
    }

    public boolean isEnabled() {
        return this.getFlag(128);
    }

    public final boolean isRenderAllowed() {
        return this.getFlag(8192);
    }

    public final boolean isStateless() {
        if ((!this.isVisibleInHierarchy() || !this.isEnabledInHierarchy()) && !this.canCallListener()) {
            return true;
        } else if (!this.getStatelessHint()) {
            return false;
        } else {
            Iterator var1 = this.getBehaviors().iterator();

            Behavior behavior;
            do {
                if (!var1.hasNext()) {
                    return true;
                }

                behavior = (Behavior) var1.next();
            } while (behavior.getStatelessHint(this));

            return false;
        }
    }

    public boolean isVersioned() {
        if (!this.getFlag(8)) {
            return false;
        } else {
            return this.parent == null || this.parent.isVersioned();
        }
    }

    public boolean isVisible() {
        return this.getFlag(16);
    }

    public final boolean isVisibleInHierarchy() {
        if (this.getRequestFlag((short) 8)) {
            return this.getRequestFlag((short) 4);
        } else {
            Component parent = this.getParent();
            boolean state;
            if (parent != null && !((Component) parent).isVisibleInHierarchy()) {
                state = false;
            } else {
                state = this.determineVisibility();
            }

            this.setRequestFlag((short) 8, true);
            this.setRequestFlag((short) 4, state);
            return state;
        }
    }

    public final void markRendering(boolean setRenderingFlag) {
        this.internalMarkRendering(setRenderingFlag);
    }

    public final void modelChanged() {
        this.internalOnModelChanged();
        this.onModelChanged();
    }

    public final void modelChanging() {
        this.checkHierarchyChange(this);
        this.onModelChanging();
        Page page = this.findPage();
        if (page != null) {
            page.componentModelChanging(this);
        }

    }

    @SneakyThrows
    public final void redirectToInterceptPage(Page page) {
        throw new RestartResponseAtInterceptPageException(page);
    }

    public final void remove() {
        if (this.parent == null) {
            throw new IllegalStateException("Cannot remove " + this + " from null parent!");
        } else {
            this.parent.remove(this);
        }
    }

    public final void renderPart() {
        Page page = this.getPage();
        page.startComponentRender(this);
        this.markRendering(true);
        this.render();
        page.endComponentRender(this);
    }

    public final void render() {
        if (this.isAuto()) {
            this.beforeRender();
        }

        RuntimeException exception = null;

        try {
            this.setRequestFlag((short) 512, true);
            this.internalRender();
        } catch (RuntimeException var10) {
            RuntimeException ex = var10;
            exception = ex;
        } finally {
            try {
                this.afterRender();
            } catch (RuntimeException var11) {
                RuntimeException ex2 = var11;
                if (exception == null) {
                    exception = ex2;
                }
            }

        }

        if (exception != null) {
            throw exception;
        }
    }

    @SneakyThrows
    private void internalRender() {
        IMarkupFragment markup = this.getMarkup();
        if (markup == null) {
            throw new MarkupNotFoundException("Markup not found for Component: " + this.toString());
        } else {
            MarkupStream markupStream = new MarkupStream(markup);
            MarkupElement elem = markup.get(0);
            if (elem instanceof ComponentTag) {
                ((ComponentTag) elem).onBeforeRender(this, markupStream);
            }

            if (this.determineVisibility()) {
                this.setFlag(4096, true);
                if (log.isDebugEnabled()) {
                    log.debug("Begin render {}", this);
                }

                try {
                    this.notifyBehaviorsComponentBeforeRender();
                    this.onRender();
                    this.notifyBehaviorsComponentRendered();
                    this.rendered();
                } catch (RuntimeException var5) {
                    RuntimeException ex = var5;
                    this.onException(ex);
                }

                if (log.isDebugEnabled()) {
                    log.debug("End render {}", this);
                }
            } else if (elem != null && elem instanceof ComponentTag && this.getFlag(32768)) {
                this.renderPlaceholderTag(((ComponentTag) elem).mutable(), this.getResponse());
            }

        }
    }

    private void onException(RuntimeException ex) {
        Iterator var2 = this.getBehaviors().iterator();

        while (var2.hasNext()) {
            Behavior behavior = (Behavior) var2.next();
            if (this.isBehaviorAccepted(behavior)) {
                try {
                    behavior.onException(this, ex);
                } catch (Exception var5) {
                    Exception ex2 = var5;
                    log.error("Error while cleaning up after exception", ex2);
                }
            }
        }

        throw ex;
    }

    protected void renderPlaceholderTag(ComponentTag tag, Response response) {
        String name = Strings.isEmpty(tag.getNamespace()) ? tag.getName() : tag.getNamespace() + ":" + tag.getName();
        response.write("<" + name + " id=\"" + this.getAjaxRegionMarkupId() + "\" hidden=\"\" data-wicket-placeholder=\"\"></" + name + ">");
    }

    public final String getAjaxRegionMarkupId() {
        String markupId = null;
        Iterator var2 = this.getBehaviors().iterator();

        while (var2.hasNext()) {
            Behavior behavior = (Behavior) var2.next();
            if (behavior instanceof IAjaxRegionMarkupIdProvider && behavior.isEnabled(this)) {
                markupId = ((IAjaxRegionMarkupIdProvider) behavior).getAjaxRegionMarkupId(this);
                break;
            }
        }

        if (markupId == null && this instanceof IAjaxRegionMarkupIdProvider) {
            markupId = ((IAjaxRegionMarkupIdProvider) this).getAjaxRegionMarkupId(this);
        }

        if (markupId == null) {
            markupId = this.getMarkupId();
        }

        return markupId;
    }

    @SneakyThrows
    protected final void internalRenderComponent() {
        IMarkupFragment markup = this.getMarkup();
        if (markup == null) {
            throw new MarkupException("Markup not found. Component: " + this.toString());
        } else {
            MarkupStream markupStream = new MarkupStream(markup);
            ComponentTag openTag = markupStream.getTag();
            ComponentTag tag = openTag.mutable();
            this.getApplication().getOnComponentTagListeners().onComponentTag(this, tag);
            this.onComponentTag(tag);
            if (!tag.isOpenClose() && !tag.isOpen()) {
                markupStream.throwMarkupException("Method renderComponent called on bad markup element: " + tag);
            }

            if (tag.isOpenClose() && openTag.isOpen()) {
                markupStream.throwMarkupException("You can not modify a open tag to open-close: " + tag);
            }

            try {
                boolean renderBodyOnly = this.getRenderBodyOnly();
                if (renderBodyOnly) {
                    ExceptionSettings.NotRenderableErrorStrategy notRenderableErrorStrategy = ExceptionSettings.NotRenderableErrorStrategy.LOG_WARNING;
                    if (Application.exists()) {
                        notRenderableErrorStrategy = this.getApplication().getExceptionSettings().getNotRenderableErrorStrategy();
                    }

                    String message;
                    if (this.getFlag(16384)) {
                        message = String.format("Markup id set on a component that renders its body only. Markup id: %s, component id: %s.", this.getMarkupId(), this.getId());
                        if (notRenderableErrorStrategy == ExceptionSettings.NotRenderableErrorStrategy.THROW_EXCEPTION) {
                            throw new IllegalStateException(message);
                        }

                        log.warn(message);
                    }

                    if (this.getFlag(32768)) {
                        message = String.format("Placeholder tag set on a component that renders its body only. Component id: %s.", this.getId());
                        if (notRenderableErrorStrategy == ExceptionSettings.NotRenderableErrorStrategy.THROW_EXCEPTION) {
                            throw new IllegalStateException(message);
                        }

                        log.warn(message);
                    }
                } else {
                    this.renderComponentTag(tag);
                }

                markupStream.next();
                if (tag.isOpen()) {
                    this.getMarkupSourcingStrategy().onComponentTagBody(this, markupStream, tag);
                    if (openTag.isOpen()) {
                        this.renderClosingComponentTag(markupStream, tag, renderBodyOnly);
                    } else if (!renderBodyOnly && this.needToRenderTag(openTag)) {
                        tag.writeSyntheticCloseTag(this.getResponse());
                    }
                }

 /*           } catch (WicketRuntimeException var8) {
                WicketRuntimeException wre = var8;
                throw wre;*/
            } catch (RuntimeException var9) {
                RuntimeException re = var9;
                throw new WicketRuntimeException("Exception in rendering component: " + this, re);
            }
        }
    }

    private boolean needToRenderTag(ComponentTag openTag) {
        boolean renderTag = openTag != null && !(openTag instanceof WicketTag);
        if (!renderTag) {
            renderTag = !this.getApplication().getMarkupSettings().getStripWicketTags();
        }

        return renderTag;
    }

    public final void rendered() {
        Page page = this.findPage();
        if (page != null) {
            page.componentRendered(this);
        } else {
            log.error("Component is not connected to a Page. Cannot register the component as being rendered. Component: " + this.toString());
        }

    }

    protected final IMarkupSourcingStrategy getMarkupSourcingStrategy() {
        if (this.markupSourcingStrategy == null) {
            this.markupSourcingStrategy = this.newMarkupSourcingStrategy();
            if (this.markupSourcingStrategy == null) {
                this.markupSourcingStrategy = DefaultMarkupSourcingStrategy.get();
            }
        }

        return this.markupSourcingStrategy;
    }

    protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
        return null;
    }

    public void internalRenderHead(HtmlHeaderContainer container) {
        if (this.isVisibleInHierarchy() && this.isRenderAllowed()) {
            if (log.isDebugEnabled()) {
                log.debug("internalRenderHead: {}", this.toString(false));
            }

            IHeaderResponse response = container.getHeaderResponse();
            boolean wasRendered = response.wasRendered(this);
            if (!wasRendered) {
                StringResponse markupHeaderResponse = new StringResponse();
                Response oldResponse = this.getResponse();
                RequestCycle.get().setResponse(markupHeaderResponse);

                try {
                    this.getMarkupSourcingStrategy().renderHead(this, container);
                    CharSequence headerContribution = markupHeaderResponse.getBuffer();
                    if (!Strings.isEmpty(headerContribution)) {
                        response.render(StringHeaderItem.forString(headerContribution));
                    }
                } finally {
                    RequestCycle.get().setResponse(oldResponse);
                }

                this.renderHead(response);
            }

            Iterator var10 = this.getBehaviors().iterator();

            while (var10.hasNext()) {
                Behavior behavior = (Behavior) var10.next();
                if (this.isBehaviorAccepted(behavior)) {
                    List<IClusterable> pair = List.of(this, (IClusterable) behavior);
                    if (!response.wasRendered(pair)) {
                        behavior.renderHead(this, response);
                        response.markRendered(pair);
                    }
                }
            }

            if (!wasRendered) {
                response.markRendered(this);
            }
        }

    }

    public Component replaceWith(Component replacement) {
        Args.notNull(replacement, "replacement");
        if (!this.getId().equals(replacement.getId())) {
            String var10002 = replacement.getId();
            throw new IllegalArgumentException("Replacement component must have the same id as the component it will replace. Replacement id [[" + var10002 + "]], replaced id [[" + this.getId() + "]].");
        } else if (this.parent == null) {
            throw new IllegalStateException("This method can only be called on a component that has already been added to its parent.");
        } else {
            this.parent.replace(replacement);
            return replacement;
        }
    }

    public final boolean sameInnermostModel(Component component) {
        return this.sameInnermostModel(component.getDefaultModel());
    }

    public final boolean sameInnermostModel(IModel<?> model) {
        IModel<?> thisModel = this.getDefaultModel();
        if (thisModel != null && model != null) {
            return this.getInnermostModel(thisModel) == this.getInnermostModel(model);
        } else {
            return false;
        }
    }

    public final Component setEnabled(boolean enabled) {
        if (enabled != this.getFlag(128)) {
            if (this.isVersioned()) {
                Page page = this.findPage();
                if (page != null) {
                    this.addStateChange();
                }
            }

            this.setFlag(128, enabled);
            this.onEnabledStateChanged();
        }

        return this;
    }

    void clearEnabledInHierarchyCache() {
        this.setRequestFlag((short) 2, false);
    }

    void onEnabledStateChanged() {
        this.clearEnabledInHierarchyCache();
    }

    public final Component setEscapeModelStrings(boolean escapeMarkup) {
        this.setFlag(2, escapeMarkup);
        return this;
    }

    public final void setMarkupIdImpl(Object markupId) {
        if (markupId != null && !(markupId instanceof String) && !(markupId instanceof Integer)) {
            throw new IllegalArgumentException("markupId must be String or Integer");
        } else {
            this.setOutputMarkupId(true);
            if (markupId instanceof Integer) {
                this.generatedMarkupId = (Integer) markupId;
                this.setMetaData(MARKUP_ID_KEY, (Serializable) null);
            } else {
                this.generatedMarkupId = -1;
                this.setMetaData(MARKUP_ID_KEY, (Serializable) ((String) markupId));
            }
        }
    }

    final void setMarkupId(Component comp) {
        Args.notNull(comp, "comp");
        this.generatedMarkupId = comp.generatedMarkupId;
        this.setMetaData(MARKUP_ID_KEY, (Serializable) ((String) comp.getMetaData(MARKUP_ID_KEY)));
        if (comp.getOutputMarkupId()) {
            this.setOutputMarkupId(true);
        }

    }

    public Component setMarkupId(String markupId) {
        Args.notEmpty(markupId, "markupId");
        this.setMarkupIdImpl(markupId);
        return this;
    }

    public final <M extends Serializable> Component setMetaData(MetaDataKey<FeedbackMessages> key, Serializable object) {
        MetaDataEntry<?>[] old = this.getMetaData();
        Object metaData = null;
        MetaDataEntry<?>[] metaDataArray = key.set(old, object);
        if (metaDataArray != null && metaDataArray.length > 0) {
            metaData = metaDataArray.length > 1 ? metaDataArray : metaDataArray[0];
        }

        int index = this.getFlag(1048576) ? 1 : 0;
        if (old == null && metaData != null) {
            this.data_insert(index, metaData);
        } else if (old != null && metaData != null) {
            this.data_set(index, metaData);
        } else if (old != null && metaData == null) {
            this.data_remove(index);
        }

        return this;
    }

    public Component setDefaultModel(IModel<?> model) {
        IModel<?> prevModel = this.getModelImpl();
        IModel<?> wrappedModel = prevModel;
        if (prevModel instanceof IWrapModel) {
            wrappedModel = ((IWrapModel) prevModel).getWrappedModel();
        }

        if (wrappedModel != model) {
            if (prevModel != null) {
                prevModel.detach();
            }

            this.modelChanging();
            this.setModelImpl(this.wrap(model));
            this.modelChanged();
            this.setFlag(4, false);
        }

        return this;
    }

    IModel<?> getModelImpl() {
        return this.getFlag(1048576) ? (IModel) this.data_get(0) : null;
    }

    void setModelImpl(IModel<?> model) {
        if (this.getFlag(1048576)) {
            if (model != null) {
                this.data_set(0, model);
            } else {
                this.data_remove(0);
                this.setFlag(1048576, false);
            }
        } else if (model != null) {
            this.data_insert(0, model);
            this.setFlag(1048576, true);
        }

    }

    @SneakyThrows
    public final Component setDefaultModelObject(Object object) {
        IModel<?> model = this.getDefaultModel();
        if (model == null) {
            throw new IllegalStateException("Attempt to set a model object on a component without a model! Either pass an IModel to the constructor or use #setDefaultModel(new SomeModel(object)). Component: " + this.getPageRelativePath());
        } else if (!this.isActionAuthorized(ENABLE)) {
            throw new UnauthorizedActionException(this, ENABLE);
        } else {
            if (!this.getModelComparator().compare(this, object)) {
                this.modelChanging();

                try {
                    model.setObject(object);
                } catch (UnsupportedOperationException var4) {
                    UnsupportedOperationException uox = var4;
                    throw new WicketRuntimeException("You need to use writeable IModel for component " + this.getPageRelativePath(), uox);
                }

                this.modelChanged();
            }

            return this;
        }
    }

    public final Component setOutputMarkupId(boolean output) {
        this.setFlag(16384, output);
        return this;
    }

    public final Component setOutputMarkupPlaceholderTag(boolean outputTag) {
        if (outputTag != this.getFlag(32768)) {
            if (outputTag) {
                this.setOutputMarkupId(true);
                this.setFlag(32768, true);
            } else {
                this.setFlag(32768, false);
            }
        }

        return this;
    }

    public final Component setRenderBodyOnly(boolean renderTag) {
        this.setFlag(32, renderTag);
        return this;
    }

    public final <C extends IRequestablePage> void setResponsePage(Class<C> cls) {
        this.getRequestCycle().setResponsePage(cls, (PageParameters) null);
    }

    public final <C extends IRequestablePage> void setResponsePage(Class<C> cls, PageParameters parameters) {
        this.getRequestCycle().setResponsePage(cls, parameters);
    }

    public final void setResponsePage(IRequestablePage page) {
        this.getRequestCycle().setResponsePage(page);
    }

    public Component setVersioned(boolean versioned) {
        this.setFlag(8, versioned);
        return this;
    }

    public final Component setVisible(boolean visible) {
        if (visible != this.getFlag(16)) {
            this.addStateChange();
            this.setFlag(16, visible);
            this.onVisibleStateChanged();
        }

        return this;
    }

    void clearVisibleInHierarchyCache() {
        this.setRequestFlag((short) 8, false);
    }

    void onVisibleStateChanged() {
        this.clearVisibleInHierarchyCache();
    }

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean detailed) {
        try {
            StringBuilder buffer = new StringBuilder();
            buffer.append("[Component id = ").append(this.getId());
            if (detailed) {
                Page page = this.findPage();
                if (page == null) {
                    buffer.append(", page = <No Page>, path = ").append(this.getPath()).append('.').append(Classes.simpleName(this.getClass()));
                } else {
                    buffer.append(", page = ").append(Classes.name(this.getPage().getPageClass())).append(", path = ").append(this.getPageRelativePath()).append(", type = ").append(Classes.name((Class<? extends MarkupContainer>) this.getClass())).append(", isVisible = ").append(this.determineVisibility()).append(", isVersioned = ").append(this.isVersioned());
                }

                if (this.markup != null) {
                    buffer.append(", markup = ").append((new MarkupStream(this.getMarkup())).toString());
                }
            }

            buffer.append(']');
            return buffer.toString();
        } catch (Exception var4) {
            Exception e = var4;
            log.warn("Error while building toString()", e);
            return String.format("[Component id = %s <attributes are not available because exception %s was thrown during toString()>]", this.getId(), e.getClass().getName());
        }
    }

    public final <C extends Page> CharSequence urlFor(Class<C> pageClass, PageParameters parameters) {
        return (CharSequence) this.getRequestCycle().urlFor(pageClass, parameters);
    }

    public final CharSequence urlForListener(Behavior behaviour, PageParameters parameters) {
        int id = this.getBehaviorId(behaviour);
        IRequestHandler handler = this.createRequestHandler(parameters, id);
        return (CharSequence) this.getRequestCycle().urlFor(handler);
    }

    private IRequestHandler createRequestHandler(PageParameters parameters, Integer id) {
        Page page = this.getPage();
        PageAndComponentProvider provider = new PageAndComponentProvider(page, this, parameters);
        return (IRequestHandler) (!page.isPageStateless() && (!page.isBookmarkable() || !page.wasCreatedBookmarkable()) ? new ListenerRequestHandler(provider, id) : new BookmarkableListenerRequestHandler(provider, id));
    }

    public final CharSequence urlFor(IRequestHandler requestHandler) {
        return (CharSequence) this.getRequestCycle().urlFor(requestHandler);
    }

    public final CharSequence urlForListener(PageParameters parameters) {
        IRequestHandler handler = this.createRequestHandler(parameters, (Integer) null);
        return (CharSequence) this.getRequestCycle().urlFor(handler);
    }

    public final CharSequence urlFor(ResourceReference resourceReference, PageParameters parameters) {
        return (CharSequence) this.getRequestCycle().urlFor(resourceReference, parameters);
    }

    public final <R, C extends MarkupContainer> R visitParents(Class<C> parentClass, IVisitor<C, R> visitor) {
        return this.visitParents(parentClass, visitor, IVisitFilter.ANY);
    }

    public final <R, C extends MarkupContainer> R visitParents(Class<C> parentClass, IVisitor<C, R> visitor, IVisitFilter filter) {
        Args.notNull(filter, "filter");
        MarkupContainer current = this.getParent();

        for (Visit<R> visit = new Visit(); current != null; current = current.getParent()) {
            if (parentClass.isInstance(current) && filter.visitObject(current)) {
                visitor.component(current, visit);
                if (visit.isStopped()) {
                    return visit.getResult();
                }
            }
        }

        return null;
    }

    public final void warn(Serializable message) {
        this.getFeedbackMessages().warn(this, message);
        this.addStateChange();
    }

    private void notifyBehaviorsComponentBeforeRender() {
        Iterator var1 = this.getBehaviors().iterator();

        while (var1.hasNext()) {
            Behavior behavior = (Behavior) var1.next();
            if (this.isBehaviorAccepted(behavior)) {
                behavior.beforeRender(this);
            }
        }

    }

    private void notifyBehaviorsComponentRendered() {
        Iterator var1 = this.getBehaviors().iterator();

        while (var1.hasNext()) {
            Behavior behavior = (Behavior) var1.next();
            if (this.isBehaviorAccepted(behavior)) {
                behavior.afterRender(this);
            }
        }

    }

    protected final void addStateChange() {
        this.checkHierarchyChange(this);
        Page page = this.findPage();
        if (page != null) {
            page.componentStateChanging(this);
        }

    }

    protected final void checkComponentTag(ComponentTag tag, String name) {
        if (!tag.getName().equalsIgnoreCase(name)) {
            String msg = String.format("Component [%s] (path = [%s]) must be applied to a tag of type [%s], not: %s", this.getId(), this.getPath(), name, tag.toUserDebugString());
            this.findMarkupStream().throwMarkupException(msg);
        }

    }

    protected final void checkComponentTagAttribute(ComponentTag tag, String key, String... values) {
        if (key != null) {
            String tagAttributeValue = tag.getAttributes().getString(key);
            boolean found = false;
            if (tagAttributeValue != null) {
                String[] var6 = values;
                int var7 = values.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    String value = var6[var8];
                    if (value.equalsIgnoreCase(tagAttributeValue)) {
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                String msg = String.format("Component [%s] (path = [%s]) must be applied to a tag with [%s] attribute matching any of %s, not [%s]", this.getId(), this.getPath(), key, Arrays.toString(values), tagAttributeValue);
                this.findMarkupStream().throwMarkupException(msg);
            }
        }

    }

    @SneakyThrows
    protected void checkHierarchyChange(Component component) {
        if (this.getRequestFlag((short) 512) && !component.isAuto()) {
            throw new WicketRuntimeException("Cannot modify component hierarchy after render phase has started (page version cant change then anymore)");
        }
    }

    protected void detachModel() {
        IModel<?> model = this.getModelImpl();
        if (model != null) {
            model.detach();
        }

        if (model instanceof IWrapModel && !this.getFlag(4)) {
            ((IWrapModel) model).getWrappedModel().detach();
        }

    }

    protected final String exceptionMessage(String message) {
        return message + ":\n" + this.toString();
    }

    protected final MarkupStream findMarkupStream() {
        return new MarkupStream(this.getMarkup());
    }

    protected final Page findPage() {
        return (Page) (this instanceof Page ? this : (Component) this.findParent(Page.class));
    }

    public <M extends Behavior> List<M> getBehaviors(Class<M> type) {
        return Behaviors.getBehaviors(this, type);
    }

    protected final boolean getFlag(int flag) {
        return (this.flags & flag) != 0;
    }

    protected final boolean getRequestFlag(short flag) {
        return (this.requestFlags & flag) != 0;
    }

    @SneakyThrows
    protected final IModel<?> getInnermostModel(IModel<?> model) {
        IModel nested;
        IModel next;
        for (nested = model; nested != null && nested instanceof IWrapModel; nested = next) {
            next = ((IWrapModel) nested).getWrappedModel();
            if (nested == next) {
                throw new WicketRuntimeException("Model for " + nested + " is self-referential");
            }
        }

        return nested;
    }

    public IModelComparator getModelComparator() {
        return defaultModelComparator;
    }

    protected boolean getStatelessHint() {
        return true;
    }

    protected IModel<?> initModel() {
        IModel<?> foundModel = null;

        for (Component current = this.getParent(); current != null; current = ((Component) current).getParent()) {
            IModel<?> model = ((Component) current).getModelImpl();
            if (model instanceof IWrapModel && !(model instanceof IComponentInheritedModel)) {
                model = ((IWrapModel) model).getWrappedModel();
            }

            if (model instanceof IComponentInheritedModel) {
                foundModel = ((IComponentInheritedModel) model).wrapOnInheritance(this);
                this.setFlag(4, true);
                break;
            }
        }

        return foundModel;
    }

    protected void internalOnModelChanged() {
    }

    protected boolean isBehaviorAccepted(Behavior behavior) {
        return behavior.isEnabled(this);
    }

    protected final boolean isIgnoreAttributeModifier() {
        return this.getFlag(64);
    }

    protected void onAfterRender() {
        this.setRequestFlag((short) 2048, true);
    }

    protected void onBeforeRender() {
        this.setRequestFlag((short) 1024, true);
        this.onBeforeRenderChildren();
        this.setRequestFlag((short) 32, true);
    }

    protected void onComponentTag(ComponentTag tag) {
        if (this.getFlag(16384)) {
            tag.putInternal("id", this.getMarkupId());
        }

        DebugSettings debugSettings = this.getApplication().getDebugSettings();
        String componentPathAttributeName = debugSettings.getComponentPathAttributeName();
        if (!Strings.isEmpty(componentPathAttributeName)) {
            String path = this.getPageRelativePath();
            path = path.replace("_", "__");
            path = path.replace(':', '_');
            tag.put(componentPathAttributeName, path);
        }

        this.getMarkupSourcingStrategy().onComponentTag(this, tag);
    }

    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
    }

    protected void onDetach() {
        this.setRequestFlag((short) 4096, false);
    }

    protected void onRemove() {
        this.setRequestFlag((short) 8192, false);
    }

    protected void onModelChanged() {
    }

    protected void onModelChanging() {
    }

    protected abstract void onRender();

    protected final void renderComponentTag(ComponentTag tag) {
        if (this.needToRenderTag(tag)) {
            if (tag.hasBehaviors()) {
                tag = tag.mutable();

                Behavior behavior;
                for (Iterator<? extends Behavior> tagBehaviors = tag.getBehaviors(); tagBehaviors.hasNext(); behavior.detach(this)) {
                    behavior = (Behavior) tagBehaviors.next();
                    if (behavior.isEnabled(this)) {
                        behavior.onComponentTag(this, tag);
                    }
                }
            }

            List<? extends Behavior> behaviors = this.getBehaviors();
            if (behaviors != null && !behaviors.isEmpty() && !tag.isClose() && !this.isIgnoreAttributeModifier()) {
                tag = tag.mutable();
                Iterator var8 = behaviors.iterator();

                while (var8.hasNext()) {
                    Behavior behavior = (Behavior) var8.next();
                    if (this.isBehaviorAccepted(behavior)) {
                        behavior.onComponentTag(this, tag);
                    }
                }
            }

            if (tag instanceof WicketTag && !tag.isClose() && !this.getFlag(64)) {
                ExceptionSettings.NotRenderableErrorStrategy notRenderableErrorStrategy = ExceptionSettings.NotRenderableErrorStrategy.LOG_WARNING;
                if (Application.exists()) {
                    notRenderableErrorStrategy = this.getApplication().getExceptionSettings().getNotRenderableErrorStrategy();
                }

                String var10000 = tag.getNamespace();
                String tagName = var10000 + ":" + tag.getName();
                String componentId = this.getId();
                String message;
                if (this.getFlag(16384)) {
                    message = String.format("Markup id set on a component that is usually not rendered into markup. Markup id: %s, component id: %s, component tag: %s.", this.getMarkupId(), componentId, tagName);
                    if (notRenderableErrorStrategy == ExceptionSettings.NotRenderableErrorStrategy.THROW_EXCEPTION) {
                        throw new IllegalStateException(message);
                    }

                    log.warn(message);
                }

                if (this.getFlag(32768)) {
                    message = String.format("Placeholder tag set on a component that is usually not rendered into markup. Component id: %s, component tag: %s.", componentId, tagName);
                    if (notRenderableErrorStrategy == ExceptionSettings.NotRenderableErrorStrategy.THROW_EXCEPTION) {
                        throw new IllegalStateException(message);
                    }

                    log.warn(message);
                }
            }

            tag.writeOutput(this.getResponse(), !this.needToRenderTag((ComponentTag) null), this.getMarkup().getMarkupResourceStream().getWicketNamespace());
        }

    }

    protected final void replaceComponentTagBody(MarkupStream markupStream, ComponentTag tag, CharSequence body) {
        ComponentTag markupOpenTag = null;
        if (tag.isOpen()) {
            markupOpenTag = markupStream.getPreviousTag();
            if (markupOpenTag.isOpen()) {
                markupStream.skipRawMarkup();
            }
        }

        if (body != null) {
            this.getResponse().write(body);
        }

        if (tag.isOpen() && markupOpenTag != null && markupOpenTag.isOpen() && !markupStream.atCloseTag()) {
            markupStream.throwMarkupException("Expected close tag for '" + markupOpenTag + "' Possible attempt to embed component(s) '" + markupStream.get() + "' in the body of this component which discards its body");
        }

    }

    protected final Component setAuto(boolean auto) {
        this.setFlag(1, auto);
        return this;
    }

    protected final Component setFlag(int flag, boolean set) {
        if (set) {
            this.flags |= flag;
        } else {
            this.flags &= ~flag;
        }

        return this;
    }

    final Component setRequestFlag(short flag, boolean set) {
        if (set) {
            this.requestFlags |= flag;
        } else {
            this.requestFlags = (short) (this.requestFlags & ~flag);
        }

        return this;
    }

    protected final Component setIgnoreAttributeModifier(boolean ignore) {
        this.setFlag(64, ignore);
        return this;
    }

    protected final <V> IModel<V> wrap(IModel<V> model) {
        return (IModel) (model instanceof IComponentAssignedModel ? ((IComponentAssignedModel) model).wrapOnAssignment(this) : model);
    }

    void detachChildren() {
    }

    void removeChildren() {
    }

    public Component get(String path) {
        if (path.length() == 0) {
            return this;
        } else {
            throw new IllegalArgumentException(this.exceptionMessage("Component is not a container and so does not contain the path " + path));
        }
    }

    void internalMarkRendering(boolean setRenderingFlag) {
        this.setRequestFlag((short) 1024, false);
        this.setRequestFlag((short) 512, setRenderingFlag);
    }

    public final boolean isAuto() {
        for (Component current = this; current != null; current = ((Component) current).getParent()) {
            if (((Component) current).getFlag(1)) {
                return true;
            }
        }

        return false;
    }

    boolean isPreparedForRender() {
        return this.getRequestFlag((short) 1024);
    }

    void onBeforeRenderChildren() {
    }

    final void renderClosingComponentTag(MarkupStream markupStream, ComponentTag openTag, boolean renderBodyOnly) {
        if (openTag.isOpen()) {
            if (markupStream.atCloseTag() && markupStream.getTag().closes(openTag)) {
                if (!renderBodyOnly && this.needToRenderTag(openTag)) {
                    openTag.writeSyntheticCloseTag(this.getResponse());
                }
            } else if (openTag.requiresCloseTag()) {
                markupStream.throwMarkupException("Expected close tag for " + openTag);
            }
        }

    }

    @SneakyThrows
    private void checkId(String id) {
        if (!(this instanceof Page) && Strings.isEmpty(id)) {
            throw new WicketRuntimeException("Null or empty component ID's are not allowed.");
        } else if (id != null && (id.indexOf(58) != -1 || id.indexOf(126) != -1)) {
            throw new WicketRuntimeException("The component ID must not contain ':' or '~' chars.");
        }
    }

    public final void setParent(MarkupContainer parent) {
        if (this.parent != null && log.isDebugEnabled()) {
            log.debug("Replacing parent " + this.parent + " with " + parent);
        }

        this.parent = parent;
    }

    final void setRenderAllowed(boolean renderAllowed) {
        this.setFlag(8192, renderAllowed);
    }

    void setRenderAllowed() {
        this.setRenderAllowed(this.isActionAuthorized(RENDER));
    }

    public final Component setVisibilityAllowed(boolean allowed) {
        if (allowed != this.getFlag(1073741824)) {
            this.setFlag(1073741824, allowed);
            this.onVisibleStateChanged();
        }

        return this;
    }

    public final boolean isVisibilityAllowed() {
        return this.getFlag(1073741824);
    }

    public final boolean determineVisibility() {
        return this.isVisible() && this.isRenderAllowed() && this.isVisibilityAllowed();
    }

    public boolean isEnabledInHierarchy() {
        if (this.getRequestFlag((short) 2)) {
            return this.getRequestFlag((short) 1);
        } else {
            Component parent = this.getParent();
            boolean state;
            if (parent != null && !((Component) parent).isEnabledInHierarchy()) {
                state = false;
            } else {
                state = this.isEnabled() && this.isEnableAllowed();
            }

            this.setRequestFlag((short) 2, true);
            this.setRequestFlag((short) 1, state);
            return state;
        }
    }

    public final boolean isRendering() {
        return this.getRequestFlag((short) 1024) || this.getRequestFlag((short) 512);
    }

    public boolean canCallListener() {
        return this.isEnabledInHierarchy() && this.isVisibleInHierarchy();
    }

    public void renderHead(IHeaderResponse response) {
    }

    public void onEvent(IEvent<?> event) {
    }

    public final <T> void send(IEventSink sink, Broadcast type, T payload) {
        if (this.getApplication().getFrameworkSettings().hasAnyEventDispatchers()) {
            (new ComponentEventSender(this, this.getApplication().getFrameworkSettings())).send(sink, type, payload);
        }

    }

    public Component remove(Behavior... behaviors) {
        Behavior[] var2 = behaviors;
        int var3 = behaviors.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Behavior behavior = var2[var4];
            Behaviors.remove(this, behavior);
        }

        return this;
    }

    public final Behavior getBehaviorById(int id) {
        return Behaviors.getBehaviorById(this, id);
    }

    public final int getBehaviorId(Behavior behavior) {
        if (behavior.isTemporary(this)) {
            throw new IllegalArgumentException("Cannot get a stable id for temporary behavior " + behavior);
        } else {
            return Behaviors.getBehaviorId(this, behavior);
        }
    }

    public Component add(Behavior... behaviors) {
        Behaviors.add(this, behaviors);
        return this;
    }

    public final List<? extends Behavior> getBehaviors() {
        return this.getBehaviors((Class) null);
    }

    public boolean canCallListenerAfterExpiry() {
        return false;
    }

    protected void onReAdd() {
        this.setRequestFlag((short) 256, true);
    }

    protected void setMetaData(MetaDataKey<String> addedAtKey, Object added) {
    }
}

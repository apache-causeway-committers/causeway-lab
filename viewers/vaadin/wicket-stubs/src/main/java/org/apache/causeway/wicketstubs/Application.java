package org.apache.causeway.wicketstubs;

import java.net.URLConnection;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import lombok.SneakyThrows;

import org.apache.causeway.wicketstubs.api.Args;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.Generics;
import org.apache.causeway.wicketstubs.api.IEvent;

import org.apache.causeway.wicketstubs.api.IHeaderResponse;

import org.apache.causeway.wicketstubs.api.Page;

import org.apache.causeway.wicketstubs.api.RequestCycle;

import org.apache.causeway.wicketstubs.api.WicketRuntimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.causeway.wicketstubs.RuntimeConfigurationType.DEPLOYMENT;
import static org.apache.causeway.wicketstubs.RuntimeConfigurationType.DEVELOPMENT;

public abstract class Application implements ISessionStore.UnboundListener, IEventSink, IMetadataContext<Object, Application> {
    public static final String CONFIGURATION = "configuration";
    private static final Map<String, Application> applicationKeyToApplication = Generics.newHashMap(1);
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private IRequestMapper rootRequestMapper;
    private IConverterLocator converterLocator;
    private final List<IInitializer> initializers = Generics.newArrayList();
    private final ConcurrentHashMap<MetaDataKey<?>, Object> metaData = new ConcurrentHashMap();
    private String name;
    private IRequestLogger requestLogger;
    private volatile ISessionStore sessionStore;
    private IPageRendererProvider pageRendererProvider;
    private IRequestCycleProvider requestCycleProvider;
    private Supplier<IExceptionMapper> exceptionMapperProvider;
    private Supplier<ISessionStore> sessionStoreProvider;
    private HeaderResponseDecoratorCollection headerResponseDecorators = new HeaderResponseDecoratorCollection();
    private final ComponentOnBeforeRenderListenerCollection componentPreOnBeforeRenderListeners = new ComponentOnBeforeRenderListenerCollection();
    private final ComponentOnBeforeRenderListenerCollection componentPostOnBeforeRenderListeners = new ComponentOnBeforeRenderListenerCollection();
    private final ComponentOnAfterRenderListenerCollection componentOnAfterRenderListeners = new ComponentOnAfterRenderListenerCollection();
    private final RequestCycleListenerCollection requestCycleListeners = new RequestCycleListenerCollection();
    private final ApplicationListenerCollection applicationListeners = new ApplicationListenerCollection();
    private final SessionListenerCollection sessionListeners = new SessionListenerCollection();
    private final ComponentInstantiationListenerCollection componentInstantiationListeners = new ComponentInstantiationListenerCollection();
    private final ComponentInitializationListenerCollection componentInitializationListeners = new ComponentInitializationListenerCollection();
    private final ComponentOnConfigureListenerCollection componentOnConfigureListeners = new ComponentOnConfigureListenerCollection();
    private final HeaderContributorListenerCollection headerContributorListeners = new HeaderContributorListenerCollection();
    private final BehaviorInstantiationListenerCollection behaviorInstantiationListeners = new BehaviorInstantiationListenerCollection();
    private final OnComponentTagListenerCollection onComponentTagListeners = new OnComponentTagListenerCollection();
    private ApplicationSettings applicationSettings;
    private JavaScriptLibrarySettings javaScriptLibrarySettings;
    private DebugSettings debugSettings;
    private ExceptionSettings exceptionSettings;
    private FrameworkSettings frameworkSettings;
    private MarkupSettings markupSettings;
    private PageSettings pageSettings;
    private RequestCycleSettings requestCycleSettings;
    private RequestLoggerSettings requestLoggerSettings;
    private ResourceSettings resourceSettings;
    private SecuritySettings securitySettings;
    private StoreSettings storeSettings;
    private boolean settingsAccessible;
    private volatile IPageManager pageManager;
    private IPageManagerProvider pageManagerProvider;
    private ResourceReferenceRegistry resourceReferenceRegistry;
    private SharedResources sharedResources;
    private ResourceBundles resourceBundles;
    private IPageFactory pageFactory;
    private IMapperContext encoderContext;

    public static boolean exists() {
        return ThreadContext.getApplication() != null;
    }

    @SneakyThrows
    public static Application get() {
        Application application = ThreadContext.getApplication();
        if (application == null) {
            throw new WicketRuntimeException("There is no application attached to current thread " + Thread.currentThread().getName());
        } else {
            return application;
        }
    }

    public static Application get(String applicationKey) {
        return (Application) applicationKeyToApplication.get(applicationKey);
    }

    public static Set<String> getApplicationKeys() {
        return Collections.unmodifiableSet(applicationKeyToApplication.keySet());
    }

    public Application() {
        this.getComponentInstantiationListeners().add(new IComponentInstantiationListener() {
            public void onInstantiation(Component component) {
                Class<? extends Component> cl = component.getClass();
                if (!Session.get().getAuthorizationStrategy().isInstantiationAuthorized(cl)) {
                    Application.this.getSecuritySettings().getUnauthorizedComponentInstantiationListener().onUnauthorizedInstantiation(component);
                }

            }
        });
    }

    public final void configure() {
        RuntimeConfigurationType configurationType = this.getConfigurationType();
        if (configurationType.equals(DEVELOPMENT)) {
            this.getResourceSettings().setResourcePollFrequency(Duration.ofSeconds(1L));
            this.getResourceSettings().setJavaScriptCompressor((IJavaScriptCompressor) null);
            this.getResourceSettings().setUseMinifiedResources(false);
            this.getMarkupSettings().setStripWicketTags(false);
            this.getExceptionSettings().setUnexpectedExceptionDisplay(ExceptionSettings.SHOW_EXCEPTION_PAGE);
            this.getDebugSettings().setComponentUseCheck(true);
            this.getDebugSettings().setAjaxDebugModeEnabled(true);
            this.getDebugSettings().setDevelopmentUtilitiesEnabled(true);
            this.getRequestCycleSettings().addResponseFilter(EmptySrcAttributeCheckFilter.INSTANCE);
        } else if (configurationType.equals(DEPLOYMENT)) {
            this.getResourceSettings().setResourcePollFrequency((Duration) null);
            this.getResourceSettings().setJavaScriptCompressor(new DefaultJavaScriptCompressor());
            this.getMarkupSettings().setStripWicketTags(true);
            this.getExceptionSettings().setUnexpectedExceptionDisplay(ExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
            this.getDebugSettings().setComponentUseCheck(false);
            this.getDebugSettings().setAjaxDebugModeEnabled(false);
            this.getDebugSettings().setDevelopmentUtilitiesEnabled(false);
        }

    }

    public abstract String getApplicationKey();

    public abstract RuntimeConfigurationType getConfigurationType();

    public abstract Class<? extends Page> getHomePage();

    public final IConverterLocator getConverterLocator() {
        return this.converterLocator;
    }

    public final <T> T getMetaData(MetaDataKey<T> key) {
        return (T) this.metaData.get(key);
    }

    public final String getName() {
        return this.name;
    }

    public final IRequestLogger getRequestLogger() {
        if (this.getRequestLoggerSettings().isRequestLoggerEnabled()) {
            if (this.requestLogger == null) {
                this.requestLogger = this.newRequestLogger();
            }
        } else {
            this.requestLogger = null;
        }

        return this.requestLogger;
    }

    public final ISessionStore getSessionStore() {
        if (this.sessionStore == null) {
            synchronized (this) {
                if (this.sessionStore == null) {
                    this.sessionStore = (ISessionStore) this.sessionStoreProvider.get();
                    this.sessionStore.registerUnboundListener(this);
                }
            }
        }

        return this.sessionStore;
    }

    public void sessionUnbound(String sessionId) {
        this.getSessionListeners().onUnbound(sessionId);
    }

    public void logEventTarget(IRequestHandler target) {
    }

    public void logResponseTarget(IRequestHandler requestTarget) {
    }

    public abstract Session newSession(Request var1, Response var2);

    public final <T> Application setMetaData(MetaDataKey<T> key, T object) {
        this.metaData.put(key, object);
        return this;
    }

    private void addInitializer(String className) {
        IInitializer initializer = (IInitializer) WicketObjects.newInstance(className);
        if (initializer != null) {
            this.initializers.add(initializer);
        }

    }

    private void destroyInitializers() {
        Iterator var1 = this.initializers.iterator();

        while (var1.hasNext()) {
            IInitializer initializer = (IInitializer) var1.next();
            log.info("[{}] destroy: {}", this.getName(), initializer);
            initializer.destroy(this);
        }

    }

    private void initInitializers() {
        Iterator var1 = this.initializers.iterator();

        while (var1.hasNext()) {
            IInitializer initializer = (IInitializer) var1.next();
            log.info("[{}] init: {}", this.getName(), initializer);
            initializer.init(this);
        }

        ServiceLoader<IInitializer> serviceLoaderInitializers = ServiceLoader.load(IInitializer.class);
        Iterator var5 = serviceLoaderInitializers.iterator();

        while (var5.hasNext()) {
            IInitializer serviceLoaderInitializer = (IInitializer) var5.next();
            log.info("[{}] init: {}", this.getName(), serviceLoaderInitializer);
            serviceLoaderInitializer.init(this);
            this.initializers.add(serviceLoaderInitializer);
        }

    }

    protected void onDestroy() {
    }

    protected void init() {
    }

    public void internalDestroy() {
        this.applicationListeners.onBeforeDestroyed(this);
        IDetachListener detachListener = this.getFrameworkSettings().getDetachListener();
        if (detachListener != null) {
            detachListener.onDestroyListener();
        }

        PropertyResolver.destroy(this);
        MarkupFactory markupFactory = this.getMarkupSettings().getMarkupFactory();
        if (markupFactory.hasMarkupCache()) {
            markupFactory.getMarkupCache().shutdown();
        }

        this.onDestroy();
        this.destroyInitializers();
        this.internalGetPageManager().destroy();
        this.getSessionStore().destroy();
        applicationKeyToApplication.remove(this.getApplicationKey());
    }

    protected void internalInit() {
        this.settingsAccessible = true;
        PageSettings pageSettings = this.getPageSettings();
        pageSettings.addComponentResolver(new HtmlHeaderResolver());
        pageSettings.addComponentResolver(new WicketLinkTagHandler());
        pageSettings.addComponentResolver(new WicketMessageResolver());
        pageSettings.addComponentResolver(new RelativePathPrefixHandler());
        pageSettings.addComponentResolver(new EnclosureHandler());
        pageSettings.addComponentResolver(new InlineEnclosureHandler());
        pageSettings.addComponentResolver(new WicketMessageTagHandler());
        pageSettings.addComponentResolver(new WicketContainerResolver());
        this.getResourceSettings().getResourceFinders().add(new ClassPathResourceFinder(""));
        this.getResourceSettings().addResourceFactory("buttonFactory", new DefaultButtonImageResourceFactory());
        String applicationKey = this.getApplicationKey();
        applicationKeyToApplication.put(applicationKey, this);
        this.converterLocator = this.newConverterLocator();
        this.setPageManagerProvider(new DefaultPageManagerProvider(this));
        this.resourceReferenceRegistry = this.newResourceReferenceRegistry();
        this.sharedResources = this.newSharedResources(this.resourceReferenceRegistry);
        this.resourceBundles = this.newResourceBundles(this.resourceReferenceRegistry);
        this.setRootRequestMapper(new SystemMapper(this));
        this.pageFactory = this.newPageFactory();
        this.requestCycleProvider = RequestCycle::new;
        //FIXME this.exceptionMapperProvider = new DefaultExceptionMapper::new;
        this.getRequestCycleListeners().add(new RequestLoggerRequestCycleListener());
    }

    public Supplier<IExceptionMapper> getExceptionMapperProvider() {
        return this.exceptionMapperProvider;
    }

    public void setExceptionMapperProvider(Supplier<IExceptionMapper> exceptionMapperProvider) {
        this.exceptionMapperProvider = (Supplier) Args.notNull(exceptionMapperProvider, "exceptionMapperProvider");
    }

    public final Supplier<ISessionStore> getSessionStoreProvider() {
        return this.sessionStoreProvider;
    }

    public final Application setSessionStoreProvider(Supplier<ISessionStore> sessionStoreProvider) {
        this.sessionStoreProvider = (Supplier) Args.notNull(sessionStoreProvider, "sessionStoreProvider");
        this.sessionStore = null;
        return this;
    }

    protected IConverterLocator newConverterLocator() {
        return new ConverterLocator();
    }

    protected IRequestLogger newRequestLogger() {
        return new RequestLogger();
    }

    public final ICompoundRequestMapper getRootRequestMapperAsCompound() {
        IRequestMapper root = this.getRootRequestMapper();
        if (!(root instanceof ICompoundRequestMapper)) {
            root = (new SystemMapper(this)).add((IRequestMapper) root);
            this.setRootRequestMapper((IRequestMapper) root);
        }

        return (ICompoundRequestMapper) root;
    }

    public final IRequestMapper getRootRequestMapper() {
        return this.rootRequestMapper;
    }

    public final Application setRootRequestMapper(IRequestMapper rootRequestMapper) {
        this.rootRequestMapper = rootRequestMapper;
        return this;
    }

    public final void initApplication() {
        if (this.name == null) {
            throw new IllegalStateException("setName must be called before initApplication");
        } else {
            this.internalInit();
            this.initInitializers();
            this.init();
            this.applicationListeners.onAfterInitialized(this);
            this.validateInit();
        }
    }

    protected void validateInit() {
        if (this.getPageRendererProvider() == null) {
            throw new IllegalStateException("An instance of IPageRendererProvider has not yet been set on this Application. @see Application#setPageRendererProvider");
        } else if (this.getSessionStoreProvider() == null) {
            throw new IllegalStateException("An instance of ISessionStoreProvider has not yet been set on this Application. @see Application#setSessionStoreProvider");
        } else if (this.getPageManagerProvider() == null) {
            throw new IllegalStateException("An instance of IPageManagerProvider has not yet been set on this Application. @see Application#setPageManagerProvider");
        }
    }

    public final void setName(String name) {
        Args.notEmpty(name, "name");
        if (this.name != null) {
            throw new IllegalStateException("Application name can only be set once.");
        } else if (applicationKeyToApplication.get(name) != null) {
            throw new IllegalStateException("Application with name '" + name + "' already exists.'");
        } else {
            this.name = name;
            applicationKeyToApplication.put(name, this);
        }
    }

    public String getMimeType(String fileName) {
        return URLConnection.getFileNameMap().getContentTypeFor(fileName);
    }

    public void onEvent(IEvent<?> event) {
    }

    public final HeaderContributorListenerCollection getHeaderContributorListeners() {
        return this.headerContributorListeners;
    }

    public final List<IInitializer> getInitializers() {
        return Collections.unmodifiableList(this.initializers);
    }

    public final ApplicationListenerCollection getApplicationListeners() {
        return this.applicationListeners;
    }

    public final SessionListenerCollection getSessionListeners() {
        return this.sessionListeners;
    }

    public final BehaviorInstantiationListenerCollection getBehaviorInstantiationListeners() {
        return this.behaviorInstantiationListeners;
    }

    public final OnComponentTagListenerCollection getOnComponentTagListeners() {
        return this.onComponentTagListeners;
    }

    public final ComponentInstantiationListenerCollection getComponentInstantiationListeners() {
        return this.componentInstantiationListeners;
    }

    public final ComponentInitializationListenerCollection getComponentInitializationListeners() {
        return this.componentInitializationListeners;
    }

    public final ComponentOnConfigureListenerCollection getComponentOnConfigureListeners() {
        return this.componentOnConfigureListeners;
    }

    public final ComponentOnBeforeRenderListenerCollection getComponentPreOnBeforeRenderListeners() {
        return this.componentPreOnBeforeRenderListeners;
    }

    public final ComponentOnBeforeRenderListenerCollection getComponentPostOnBeforeRenderListeners() {
        return this.componentPostOnBeforeRenderListeners;
    }

    public final ComponentOnAfterRenderListenerCollection getComponentOnAfterRenderListeners() {
        return this.componentOnAfterRenderListeners;
    }

    public RequestCycleListenerCollection getRequestCycleListeners() {
        return this.requestCycleListeners;
    }

    public final ApplicationSettings getApplicationSettings() {
        this.checkSettingsAvailable();
        if (this.applicationSettings == null) {
            this.applicationSettings = new ApplicationSettings();
        }

        return this.applicationSettings;
    }

    public final Application setApplicationSettings(ApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;
        return this;
    }

    public final JavaScriptLibrarySettings getJavaScriptLibrarySettings() {
        this.checkSettingsAvailable();
        if (this.javaScriptLibrarySettings == null) {
            this.javaScriptLibrarySettings = new JavaScriptLibrarySettings();
        }

        return this.javaScriptLibrarySettings;
    }

    public final Application setJavaScriptLibrarySettings(JavaScriptLibrarySettings javaScriptLibrarySettings) {
        this.javaScriptLibrarySettings = javaScriptLibrarySettings;
        return this;
    }

    public final DebugSettings getDebugSettings() {
        this.checkSettingsAvailable();
        if (this.debugSettings == null) {
            this.debugSettings = new DebugSettings();
        }

        return this.debugSettings;
    }

    public final Application setDebugSettings(DebugSettings debugSettings) {
        this.debugSettings = debugSettings;
        return this;
    }

    public final ExceptionSettings getExceptionSettings() {
        this.checkSettingsAvailable();
        if (this.exceptionSettings == null) {
            this.exceptionSettings = new ExceptionSettings();
        }

        return this.exceptionSettings;
    }

    public final Application setExceptionSettings(ExceptionSettings exceptionSettings) {
        this.exceptionSettings = exceptionSettings;
        return this;
    }

    public final FrameworkSettings getFrameworkSettings() {
        this.checkSettingsAvailable();
        if (this.frameworkSettings == null) {
            this.frameworkSettings = new FrameworkSettings(this);
        }

        return this.frameworkSettings;
    }

    public final Application setFrameworkSettings(FrameworkSettings frameworkSettings) {
        this.frameworkSettings = frameworkSettings;
        return this;
    }

    public final PageSettings getPageSettings() {
        this.checkSettingsAvailable();
        if (this.pageSettings == null) {
            this.pageSettings = new PageSettings();
        }

        return this.pageSettings;
    }

    public final Application setPageSettings(PageSettings pageSettings) {
        this.pageSettings = pageSettings;
        return this;
    }

    public RequestCycleSettings getRequestCycleSettings() {
        this.checkSettingsAvailable();
        if (this.requestCycleSettings == null) {
            this.requestCycleSettings = new RequestCycleSettings();
        }

        return this.requestCycleSettings;
    }

    public final Application setRequestCycleSettings(RequestCycleSettings requestCycleSettings) {
        this.requestCycleSettings = requestCycleSettings;
        return this;
    }

    public MarkupSettings getMarkupSettings() {
        this.checkSettingsAvailable();
        if (this.markupSettings == null) {
 //           this.markupSettings = new MarkupSettings();
        }

        return this.markupSettings;
    }

    public final Application setMarkupSettings(MarkupSettings markupSettings) {
        this.markupSettings = markupSettings;
        return this;
    }

    public final RequestLoggerSettings getRequestLoggerSettings() {
        this.checkSettingsAvailable();
        if (this.requestLoggerSettings == null) {
            this.requestLoggerSettings = new RequestLoggerSettings();
        }

        return this.requestLoggerSettings;
    }

    public final Application setRequestLoggerSettings(RequestLoggerSettings requestLoggerSettings) {
        this.requestLoggerSettings = requestLoggerSettings;
        return this;
    }

    public final ResourceSettings getResourceSettings() {
        this.checkSettingsAvailable();
        if (this.resourceSettings == null) {
            this.resourceSettings = new ResourceSettings(this);
        }

        return this.resourceSettings;
    }

    public final Application setResourceSettings(ResourceSettings resourceSettings) {
        this.resourceSettings = resourceSettings;
        return this;
    }

    public final SecuritySettings getSecuritySettings() {
        this.checkSettingsAvailable();
        if (this.securitySettings == null) {
            this.securitySettings = new SecuritySettings();
        }

        return this.securitySettings;
    }

    public final Application setSecuritySettings(SecuritySettings securitySettings) {
        this.securitySettings = securitySettings;
        return this;
    }

    public final StoreSettings getStoreSettings() {
        this.checkSettingsAvailable();
        if (this.storeSettings == null) {
            this.storeSettings = new StoreSettings(this);
        }

        return this.storeSettings;
    }

    public final Application setStoreSettings(StoreSettings storeSettings) {
        this.storeSettings = storeSettings;
        return this;
    }

    @SneakyThrows
    protected void checkSettingsAvailable() {
        if (!this.settingsAccessible) {
            throw new WicketRuntimeException("Use Application.init() method for configuring your application object");
        }
    }

    public final IPageManagerProvider getPageManagerProvider() {
        return this.pageManagerProvider;
    }

    public final Application setPageManagerProvider(IPageManagerProvider provider) {
        this.pageManagerProvider = provider;
        return this;
    }

    final IPageManager internalGetPageManager() {
        if (this.pageManager == null) {
            synchronized (this) {
                if (this.pageManager == null) {
                    this.pageManager = (IPageManager) this.pageManagerProvider.get();
                }
            }
        }

        return this.pageManager;
    }

    public final IPageRendererProvider getPageRendererProvider() {
        return this.pageRendererProvider;
    }

    public final Application setPageRendererProvider(IPageRendererProvider pageRendererProvider) {
        Args.notNull(pageRendererProvider, "pageRendererProvider");
        this.pageRendererProvider = pageRendererProvider;
        return this;
    }

    protected ResourceReferenceRegistry newResourceReferenceRegistry() {
        return new ResourceReferenceRegistry();
    }

    public final ResourceReferenceRegistry getResourceReferenceRegistry() {
        return this.resourceReferenceRegistry;
    }

    protected SharedResources newSharedResources(ResourceReferenceRegistry registry) {
        return new SharedResources(registry);
    }

    public SharedResources getSharedResources() {
        return this.sharedResources;
    }

    protected ResourceBundles newResourceBundles(ResourceReferenceRegistry registry) {
        return new ResourceBundles(registry);
    }

    public ResourceBundles getResourceBundles() {
        return this.resourceBundles;
    }

    protected IPageFactory newPageFactory() {
        return new DefaultPageFactory();
    }

    public final IPageFactory getPageFactory() {
        return this.pageFactory;
    }

    public final IMapperContext getMapperContext() {
        if (this.encoderContext == null) {
            this.encoderContext = this.newMapperContext();
        }

        return this.encoderContext;
    }

    protected IMapperContext newMapperContext() {
        return new DefaultMapperContext(this);
    }

    public Session fetchCreateAndSetSession(RequestCycle requestCycle) {
        Args.notNull(requestCycle, "requestCycle");
        Session session = this.getSessionStore().lookup(requestCycle.getRequest());
        if (session == null) {
            session = this.newSession(requestCycle.getRequest(), requestCycle.getResponse());
            ThreadContext.setSession(session);
            this.internalGetPageManager().clear();
            this.sessionListeners.onCreated(session);
        } else {
            ThreadContext.setSession(session);
        }

        return session;
    }

    public final IRequestCycleProvider getRequestCycleProvider() {
        return this.requestCycleProvider;
    }

    public final Application setRequestCycleProvider(IRequestCycleProvider requestCycleProvider) {
        this.requestCycleProvider = requestCycleProvider;
        return this;
    }

    public final RequestCycle createRequestCycle(Request request, Response response) {
        RequestCycleContext context = new RequestCycleContext(request, response, this.getRootRequestMapper(), (IExceptionMapper) this.getExceptionMapperProvider().get());
        RequestCycle requestCycle = (RequestCycle) this.getRequestCycleProvider().apply(context);
        requestCycle.getListeners().add((IRequestCycleListener) this.requestCycleListeners);
        requestCycle.getListeners().add(new IRequestCycleListener() {
            public void onEndRequest(RequestCycle cycle) {
                Application.this.internalGetPageManager().end();
            }

            public void onDetach(RequestCycle requestCycle) {
                Application.this.internalGetPageManager().detach();
                IRequestLogger requestLogger = Application.this.getRequestLogger();
                if (requestLogger != null) {
                    requestLogger.requestTime(System.currentTimeMillis() - requestCycle.getStartTime());
                }

            }
        });
        return requestCycle;
    }

    public HeaderResponseDecoratorCollection getHeaderResponseDecorators() {
        return this.headerResponseDecorators;
    }

    public final IHeaderResponse decorateHeaderResponse(IHeaderResponse response) {
        return this.headerResponseDecorators.decorate(response);
    }

    public final boolean usesDevelopmentConfig() {
        return DEVELOPMENT.equals(this.getConfigurationType());
    }

    public final boolean usesDeploymentConfig() {
        return DEPLOYMENT.equals(this.getConfigurationType());
    }
}

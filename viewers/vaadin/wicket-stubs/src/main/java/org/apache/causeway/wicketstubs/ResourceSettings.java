package org.apache.causeway.wicketstubs;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.causeway.wicketstubs.api.Args;
import org.apache.causeway.wicketstubs.api.Generics;
import org.apache.commons.collections4.properties.PropertiesFactory;

public class ResourceSettings implements IPropertiesFactoryContext {
    private Localizer localizer;
    private final Map<String, IResourceFactory> nameToResourceFactory = Generics.newHashMap();
    private IPackageResourceGuard packageResourceGuard = new SecurePackageResourceGuard(new SecurePackageResourceGuard.SimpleCache(100));
    private IPropertiesFactory propertiesFactory;
    private List<IResourceFinder> resourceFinders = new ArrayList();
    private Duration resourcePollFrequency = null;
    private IResourceStreamLocator resourceStreamLocator;
    private IModificationWatcher resourceWatcher;
    private IFileCleaner fileCleaner;
    private final List<IStringResourceLoader> stringResourceLoaders = Generics.newArrayList(6);
    private boolean throwExceptionOnMissingResource = true;
    private boolean useDefaultOnMissingResource = true;
    private Duration defaultCacheDuration;
    private IJavaScriptCompressor javascriptCompressor;
    private ICssCompressor cssCompressor;
    private String parentFolderPlaceholder;
    private IResourceCachingStrategy resourceCachingStrategy;
    private final Application application;
    private boolean useMinifiedResources;
    private Comparator<? super ResourceAggregator.RecordedHeaderItem> headerItemComparator;
    private boolean encodeJSessionId;

    public ResourceSettings(Application application) {
        this.defaultCacheDuration = WebResponse.MAX_CACHE_DURATION;
        this.parentFolderPlaceholder = "::";
        this.useMinifiedResources = true;
        this.headerItemComparator = new PriorityFirstComparator(false);
        this.encodeJSessionId = false;
        this.application = application;
        this.stringResourceLoaders.add(new ComponentStringResourceLoader());
        this.stringResourceLoaders.add(new PackageStringResourceLoader());
        this.stringResourceLoaders.add(new ClassStringResourceLoader(application.getClass()));
        this.stringResourceLoaders.add(new ValidatorStringResourceLoader());
        this.stringResourceLoaders.add(new InitializerStringResourceLoader(application.getInitializers()));
    }

    public ResourceSettings addResourceFactory(String name, IResourceFactory resourceFactory) {
        this.nameToResourceFactory.put(name, resourceFactory);
        return this;
    }

    public Localizer getLocalizer() {
        if (this.localizer == null) {
            this.localizer = new Localizer();
        }

        return this.localizer;
    }

    public IPackageResourceGuard getPackageResourceGuard() {
        return this.packageResourceGuard;
    }

    public IPropertiesFactory getPropertiesFactory() {
        if (this.propertiesFactory == null) {
            this.propertiesFactory = new PropertiesFactory(this);
        }

        return this.propertiesFactory;
    }

    public IResourceFactory getResourceFactory(String name) {
        return (IResourceFactory) this.nameToResourceFactory.get(name);
    }

    public List<IResourceFinder> getResourceFinders() {
        return this.resourceFinders;
    }

    public Duration getResourcePollFrequency() {
        return this.resourcePollFrequency;
    }

    public IResourceStreamLocator getResourceStreamLocator() {
        if (this.resourceStreamLocator == null) {
            this.resourceStreamLocator = new ResourceStreamLocator(this.getResourceFinders());
            this.resourceStreamLocator = new CachingResourceStreamLocator(this.resourceStreamLocator);
        }

        return this.resourceStreamLocator;
    }

    public IModificationWatcher getResourceWatcher(boolean start) {
        if (this.resourceWatcher == null && start) {
            synchronized (this) {
                if (this.resourceWatcher == null) {
                    Duration pollFrequency = this.getResourcePollFrequency();
                    if (pollFrequency != null) {
                        this.resourceWatcher = new ModificationWatcher(pollFrequency);
                    }
                }
            }
        }

        return this.resourceWatcher;
    }

    public ResourceSettings setResourceWatcher(IModificationWatcher watcher) {
        this.resourceWatcher = watcher;
        return this;
    }

    public IFileCleaner getFileCleaner() {
        return this.fileCleaner;
    }

    public ResourceSettings setFileCleaner(IFileCleaner fileUploadCleaner) {
        this.fileCleaner = fileUploadCleaner;
        return this;
    }

    public List<IStringResourceLoader> getStringResourceLoaders() {
        return this.stringResourceLoaders;
    }

    public boolean getThrowExceptionOnMissingResource() {
        return this.throwExceptionOnMissingResource;
    }

    public boolean getUseDefaultOnMissingResource() {
        return this.useDefaultOnMissingResource;
    }

    public ResourceSettings setLocalizer(Localizer localizer) {
        this.localizer = localizer;
        return this;
    }

    public ResourceSettings setPackageResourceGuard(IPackageResourceGuard packageResourceGuard) {
        this.packageResourceGuard = (IPackageResourceGuard) Args.notNull(packageResourceGuard, "packageResourceGuard");
        return this;
    }

    public ResourceSettings setPropertiesFactory(IPropertiesFactory factory) {
        this.propertiesFactory = factory;
        return this;
    }

    public ResourceSettings setResourceFinders(List<IResourceFinder> resourceFinders) {
        Args.notNull(resourceFinders, "resourceFinders");
        this.resourceFinders = resourceFinders;
        this.resourceStreamLocator = null;
        return this;
    }

    public ResourceSettings setResourcePollFrequency(Duration resourcePollFrequency) {
        this.resourcePollFrequency = resourcePollFrequency;
        return this;
    }

    public ResourceSettings setResourceStreamLocator(IResourceStreamLocator resourceStreamLocator) {
        this.resourceStreamLocator = resourceStreamLocator;
        return this;
    }

    public ResourceSettings setThrowExceptionOnMissingResource(boolean throwExceptionOnMissingResource) {
        this.throwExceptionOnMissingResource = throwExceptionOnMissingResource;
        return this;
    }

    public ResourceSettings setUseDefaultOnMissingResource(boolean useDefaultOnMissingResource) {
        this.useDefaultOnMissingResource = useDefaultOnMissingResource;
        return this;
    }

    public final Duration getDefaultCacheDuration() {
        return this.defaultCacheDuration;
    }

    public final ResourceSettings setDefaultCacheDuration(Duration duration) {
        Args.notNull(duration, "duration");
        this.defaultCacheDuration = duration;
        return this;
    }

    public IJavaScriptCompressor getJavaScriptCompressor() {
        return this.javascriptCompressor;
    }

    public IJavaScriptCompressor setJavaScriptCompressor(IJavaScriptCompressor compressor) {
        IJavaScriptCompressor old = this.javascriptCompressor;
        this.javascriptCompressor = compressor;
        return old;
    }

    public ICssCompressor getCssCompressor() {
        return this.cssCompressor;
    }

    public ICssCompressor setCssCompressor(ICssCompressor compressor) {
        ICssCompressor old = this.cssCompressor;
        this.cssCompressor = compressor;
        return old;
    }

    public String getParentFolderPlaceholder() {
        return this.parentFolderPlaceholder;
    }

    public ResourceSettings setParentFolderPlaceholder(String sequence) {
        this.parentFolderPlaceholder = sequence;
        return this;
    }

    public IResourceCachingStrategy getCachingStrategy() {
        if (this.resourceCachingStrategy == null) {
            Object resourceVersion;
            if (this.application.usesDevelopmentConfig()) {
                resourceVersion = new RequestCycleCachedResourceVersion(new LastModifiedResourceVersion());
            } else {
                resourceVersion = new CachingResourceVersion(new MessageDigestResourceVersion());
            }

            this.resourceCachingStrategy = new FilenameWithVersionResourceCachingStrategy((IResourceVersion) resourceVersion);
        }

        return this.resourceCachingStrategy;
    }

    public ResourceSettings setCachingStrategy(IResourceCachingStrategy strategy) {
        if (strategy == null) {
            throw new NullPointerException("It is not allowed to set the resource caching strategy to value NULL. Please use " + NoOpResourceCachingStrategy.class.getName() + " instead.");
        } else {
            this.resourceCachingStrategy = strategy;
            return this;
        }
    }

    public ResourceSettings setUseMinifiedResources(boolean useMinifiedResources) {
        this.useMinifiedResources = useMinifiedResources;
        return this;
    }

    public boolean getUseMinifiedResources() {
        return this.useMinifiedResources;
    }

    public Comparator<? super ResourceAggregator.RecordedHeaderItem> getHeaderItemComparator() {
        return this.headerItemComparator;
    }

    public ResourceSettings setHeaderItemComparator(Comparator<? super ResourceAggregator.RecordedHeaderItem> headerItemComparator) {
        this.headerItemComparator = headerItemComparator;
        return this;
    }

    public boolean isEncodeJSessionId() {
        return this.encodeJSessionId;
    }

    public ResourceSettings setEncodeJSessionId(boolean encodeJSessionId) {
        this.encodeJSessionId = encodeJSessionId;
        return this;
    }
}

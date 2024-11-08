package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.CssHeaderItem;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;
import org.apache.causeway.wicketstubs.api.IPartialPageRequestHandler;
import org.apache.causeway.wicketstubs.api.JavaScriptHeaderItem;
import org.apache.causeway.wicketstubs.api.RequestCycle;
import org.apache.causeway.wicketstubs.api.ResourceReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResourceAggregator
        extends DecoratingHeaderResponse {
    private final Map<HeaderItem, RecordedHeaderItem> itemsToBeRendered = new LinkedHashMap();
    private final List<HeaderItem> domReadyItemsToBeRendered = new ArrayList();
    private final List<OnLoadHeaderItem> loadItemsToBeRendered = new ArrayList();
    private Component renderBase;
    private int indexInRequest;

    public ResourceAggregator(IHeaderResponse real) {
        super(real);
    }

    public boolean wasRendered(Object object) {
        boolean ret = super.wasRendered(object);
        if (!ret && object instanceof Component) {
            this.renderBase = (Component) object;
        }

        return ret;
    }

    @Override
    public void render(CssReferenceHeaderItem cssReferenceHeaderItem) {

    }

    @Override
    public void render(Object o) {

    }

    public void markRendered(Object object) {
        super.markRendered(object);
        if (object instanceof Component) {
            this.renderBase = null;
        }

    }

    private void recordHeaderItem(HeaderItem item, Set<HeaderItem> depsDone) {
        this.renderDependencies(item, depsDone);
        RecordedHeaderItem recordedItem = (RecordedHeaderItem) this.itemsToBeRendered.get(item);
        if (recordedItem == null) {
            recordedItem = new RecordedHeaderItem(item);
            this.itemsToBeRendered.put(item, recordedItem);
        }

        recordedItem.addLocation(this.renderBase, this.indexInRequest);
        ++this.indexInRequest;
    }

    private void renderDependencies(HeaderItem item, Set<HeaderItem> depsDone) {
        Iterator var3 = item.getDependencies().iterator();

        while (var3.hasNext()) {
            HeaderItem curDependency = (HeaderItem) var3.next();
            curDependency = this.getItemToBeRendered(curDependency);
            if (!depsDone.add(curDependency)) {
                throw new CircularDependencyException(depsDone, curDependency);
            }

            this.recordHeaderItem(curDependency, depsDone);
            depsDone.remove(curDependency);
        }

    }

    public void render(HeaderItem item) {
        item = this.getItemToBeRendered(item);
        if (!(item instanceof OnDomReadyHeaderItem) && !(item instanceof OnEventHeaderItem)) {
            if (item instanceof OnLoadHeaderItem) {
                this.renderDependencies(item, new LinkedHashSet());
                this.loadItemsToBeRendered.add((OnLoadHeaderItem) item);
            } else {
                Set<HeaderItem> depsDone = new LinkedHashSet();
                depsDone.add(item);
                this.recordHeaderItem(item, depsDone);
            }
        } else {
            this.renderDependencies(item, new LinkedHashSet());
            this.domReadyItemsToBeRendered.add(item);
        }

    }

    public void close() {
        this.renderHeaderItems();
        if (RequestCycle.get().find(IPartialPageRequestHandler.class).isPresent()) {
            this.renderSeparateEventScripts();
        } else {
            this.renderCombinedEventScripts();
        }

        super.close();
    }

    private void renderHeaderItems() {
        List<RecordedHeaderItem> sortedItemsToBeRendered = new ArrayList(this.itemsToBeRendered.values());
        Comparator<? super RecordedHeaderItem> headerItemComparator = Application.get().getResourceSettings().getHeaderItemComparator();
        if (headerItemComparator != null) {
            Collections.sort(sortedItemsToBeRendered, headerItemComparator);
        }

        Iterator var3 = sortedItemsToBeRendered.iterator();

        while (var3.hasNext()) {
            RecordedHeaderItem curRenderItem = (RecordedHeaderItem) var3.next();
            if (this.markItemRendered(curRenderItem.getItem())) {
                this.getRealResponse().render(curRenderItem.getItem());
            }
        }

    }

    private void renderCombinedEventScripts() {
        int domReadyLength = this.domReadyItemsToBeRendered.size() * 256;
        StringBuilder domReadScript = new StringBuilder(domReadyLength);
        Iterator var3 = this.domReadyItemsToBeRendered.iterator();

        while (var3.hasNext()) {
            HeaderItem curItem = (HeaderItem) var3.next();
            if (this.markItemRendered(curItem)) {
                domReadScript.append('\n');
                if (curItem instanceof OnDomReadyHeaderItem) {
                    domReadScript.append(((OnDomReadyHeaderItem) curItem).getJavaScript());
                } else if (curItem instanceof OnEventHeaderItem) {
                    domReadScript.append(((OnEventHeaderItem) curItem).getCompleteJavaScript());
                }

                domReadScript.append(';');
            }
        }

        if (domReadScript.length() > 0) {
            domReadScript.append("\nWicket.Event.publish(Wicket.Event.Topic.AJAX_HANDLERS_BOUND);\n");
            this.getRealResponse().render(OnDomReadyHeaderItem.forScript(domReadScript));
        }

        int onLoadLength = this.loadItemsToBeRendered.size() * 256;
        StringBuilder onLoadScript = new StringBuilder(onLoadLength);
        Iterator var5 = this.loadItemsToBeRendered.iterator();

        while (var5.hasNext()) {
            OnLoadHeaderItem curItem = (OnLoadHeaderItem) var5.next();
            if (this.markItemRendered(curItem)) {
                onLoadScript.append('\n');
                onLoadScript.append(curItem.getJavaScript());
                onLoadScript.append(';');
            }
        }

        if (onLoadScript.length() > 0) {
            this.getRealResponse().render(OnLoadHeaderItem.forScript(onLoadScript.append('\n')));
        }

    }

    private void renderSeparateEventScripts() {
        Iterator var1 = this.domReadyItemsToBeRendered.iterator();

        while (var1.hasNext()) {
            HeaderItem curItem = (HeaderItem) var1.next();
            if (this.markItemRendered(curItem)) {
                this.getRealResponse().render(curItem);
            }
        }

        var1 = this.loadItemsToBeRendered.iterator();

        while (var1.hasNext()) {
            OnLoadHeaderItem curItem = (OnLoadHeaderItem) var1.next();
            if (this.markItemRendered(curItem)) {
                this.getRealResponse().render(curItem);
            }
        }

    }

    private boolean markItemRendered(HeaderItem item) {
        if (this.wasRendered(item)) {
            return false;
        } else {
            if (item instanceof IWrappedHeaderItem) {
                this.getRealResponse().markRendered(((IWrappedHeaderItem) item).getWrapped());
            }

            this.getRealResponse().markRendered(item);
            Iterator var2 = item.getProvidedResources().iterator();

            while (var2.hasNext()) {
                HeaderItem curProvided = (HeaderItem) var2.next();
                this.getRealResponse().markRendered(curProvided);
            }

            return true;
        }
    }

    private HeaderItem getItemToBeRendered(HeaderItem item) {
        HeaderItem innerItem;
        for (innerItem = item; innerItem instanceof IWrappedHeaderItem; innerItem = ((IWrappedHeaderItem) innerItem).getWrapped()) {
        }

        if (this.getRealResponse().wasRendered(innerItem)) {
            return NoHeaderItem.get();
        } else {
            HeaderItem bundle = Application.get().getResourceBundles().findBundle(innerItem);
            if (bundle == null) {
                return item;
            } else {
                bundle = this.preserveDetails(item, bundle);
                if (item instanceof IWrappedHeaderItem) {
                    bundle = ((IWrappedHeaderItem) item).wrap(bundle);
                }

                return bundle;
            }
        }
    }

    protected HeaderItem preserveDetails(HeaderItem item, HeaderItem bundle) {
        HeaderItem resultBundle;
        if (item instanceof CssReferenceHeaderItem originalHeaderItem && bundle instanceof CssReferenceHeaderItem) {
            resultBundle = this.preserveCssDetails(originalHeaderItem, (CssReferenceHeaderItem) bundle);
        } else if (item instanceof JavaScriptReferenceHeaderItem originalHeaderItem && bundle instanceof JavaScriptReferenceHeaderItem) {
            resultBundle = this.preserveJavaScriptDetails(originalHeaderItem, (JavaScriptReferenceHeaderItem) bundle);
        } else {
            resultBundle = bundle;
        }

        return resultBundle;
    }

    private HeaderItem preserveJavaScriptDetails(JavaScriptReferenceHeaderItem item, JavaScriptReferenceHeaderItem bundle) {
        ResourceReference bundleReference = bundle.getReference();
        Object resultBundle;
        if (bundleReference instanceof ReplacementResourceBundleReference) {
            resultBundle = JavaScriptHeaderItem.forReference(bundleReference, item.getPageParameters(), item.getId()).setCharset(item.getCharset()).setDefer(item.isDefer()).setAsync(item.isAsync()).setNonce(item.getNonce());
        } else {
            resultBundle = bundle;
        }

        return (HeaderItem) resultBundle;
    }

    protected HeaderItem preserveCssDetails(CssReferenceHeaderItem item, CssReferenceHeaderItem bundle) {
        ResourceReference bundleReference = bundle.getReference();
        CssReferenceHeaderItem resultBundle;
        if (bundleReference instanceof ReplacementResourceBundleReference) {
            resultBundle = CssHeaderItem.forReference(bundleReference, item.getPageParameters(), item.getMedia());
        } else {
            resultBundle = bundle;
        }

        return resultBundle;
    }

    public static class RecordedHeaderItem {
        private final HeaderItem item;
        private final List<RecordedHeaderItemLocation> locations;
        private int minDepth = Integer.MAX_VALUE;

        public RecordedHeaderItem(HeaderItem item) {
            this.item = item;
            this.locations = new ArrayList();
        }

        void addLocation(Component renderBase, int indexInRequest) {
            this.locations.add(new RecordedHeaderItemLocation(renderBase, indexInRequest));
            this.minDepth = Integer.MAX_VALUE;
        }

        public HeaderItem getItem() {
            return this.item;
        }

        public List<RecordedHeaderItemLocation> getLocations() {
            return this.locations;
        }

        public int getMinDepth() {
            RecordedHeaderItemLocation location;
            if (this.minDepth == Integer.MAX_VALUE) {
                for (Iterator var1 = this.locations.iterator(); var1.hasNext(); this.minDepth = Math.min(this.minDepth, location.getDepth())) {
                    location = (RecordedHeaderItemLocation) var1.next();
                }
            }

            return this.minDepth;
        }

        public String toString() {
            return this.locations + ":" + this.item;
        }
    }

    public static class RecordedHeaderItemLocation {
        private final Component renderBase;
        private int indexInRequest;
        private int depth = -1;

        public RecordedHeaderItemLocation(Component renderBase, int indexInRequest) {
            this.renderBase = renderBase;
            this.indexInRequest = indexInRequest;
        }

        public Object getRenderBase() {
            return this.renderBase;
        }

        public int getIndexInRequest() {
            return this.indexInRequest;
        }

        public int getDepth() {
            if (this.depth == -1) {
                for (Component component = this.renderBase; component != null; component = ((Component) component).getParent()) {
                    ++this.depth;
                }
            }

            return this.depth;
        }

        public String toString() {
            return Classes.simpleName(this.renderBase.getClass());
        }
    }
}

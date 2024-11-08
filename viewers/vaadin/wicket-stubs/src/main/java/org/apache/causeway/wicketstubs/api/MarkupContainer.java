package org.apache.causeway.wicketstubs.api;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.causeway.wicketstubs.Application;
import org.apache.causeway.wicketstubs.Border;
import org.apache.causeway.wicketstubs.ChildToDequeueType;
import org.apache.causeway.wicketstubs.ClassVisitFilter;
import org.apache.causeway.wicketstubs.Classes;
import org.apache.causeway.wicketstubs.ComponentQueue;
import org.apache.causeway.wicketstubs.ComponentResolvers;
import org.apache.causeway.wicketstubs.ComponentStrings;
import org.apache.causeway.wicketstubs.DebugSettings;
import org.apache.causeway.wicketstubs.DequeueContext;
import org.apache.causeway.wicketstubs.DequeueTagAction;
import org.apache.causeway.wicketstubs.FeedbackMessages;
import org.apache.causeway.wicketstubs.IComponentInheritedModel;
import org.apache.causeway.wicketstubs.IMarkupFragment;
import org.apache.causeway.wicketstubs.IQueueRegion;
import org.apache.causeway.wicketstubs.IVisit;
import org.apache.causeway.wicketstubs.IVisitor;
import org.apache.causeway.wicketstubs.IWrapModel;
import org.apache.causeway.wicketstubs.MarkupElement;
import org.apache.causeway.wicketstubs.MarkupException;
import org.apache.causeway.wicketstubs.MarkupFactory;
import org.apache.causeway.wicketstubs.MarkupNotFoundException;
import org.apache.causeway.wicketstubs.MarkupStream;
import org.apache.causeway.wicketstubs.MarkupType;
import org.apache.causeway.wicketstubs.MetaDataKey;
import org.apache.causeway.wicketstubs.OutputMarkupContainerClassNameBehavior;
import org.apache.causeway.wicketstubs.Visit;
import org.apache.causeway.wicketstubs.Visits;
import org.apache.causeway.wicketstubs.WicketTag;

import org.apache.commons.collections4.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.causeway.applib.value.Markup;

import lombok.SneakyThrows;

public abstract class MarkupContainer extends Component implements Iterable<Component> {
    private static final long serialVersionUID = 1L;
    private static final int INITIAL_CHILD_LIST_CAPACITY = 12;
    static final int MAPIFY_THRESHOLD = 24;
    private static final Logger log = LoggerFactory.getLogger(MarkupContainer.class);
    private static final MetaDataKey<FeedbackMessages> REMOVALS_KEY = new MetaDataKey<FeedbackMessages>() {
        private static final long serialVersionUID = 1L;
    };
    private transient int modCounter;
    private Object children;
    private transient ComponentQueue queue;

    public MarkupContainer(String id) {
        this(id, (IModel) null);
    }

    public MarkupContainer(String id, IModel<?> model) {
        super(id, model);
        this.modCounter = 0;
    }

    @SneakyThrows
    public MarkupContainer add(Component... children) {
        Component[] var2 = children;
        int var3 = children.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Component child = var2[var4];
            Args.notNull(child, "child");
            if (this == child) {
                throw new IllegalArgumentException(this.exceptionMessage("Trying to add this component to itself."));
            }

            for (MarkupContainer parent = this.getParent(); parent != null; parent = parent.getParent()) {
                if (child == parent) {
                    String var10000 = this.toString(false);
                    String msg = "You can not add a component's parent as child to the component (loop): Component: " + var10000 + "; parent == child: " + parent.toString(false);
                    if (child instanceof Border.BorderBodyContainer) {
                        msg = msg + ". Please consider using Border.addToBorder(new " + Classes.simpleName(this.getClass()) + "(\"" + this.getId() + "\", ...) instead of add(...)";
                    }

                    throw new WicketRuntimeException(msg);
                }
            }

            this.checkHierarchyChange(child);
            if (log.isDebugEnabled()) {
                Logger var8 = log;
                String var10001 = child.getId();
                var8.debug("Add " + var10001 + " to " + this);
            }

            Component previousChild = this.children_put(child);
            if (previousChild != null && previousChild != child) {
                String var10003 = previousChild.getClass().getSimpleName();
                throw new IllegalArgumentException(this.exceptionMessage("A child '" + var10003 + "' with id '" + child.getId() + "' already exists"));
            }

            this.addedComponent(child);
        }

        return this;
    }

    public MarkupContainer addOrReplace(Component... children) {
        Component[] var2 = children;
        int var3 = children.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Component child = var2[var4];
            Args.notNull(child, "child");
            this.checkHierarchyChange(child);
            if (this.get(child.getId()) == null) {
                this.add(child);
            } else {
                this.replace(child);
            }
        }

        return this;
    }

    public final boolean autoAdd(Component component, MarkupStream markupStream) {
        Args.notNull(component, "component");
        component.setAuto(true);
        if (markupStream != null) {
            component.setMarkup(markupStream.getMarkupFragment());
        }

        this.children_remove(component.getId());
        this.add(component);
        return true;
    }

    public boolean contains(Component component, boolean recurse) {
        Args.notNull(component, "component");
        if (recurse) {
            MarkupContainer parent;
            for (Component current = component; current != null; current = parent) {
                parent = ((Component) current).getParent();
                if (parent == this) {
                    return true;
                }
            }

            return false;
        } else {
            return component.getParent() == this;
        }
    }

    public final Component get(String path) {
        if (Strings.isEmpty(path)) {
            return this;
        } else {
            MarkupContainer container = this;

            String id;
            for (id = Strings.firstPathComponent(path, ':'); "..".equals(id); id = Strings.firstPathComponent(path, ':')) {
                container = container.getParent();
                if (container == null) {
                    return null;
                }

                path = path.length() == id.length() ? "" : path.substring(id.length() + 1);
            }

            if (Strings.isEmpty(id)) {
                return container;
            } else {
                Component child = container.children_get(id);
                if (child != null) {
                    String path2 = Strings.afterFirstPathComponent(path, ':');
                    return child.get(path2);
                } else {
                    return null;
                }
            }
        }
    }

    @SneakyThrows
    public MarkupStream getAssociatedMarkupStream(boolean throwException) {
        IMarkupFragment markup = this.getAssociatedMarkup();
        if (markup != null) {
            return new MarkupStream(markup);
        } else if (throwException) {
            String var10002 = this.getMarkupType().getExtension();
            throw new MarkupNotFoundException("Markup of type '" + var10002 + "' for component '" + this.getClass().getName() + "' not found. Enable debug messages for org.apache.wicket.util.resource to get a list of all filenames tried.: " + this.toString());
        } else {
            return null;
        }
    }

    @SneakyThrows
    public IMarkupFragment getAssociatedMarkup() {
        try {
            Markup markup = MarkupFactory.get().getMarkup(this, false);
            return markup != null && markup != Markup.NO_MARKUP ? markup : null;
        } catch (MarkupException var2) {
            MarkupException ex = var2;
            throw ex;
        } catch (MarkupNotFoundException var3) {
            MarkupNotFoundException ex = var3;
            throw ex;
        } catch (WicketRuntimeException var4) {
            WicketRuntimeException ex = var4;
            String var10003 = this.getMarkupType().getExtension();
            throw new MarkupNotFoundException(this.exceptionMessage("Markup of type '" + var10003 + "' for component '" + this.getClass().getName() + "' not found. Enable debug messages for org.apache.wicket.util.resource to get a list of all filenames tried"), ex);
        }
    }

    private Markup getMarkup(MarkupContainer components, boolean b) {
        return null;
    }

    public IMarkupFragment getMarkup(Component child) {
        return this.getMarkupSourcingStrategy().getMarkup(this, child);
    }

    public MarkupType getMarkupType() {
        MarkupContainer parent = this.getParent();
        return parent != null ? parent.getMarkupType() : null;
    }

    public void internalAdd(Component child) {
        if (log.isDebugEnabled()) {
            Logger var10000 = log;
            String var10001 = child.getId();
            var10000.debug("internalAdd " + var10001 + " to " + this);
        }

        this.children_put(child);
        this.addedComponent(child);
    }

    public Iterator<Component> iterator() {
        class MarkupChildIterator implements Iterator<Component> {
            private int indexInRemovalsSinceLastUpdate;
            private int expectedModCounter = -1;
            private Component currentComponent = null;
            private Iterator<Component> internalIterator = null;

            MarkupChildIterator() {
            }

            public boolean hasNext() {
                this.refreshInternalIteratorIfNeeded();
                return this.internalIterator.hasNext();
            }

            public Component next() {
                this.refreshInternalIteratorIfNeeded();
                return this.currentComponent = (Component) this.internalIterator.next();
            }

            public void remove() {
                MarkupContainer.this.remove(this.currentComponent);
                this.refreshInternalIteratorIfNeeded();
            }

            private void refreshInternalIteratorIfNeeded() {
                if (this.expectedModCounter < MarkupContainer.this.modCounter) {
                    if (MarkupContainer.this.children == null) {
                        this.internalIterator = Collections.emptyIterator();
                    } else if (MarkupContainer.this.children instanceof Component) {
                        this.internalIterator = Collections.singleton((Component) MarkupContainer.this.children).iterator();
                    } else if (MarkupContainer.this.children instanceof List) {
                        List<Component> childrenList = (List) MarkupContainer.this.children();
                        this.internalIterator = childrenList.iterator();
                    } else {
                        Map<String, Component> childrenMap = (Map) MarkupContainer.this.children();
                        this.internalIterator = childrenMap.values().iterator();
                    }

                    this.currentComponent = this.findLastExistingChildAlreadyReturned(this.currentComponent);
                    this.expectedModCounter = MarkupContainer.this.modCounter;
                    if (this.currentComponent != null) {
                        while (this.internalIterator.hasNext() && this.internalIterator.next() != this.currentComponent) {
                        }
                    }

                }
            }

            private Component findLastExistingChildAlreadyReturned(Component current) {
                if (current == null) {
                    this.indexInRemovalsSinceLastUpdate = 0;
                } else {
                    LinkedList<RemovedChild> removals = MarkupContainer.this.removals_get();
                    if (removals != null) {
                        RemovedChild removal;
                        label32:
                        for (; current != null; current = removal.previousSibling) {
                            int i = this.indexInRemovalsSinceLastUpdate;

                            while (true) {
                                if (i >= removals.size()) {
                                    break label32;
                                }

                                removal = (RemovedChild) removals.get(i);
                                if (removal.removedChild == current || removal.removedChild == null) {
                                    break;
                                }

                                ++i;
                            }
                        }

                        this.indexInRemovalsSinceLastUpdate = removals.size();
                    }
                }

                return current;
            }
        }

        return new MarkupChildIterator();
    }

    public final Iterator<Component> iterator(Comparator<Component> comparator) {
        List<Component> sorted = this.copyChildren();
        Collections.sort(sorted, comparator);
        return sorted.iterator();
    }

    public MarkupContainer remove(Component component) {
        this.checkHierarchyChange(component);
        Args.notNull(component, "component");
        this.children_remove(component.getId());
        this.removedComponent(component);
        return this;
    }

    @SneakyThrows
    public MarkupContainer remove(String id) {
        Args.notNull(id, "id");
        Component component = this.get(id);
        if (component != null) {
            this.remove(component);
            return this;
        } else {
            throw new WicketRuntimeException("Unable to find a component with id '" + id + "' to remove");
        }
    }

    public MarkupContainer removeAll() {
        if (this.children != null) {
            this.addStateChange();
            Iterator var1 = this.iterator();

            while (var1.hasNext()) {
                Component child = (Component) var1.next();
                child.internalOnRemove();
                child.detach();
                child.setParent((MarkupContainer) null);
            }

            this.children = null;
            this.removals_add((Component) null, (Component) null);
        }

        return this;
    }

    public final void renderAssociatedMarkup(String openTagName) {
        MarkupStream associatedMarkupStream = new MarkupStream(this.getMarkup((Component) null));
        MarkupElement elem = associatedMarkupStream.get();
        if (!(elem instanceof ComponentTag)) {
            associatedMarkupStream.throwMarkupException("Expected the open tag. Markup for a " + openTagName + " component must begin a tag like '<wicket:" + openTagName + ">'");
        }

        ComponentTag associatedMarkupOpenTag = (ComponentTag) elem;
        if (!associatedMarkupOpenTag.isOpen() || !(associatedMarkupOpenTag instanceof WicketTag)) {
            associatedMarkupStream.throwMarkupException("Markup for a " + openTagName + " component must begin a tag like '<wicket:" + openTagName + ">'");
        }

        try {
            this.setIgnoreAttributeModifier(true);
            ClassOutputStrategy outputClassName = this.getApplication().getDebugSettings().getOutputMarkupContainerClassNameStrategy();
            if (outputClassName == ClassOutputStrategy.TAG_ATTRIBUTE) {
                associatedMarkupOpenTag.addBehavior(OutputMarkupContainerClassNameBehavior.INSTANCE);
            }

            this.renderComponentTag(associatedMarkupOpenTag);
            associatedMarkupStream.next();
            String className = null;
            if (outputClassName == ClassOutputStrategy.HTML_COMMENT) {
                className = Classes.name(this.getClass());
                this.getResponse().write("<!-- MARKUP FOR ");
                this.getResponse().write(className);
                this.getResponse().write(" BEGIN -->");
            }

            this.renderComponentTagBody(associatedMarkupStream, associatedMarkupOpenTag);
            if (outputClassName == ClassOutputStrategy.HTML_COMMENT) {
                this.getResponse().write("<!-- MARKUP FOR ");
                this.getResponse().write(className);
                this.getResponse().write(" END -->");
            }

            this.renderClosingComponentTag(associatedMarkupStream, associatedMarkupOpenTag, false);
        } finally {
            this.setIgnoreAttributeModifier(false);
        }

    }

    @SneakyThrows
    public MarkupContainer replace(Component child) {
        Args.notNull(child, "child");
        this.checkHierarchyChange(child);
        if (log.isDebugEnabled()) {
            Logger var10000 = log;
            String var10001 = child.getId();
            var10000.debug("Replacing " + var10001 + " in " + this);
        }

        if (child.getParent() != this) {
            Component replaced = this.children_put(child);
            if (replaced == null) {
                String var10003 = child.getId();
                throw new WicketRuntimeException(this.exceptionMessage("Cannot replace a component which has not been added: id='" + var10003 + "', component=" + child));
            }

            this.removedComponent(replaced);
            child.setMarkupId(replaced);
            this.addedComponent(child);
        }

        return this;
    }

    public MarkupContainer setDefaultModel(final IModel<?> model) {
        final IModel<?> previous = this.getModelImpl();
        super.setDefaultModel(model);
        if (previous instanceof IComponentInheritedModel) {
            this.visitChildren(new IVisitor<Component, Void>() {
                @Override
                public void component(MarkupContainer current, Visit<Void> visit) {

                }

                public void component(Component component, IVisit<Void> visit) {
                    IModel<?> compModel = component.getDefaultModel();
                    if (compModel instanceof IWrapModel) {
                        compModel = ((IWrapModel) compModel).getWrappedModel();
                    }

                    if (compModel == previous) {
                        component.setDefaultModel((IModel) null);
                    } else if (compModel == model) {
                        component.modelChanged();
                    }

                }
            });
        }

        return this;
    }

    public int size() {
        return this.children_size();
    }

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean detailed) {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(Classes.simpleName(this.getClass())).append(' ');
        buffer.append(super.toString(detailed));
        if (detailed && this.children_size() != 0) {
            buffer.append(", children = ");
            boolean first = true;

            Component child;
            for (Iterator var4 = this.iterator(); var4.hasNext(); buffer.append(child.toString())) {
                child = (Component) var4.next();
                if (first) {
                    buffer.append(' ');
                    first = false;
                }
            }
        }

        buffer.append(']');
        return buffer.toString();
    }

    public final <S extends Component, R> R visitChildren(Class<?> clazz, IVisitor<S, R> visitor) {
        return Visits.visitChildren(this, visitor, new ClassVisitFilter(clazz));
    }

    public final <R> R visitChildren(IVisitor<Component, R> visitor) {
        return Visits.visitChildren(this, visitor);
    }

    private void addedComponent(Component child) {
        Args.notNull(child, "child");
        MarkupContainer parent = child.getParent();
        if (parent != null && parent != this) {
            parent.remove(child);
        }

        child.setParent(this);
        DebugSettings debugSettings = Application.get().getDebugSettings();
        if (debugSettings.isLinePreciseReportingOnAddComponentEnabled() && debugSettings.getComponentUseCheck()) {
            child.setMetaData(ADDED_AT_KEY, ComponentStrings.toString(child, new MarkupException("added")));
        }

        Page page = this.findPage();
        if (page != null) {
            page.componentAdded(child);
            if (page.isInitialized()) {
                child.internalInitialize();
            }
        }

        if (this.isPreparedForRender()) {
            child.beforeRender();
        }

    }

    public final void internalInitialize() {
        super.fireInitialize();
        this.visitChildren(new IVisitor<Component, Void>() {
            @Override
            public void component(MarkupContainer current, Visit<Void> visit) {

            }

            public void component(Component component, IVisit<Void> visit) {
                component.fireInitialize();
            }
        });
    }

    private <T> T children() {
        return (T) this.children;
    }

    private Component children_get(String childId) {
        if (this.children == null) {
            return null;
        } else if (this.children instanceof Component) {
            Component child = (Component) this.children();
            return child.getId().equals(childId) ? child : null;
        } else if (this.children instanceof List) {
            List<Component> kids = (List) this.children();
            Iterator var3 = kids.iterator();

            Component child;
            do {
                if (!var3.hasNext()) {
                    return null;
                }

                child = (Component) var3.next();
            } while (!child.getId().equals(childId));

            return child;
        } else {
            Map<String, Component> kids = (Map) this.children();
            return (Component) kids.get(childId);
        }
    }

    private void children_remove(String childId) {
        if (this.children instanceof Component) {
            Component oldChild = (Component) this.children();
            if (oldChild.getId().equals(childId)) {
                this.children = null;
                this.removals_add(oldChild, (Component) null);
            }
        } else {
            Component prevChild;
            if (this.children instanceof List) {
                List<Component> childrenList = (List) this.children();
                Iterator<Component> it = childrenList.iterator();

                Component child;
                for (prevChild = null; it.hasNext(); prevChild = child) {
                    child = (Component) it.next();
                    if (child.getId().equals(childId)) {
                        it.remove();
                        this.removals_add(child, prevChild);
                        if (childrenList.size() == 1) {
                            this.children = childrenList.get(0);
                        }

                        return;
                    }
                }
            } else if (this.children instanceof LinkedMap) {
                LinkedMap<String, Component> childrenMap = (LinkedMap) this.children();
                if (childrenMap.containsKey(childId)) {
                    String prevSiblingId = (String) childrenMap.previousKey(childId);
                    prevChild = (Component) childrenMap.remove(childId);
                    this.removals_add(prevChild, (Component) childrenMap.get(prevSiblingId));
                    if (childrenMap.size() == 1) {
                        this.children = childrenMap.values().iterator().next();
                    }
                }
            }
        }

    }

    private int children_size() {
        if (this.children == null) {
            return 0;
        } else if (this.children instanceof Component) {
            return 1;
        } else if (this.children instanceof List) {
            List<?> kids = (List) this.children();
            return kids.size();
        } else {
            return ((Map) this.children).size();
        }
    }

    private Component children_put(Component child) {
        if (this.children == null) {
            this.children = child;
            ++this.modCounter;
            return null;
        } else {
            Component oldChild;
            if (this.children instanceof Component) {
                oldChild = (Component) this.children();
                if (oldChild.getId().equals(child.getId())) {
                    this.children = child;
                    return oldChild;
                } else {
                    oldChild = (Component) this.children();
                    List<Component> newChildren = new ArrayList(12);
                    newChildren.add(oldChild);
                    newChildren.add(child);
                    this.children = newChildren;
                    ++this.modCounter;
                    return null;
                }
            } else if (!(this.children instanceof List)) {
                Map<String, Component> childrenMap = (Map) this.children();
                oldChild = (Component) childrenMap.put(child.getId(), child);
                if (oldChild == null) {
                    ++this.modCounter;
                }

                return oldChild;
            } else {
                List<Component> childrenList = (List) this.children();

                for (int i = 0; i < childrenList.size(); ++i) {
                    Component curChild = (Component) childrenList.get(i);
                    if (curChild.getId().equals(child.getId())) {
                        return (Component) childrenList.set(i, child);
                    }
                }

                ++this.modCounter;
                if (childrenList.size() < 24) {
                    childrenList.add(child);
                } else {
                    Map<String, Component> newChildren = new LinkedMap(48);
                    Iterator var10 = childrenList.iterator();

                    while (var10.hasNext()) {
                        Component curChild = (Component) var10.next();
                        newChildren.put(curChild.getId(), curChild);
                    }

                    newChildren.put(child.getId(), child);
                    this.children = newChildren;
                }

                return null;
            }
        }
    }

    private LinkedList<RemovedChild> removals_get() {
        return this.getRequestFlag((short) 16384) ? (LinkedList) this.getMetaData(REMOVALS_KEY) : null;
    }

    private void removals_set(LinkedList<RemovedChild> removals) {
        this.setRequestFlag((short) 16384, removals != null);
        this.setMetaData(REMOVALS_KEY, removals);
    }

    private void removals_clear() {
        if (this.getRequestFlag((short) 16384)) {
            this.removals_set((LinkedList) null);
        }

    }

    private void removals_add(Component removedChild, Component prevSibling) {
        ++this.modCounter;
        LinkedList<RemovedChild> removals = this.removals_get();
        if (removals == null) {
            removals = new LinkedList();
            this.removals_set(removals);
        }

        removals.add(new RemovedChild(removedChild, prevSibling));
    }

    private void removedComponent(Component component) {
        Page page = component.findPage();
        if (page != null) {
            page.componentRemoved(component);
        }

        component.detach();
        component.internalOnRemove();
        component.setParent((MarkupContainer) null);
    }

    protected boolean renderNext(MarkupStream markupStream) {
        MarkupElement element = markupStream.get();
        if (element instanceof ComponentTag tag && !markupStream.atCloseTag()) {
            if (tag instanceof WicketTag && ((WicketTag) tag).isFragmentTag()) {
                return false;
            } else {
                String id = tag.getId();
                Component component = this.get(id);
                if (component == null) {
                    component = ComponentResolvers.resolve(this, markupStream, tag, (ComponentResolvers.ResolverFilter) null);
                    if (component != null && component.getParent() == null) {
                        this.autoAdd(component, markupStream);
                    } else if (component != null) {
                        component.setMarkup(markupStream.getMarkupFragment());
                    }
                }

                if (component != null) {
                    component.render();
                } else {
                    if (tag.getFlag(32)) {
                        if (this.canRenderRawTag(tag)) {
                            this.getResponse().write(element.toCharSequence());
                        }

                        return true;
                    }

                    this.throwException(markupStream, tag);
                }

                return false;
            }
        } else {
            if (this.canRenderRawTag(element)) {
                this.getResponse().write(element.toCharSequence());
            }

            return true;
        }
    }

    private boolean canRenderRawTag(MarkupElement tag) {
        boolean isWicketTag = tag instanceof WicketTag;
        boolean stripTag = isWicketTag ? Application.get().getMarkupSettings().getStripWicketTags() : false;
        return !stripTag;
    }

    private void throwException(MarkupStream markupStream, ComponentTag tag) {
        String id = tag.getId();
        if (tag instanceof WicketTag) {
            if (((WicketTag) tag).isChildTag()) {
                String var10001 = tag.toString();
                markupStream.throwMarkupException("Found " + var10001 + " but no <wicket:extend>. Container: " + this.toString());
            } else {
                markupStream.throwMarkupException("Failed to handle: " + tag.toString() + ". It might be that no resolver has been registered to handle this special tag.  But it also could be that you declared wicket:id=" + id + " in your markup, but that you either did not add the component to your page at all, or that the hierarchy does not match. Container: " + this.toString());
            }
        }

        List<String> names = this.findSimilarComponents(id);
        StringBuilder msg = new StringBuilder(500);
        msg.append("Unable to find component with id '");
        msg.append(id);
        msg.append("' in ");
        msg.append(this.toString());
        msg.append("\n\tExpected: '");
        msg.append(this.getPageRelativePath());
        msg.append(':');
        msg.append(id);
        msg.append("'.\n\tFound with similar names: '");
        msg.append(Strings.join("', ", names));
        msg.append('\'');
        log.error(msg.toString());
        markupStream.throwMarkupException(msg.toString());
    }

    private List<String> findSimilarComponents(final String id) {
        final List<String> names = Generics.newArrayList();
        Page page = this.findPage();
        if (page != null) {
            page.visitChildren(new IVisitor<Component, Void>() {
                @Override
                public void component(MarkupContainer current, Visit<Void> visit) {

                }

                public void component(Component component, IVisit<Void> visit) {
                    if (Strings.getLevenshteinDistance(id.toLowerCase(Locale.ROOT), component.getId().toLowerCase(Locale.ROOT)) < 3) {
                        names.add(component.getPageRelativePath());
                    }

                }
            });
        }

        return names;
    }

    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        this.renderComponentTagBody(markupStream, openTag);
    }

    protected void onRender() {
        this.internalRenderComponent();
    }

    private void renderComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        if (markupStream != null && markupStream.getCurrentIndex() > 0) {
            ComponentTag origOpenTag = (ComponentTag) markupStream.get(markupStream.getCurrentIndex() - 1);
            if (origOpenTag.isOpenClose()) {
                return;
            }
        }

        boolean render = openTag.requiresCloseTag();
        if (!render) {
            render = !openTag.hasNoCloseTag();
        }

        if (render) {
            this.renderAll(markupStream, openTag);
        }

    }

    @SneakyThrows
    protected final void renderAll(MarkupStream markupStream, ComponentTag openTag) {
        while (markupStream.isCurrentIndexInsideTheStream() && (openTag == null || !markupStream.get().closes(openTag))) {
            int index = markupStream.getCurrentIndex();
            boolean rawMarkup = this.renderNext(markupStream);
            markupStream.setCurrentIndex(index);
            if (rawMarkup) {
                markupStream.next();
            } else {
                if (markupStream.getTag().isClose()) {
                    throw new WicketRuntimeException("Ups. This should never happen. " + markupStream.toString());
                }

                markupStream.skipComponent();
            }
        }

    }

    void removeChildren() {
        super.removeChildren();
        Iterator var1 = this.iterator();

        while (var1.hasNext()) {
            Component component = (Component) var1.next();
            component.internalOnRemove();
        }

    }

    void detachChildren() {
        super.detachChildren();
        Iterator var1 = this.iterator();

        while (var1.hasNext()) {
            Component component = (Component) var1.next();
            component.detach();
        }

    }

    void internalMarkRendering(boolean setRenderingFlag) {
        super.internalMarkRendering(setRenderingFlag);
        Iterator var2 = this.iterator();

        while (var2.hasNext()) {
            Component child = (Component) var2.next();
            child.internalMarkRendering(setRenderingFlag);
        }

    }

    private List<Component> copyChildren() {
        if (this.children == null) {
            return Collections.emptyList();
        } else if (this.children instanceof Component) {
            return Collections.singletonList((Component) this.children);
        } else {
            return this.children instanceof List ? new ArrayList((List) this.children) : new ArrayList(((Map) this.children).values());
        }
    }

    @SneakyThrows
    void onBeforeRenderChildren() {
        super.onBeforeRenderChildren();

        try {
            Iterator var4 = this.iterator();

            while (var4.hasNext()) {
                Component child = (Component) var4.next();
                if (child.getParent() == this) {
                    child.beforeRender();
                }
            }

        } catch (RuntimeException var3) {
            RuntimeException ex = var3;
            if (ex instanceof WicketRuntimeException) {
                throw ex;
            } else {
                throw new WicketRuntimeException("Error attaching this container for rendering: " + this, ex);
            }
        }
    }

    void onEnabledStateChanged() {
        super.onEnabledStateChanged();
        this.visitChildren(new IVisitor<Component, Void>() {
            @Override
            public void component(MarkupContainer current, Visit<Void> visit) {

            }

            public void component(Component component, IVisit<Void> visit) {
                component.clearEnabledInHierarchyCache();
            }
        });
    }

    void onVisibleStateChanged() {
        super.onVisibleStateChanged();
        this.visitChildren(new IVisitor<Component, Void>() {
            @Override
            public void component(MarkupContainer current, Visit<Void> visit) {

            }

            public void component(Component component, IVisit<Void> visit) {
                component.clearVisibleInHierarchyCache();
            }
        });
    }

    @SneakyThrows
    protected void onDetach() {
        super.onDetach();
        ++this.modCounter;
        this.removals_clear();
        if (this.queue != null && !this.queue.isEmpty() && this.hasBeenRendered()) {
            throw new WicketRuntimeException(String.format("Detach called on component with id '%s' while it had a non-empty queue: %s", this.getId(), this.queue));
        }
    }

    public MarkupContainer queue(Component... components) {
        if (this.queue == null) {
            this.queue = new ComponentQueue();
        }

        this.queue.add(components);
        Page page = this.findPage();
        if (page != null) {
            this.dequeue();
        }

        return this;
    }

    public void dequeue() {
        if (this instanceof IQueueRegion) {
            DequeueContext dequeue = this.newDequeueContext();
            this.dequeuePreamble(dequeue);
        } else {
            MarkupContainer queueRegion = (MarkupContainer) this.findParent(IQueueRegion.class);
            if (queueRegion == null) {
                return;
            }

            MarkupContainer anchestor = this;

            boolean hasQueuedChildren;
            for (hasQueuedChildren = !this.isQueueEmpty(); !hasQueuedChildren && anchestor != queueRegion; hasQueuedChildren = !anchestor.isQueueEmpty()) {
                anchestor = anchestor.getParent();
            }

            if (hasQueuedChildren && !queueRegion.getRequestFlag((short) 128)) {
                queueRegion.dequeue();
            }
        }

    }

    protected void onInitialize() {
        super.onInitialize();
        this.dequeue();
    }

    private boolean isQueueEmpty() {
        return this.queue == null || this.queue.isEmpty();
    }

    private boolean isQueueRegion() {
        return IQueueRegion.class.isInstance(this);
    }

    protected void dequeuePreamble(DequeueContext dequeue) {
        if (this.getRequestFlag((short) 128)) {
            throw new IllegalStateException("This container is already dequeing: " + this);
        } else {
            this.setRequestFlag((short) 128, true);

            try {
                if (dequeue != null) {
                    // if (null != dequeue.peekTag()) {
                    this.dequeue(dequeue);
                    //}

                    return;
                }
            } finally {
                this.setRequestFlag((short) 128, false);
            }

        }
    }

    public void dequeue(DequeueContext dequeue) {
        while (dequeue.isAtOpenOrOpenCloseTag()) {
            ComponentTag tag = dequeue.takeTag();
            Component child = this.findChildComponent(tag);
            if (child == null) {
                child = dequeue.findComponentToDequeue(tag);
                if (child == null && tag.getAutoComponentFactory() != null) {
                    ComponentTag.IAutoComponentFactory autoComponentFactory = tag.getAutoComponentFactory();
                    child = autoComponentFactory.newComponent(this, tag);
                }

                if (child != null) {
                    this.addDequeuedComponent(child, tag);
                }
            }

            if (tag.isOpen() && !tag.hasNoCloseTag()) {
                this.dequeueChild(child, tag, dequeue);
            }
        }

    }

    protected Component findChildComponent(ComponentTag tag) {
        return this.get(tag.getId());
    }

    private void dequeueChild(Component child, ComponentTag tag, DequeueContext dequeue) {
        ChildToDequeueType childType = ChildToDequeueType.fromChild(child);
        if (childType == ChildToDequeueType.QUEUE_REGION || childType == ChildToDequeueType.BORDER) {
            ((IQueueRegion) child).dequeue();
        }

        if (childType == ChildToDequeueType.BORDER) {
            Border childContainer = (Border) child;
            MarkupContainer body = childContainer.getBodyContainer();
            this.dequeueChildrenContainer(dequeue, body);
        }

        if (childType == ChildToDequeueType.MARKUP_CONTAINER) {
            MarkupContainer childContainer = (MarkupContainer) child;
            this.dequeueChildrenContainer(dequeue, childContainer);
        }

        if (childType == ChildToDequeueType.NULL || childType == ChildToDequeueType.QUEUE_REGION) {
            dequeue.skipToCloseTag();
        }

        ComponentTag close = dequeue.takeTag();

        do {
            if (close != null && close.closes(tag)) {
                return;
            }
        } while ((close = dequeue.takeTag()) != null);

        throw new IllegalStateException(String.format("Could not find the closing tag for '%s'", tag));
    }

    private void dequeueChildrenContainer(DequeueContext dequeue, MarkupContainer child) {
        dequeue.pushContainer(child);
        child.dequeue(dequeue);
        dequeue.popContainer();
    }

    public DequeueContext newDequeueContext() {
        IMarkupFragment markup = this.getRegionMarkup();
        return markup == null ? null : new DequeueContext(markup, this, false);
    }

    public IMarkupFragment getRegionMarkup() {
        return this.getAssociatedMarkup();
    }

    protected DequeueTagAction canDequeueTag(ComponentTag tag) {
        if (tag instanceof WicketTag wicketTag) {
            if (wicketTag.isContainerTag()) {
                return DequeueTagAction.DEQUEUE;
            } else if (wicketTag.getAutoComponentFactory() != null) {
                return DequeueTagAction.DEQUEUE;
            } else if (wicketTag.isFragmentTag()) {
                return DequeueTagAction.SKIP;
            } else if (wicketTag.isChildTag()) {
                return DequeueTagAction.IGNORE;
            } else if (wicketTag.isHeadTag()) {
                return DequeueTagAction.SKIP;
            } else {
                return wicketTag.isLinkTag() ? DequeueTagAction.DEQUEUE : null;
            }
        } else {
            return tag.isAutoComponentTag() && tag.getId().startsWith("label_attr") ? DequeueTagAction.IGNORE : DequeueTagAction.DEQUEUE;
        }
    }

    public Component findComponentToDequeue(ComponentTag tag) {
        return this.queue == null ? null : this.queue.remove(tag.getId());
    }

    protected void addDequeuedComponent(Component component, ComponentTag tag) {
        this.add(component);
    }

    public Stream<Component> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Stream<Component> streamChildren() {
        class ChildrenIterator<C> implements Iterator<C> {
            private Iterator<C> currentIterator;
            private Deque<Iterator<C>> iteratorStack = new ArrayDeque();

            private ChildrenIterator(Iterator<C> iterator) {
                this.currentIterator = iterator;
            }

            public boolean hasNext() {
                while (!this.currentIterator.hasNext() && !this.iteratorStack.isEmpty()) {
                    this.currentIterator = (Iterator) this.iteratorStack.pop();
                }

                return this.currentIterator.hasNext();
            }

            public C next() {
                C child = this.currentIterator.next();
                if (child instanceof Iterable) {
                    this.iteratorStack.push(this.currentIterator);
                    this.currentIterator = ((Iterable) child).iterator();
                }

                return child;
            }
        }

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ChildrenIterator(this.iterator()), 0), false);
    }

    private static class RemovedChild implements Serializable {
        private static final long serialVersionUID = 1L;
        private final transient Component removedChild;
        private final transient Component previousSibling;

        private RemovedChild(Component removedChild, Component previousSibling) {
            this.removedChild = removedChild;
            this.previousSibling = previousSibling;
        }
    }

    private class BorderBodyContainer {
    }

    public class ClassOutputStrategy {
        public static final ClassOutputStrategy HTML_COMMENT = null;
        public static final ClassOutputStrategy TAG_ATTRIBUTE = null;
    }
}

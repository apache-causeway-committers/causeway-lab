//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package org.apache.causeway.wicketstubs.api;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.causeway.applib.value.Markup;
import org.apache.causeway.wicketstubs.MarkupStream;
import org.apache.causeway.wicketstubs.MetaDataKey;

public abstract class MarkupContainer
        extends Component
        implements Iterable<Component> {
    private static final long serialVersionUID = 1L;
    private static final int INITIAL_CHILD_LIST_CAPACITY = 12;
    static final int MAPIFY_THRESHOLD = 24;
    private static final Logger log = LoggerFactory.getLogger(MarkupContainer.class);
    private static final MetaDataKey<LinkedList<RemovedChild>> REMOVALS_KEY = new MetaDataKey<LinkedList<RemovedChild>>() {
        private static final long serialVersionUID = 1L;
    };
    private transient int modCounter;
    private Object children;
    private transient ComponentQueue queue;

    public MarkupContainer() {
        super(null);
    }

    public MarkupContainer(String id, IModel<?> model) {
        super(id, model);
        this.modCounter = 0;
    }

    public MarkupContainer add(Component... children) {
        return this;
    }

    private String exceptionMessage(String s) {
        return null;
    }

    public final boolean autoAdd(Component component, MarkupStream markupStream) {
        return true;
    }

    public boolean contains(Component component, boolean recurse) {
        return false;
    }

    public final Component get(String path) {
        return null;
    }

    public Markup getAssociatedMarkup() {
        return null;
    }

    public MarkupType getMarkupType() {
        return null;
    }

    public Iterator<Component> iterator() {
        class MarkupChildIterator implements Iterator<Component> {
            private Component currentComponent = null;

            MarkupChildIterator() {
            }

            public boolean hasNext() {
                return null != currentComponent;
            }

            public Component next() {
                return currentComponent;
            }

            public void remove() {
            }

            private Component findLastExistingChildAlreadyReturned(Component current) {
                return current;
            }
        }

        return new MarkupChildIterator();
    }

    public MarkupContainer remove(Component component) {
        return null;
    }

    public MarkupContainer remove(String id) {
        return null;
    }

    public MarkupContainer replace(Component child) {
        return this;
    }

    public MarkupContainer setDefaultModel(final IModel<?> model) {
        return this;
    }

    public int size() {
        return this.children_size();
    }

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean detailed) {
        return null;
    }

    public final <R> R visitChildren(IVisitor<Component, R> visitor) {
        return Visits.visitChildren(this, visitor);
    }

    private int children_size() {
        return 0;
    }

    private LinkedList<RemovedChild> removals_get() {
        return null;
    }

    private void removals_set(LinkedList<RemovedChild> removals) {
    }

    public MarkupContainer queue(Component... components) {
        return this;
    }

    public void dequeue() {
    }

    public void dequeue(DequeueContext dequeue) {
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

    public void addOrReplace(Component component) {
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
}

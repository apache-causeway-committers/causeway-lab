package org.apache.causeway.wicketstubs;

import lombok.SneakyThrows;

import org.apache.causeway.wicketstubs.api.Args;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.IModel;
import org.apache.causeway.wicketstubs.api.MarkupContainer;
import org.apache.causeway.wicketstubs.api.WebMarkupContainer;

public abstract class Border extends WebMarkupContainer implements IComponentResolver, IQueueRegion {
    private static final long serialVersionUID = 1L;
    public static final String BODY = "body";
    public static final String BORDER = "border";
    private final BorderBodyContainer body;

    public Border(String id) {
        this(id, (IModel) null);
    }

    public Border(String id, IModel<?> model) {
        super(id, model);
        this.body = new BorderBodyContainer(id + "_body");
        this.queueToBorder(this.body);
    }

    public final BorderBodyContainer getBodyContainer() {
        return this.body;
    }

    public Border add(Component... children) {
        Component[] var2 = children;
        int var3 = children.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            if (component != this.body && !component.isAuto()) {
                this.getBodyContainer().add(new Component[]{component});
            } else {
                this.addToBorder(component);
            }
        }

        return this;
    }

    public Border addOrReplace(Component... children) {
        Component[] var2 = children;
        int var3 = children.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            if (component == this.body) {
                super.addOrReplace(new Component[]{component});
            } else {
                this.getBodyContainer().addOrReplace(new Component[]{component});
            }
        }

        return this;
    }

    public Border remove(Component component) {
        if (component == this.body) {
            this.removeFromBorder(component);
        } else {
            this.getBodyContainer().remove(component);
        }

        return this;
    }

    public Border remove(String id) {
        if (this.body.getId().equals(id)) {
            super.remove(id);
        } else {
            this.getBodyContainer().remove(id);
        }

        return this;
    }

    public Border removeAll() {
        this.getBodyContainer().removeAll();
        return this;
    }

    public Border replace(Component replacement) {
        if (this.body.getId().equals(replacement.getId())) {
            this.replaceInBorder(replacement);
        } else {
            this.getBodyContainer().replace(replacement);
        }

        return this;
    }

    public Border addToBorder(Component... children) {
        super.add(children);
        return this;
    }

    public Border queue(Component... components) {
        this.getBodyContainer().queue(components);
        return this;
    }

    protected void onConfigure() {
        super.onConfigure();
        this.dequeue();
    }

    public Border queueToBorder(Component... children) {
        super.queue(children);
        return this;
    }

    public Border removeFromBorder(Component child) {
        super.remove(child);
        return this;
    }

    public Border replaceInBorder(Component component) {
        super.replace(component);
        return this;
    }

    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {
        return !this.body.rendering && TagUtils.isWicketBodyTag(tag) ? this.body : null;
    }

    protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
        return (IMarkupSourcingStrategy) new BorderMarkupSourcingStrategy();
    }

    @SneakyThrows
    public IMarkupFragment getMarkup(Component child) {
        IMarkupFragment markup = this.getAssociatedMarkup();
        if (markup == null) {
            throw new MarkupException("Unable to find associated markup file for Border: " + this.toString());
        } else {
            IMarkupFragment borderMarkup = null;

            for (int i = 0; i < markup.size(); ++i) {
                MarkupElement elem = markup.get(i);
                if (TagUtils.isWicketBorderTag(elem)) {
                    borderMarkup = new MarkupFragment(markup, i);
                    break;
                }
            }

            if (borderMarkup == null) {
                throw new MarkupException(markup.getMarkupResourceStream(), "Unable to find <wicket:border> tag in associated markup file for Border: " + this.toString());
            } else if (child == null) {
                return borderMarkup;
            } else if (child == this.body) {
                return this.body.getMarkup();
            } else {
                IMarkupFragment childMarkup = borderMarkup.find(child.getId());
                return childMarkup != null ? childMarkup : ((BorderMarkupSourcingStrategy) this.getMarkupSourcingStrategy()).findMarkupInAssociatedFileHeader(this, child);
            }
        }
    }

    protected DequeueTagAction canDequeueTag(ComponentTag tag) {
        return this.canDequeueBody(tag) ? DequeueTagAction.DEQUEUE : super.canDequeueTag(tag);
    }

    public Component findComponentToDequeue(ComponentTag tag) {
        if (this.canDequeueBody(tag)) {
            tag.setId(this.body.getId());
        }

        return super.findComponentToDequeue(tag);
    }

    private boolean canDequeueBody(ComponentTag tag) {
        boolean isBodyTag = tag instanceof WicketTag && ((WicketTag) tag).isBodyTag();
        return isBodyTag;
    }

    protected void addDequeuedComponent(Component component, ComponentTag tag) {
        super.add(new Component[]{component});
    }

    public IMarkupFragment getRegionMarkup() {
        IMarkupFragment markup = super.getRegionMarkup();
        if (markup == null) {
            return markup;
        } else {
            IMarkupFragment borderMarkup = MarkupUtil.findStartTag(markup, "border");
            return borderMarkup != null ? borderMarkup : markup;
        }
    }

    public class BorderBodyContainer extends WebMarkupContainer implements IQueueRegion {
        private static final long serialVersionUID = 1L;
        private transient IMarkupFragment markup;
        protected boolean rendering;

        public BorderBodyContainer(String id) {
            super(id);
        }

        protected void onComponentTag(ComponentTag tag) {
            if (tag.isOpenClose()) {
                tag.setType(TagType.OPEN);
                tag.setModified(true);
            }

            super.onComponentTag(tag);
        }

        public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
            if (markupStream.getPreviousTag().isOpen()) {
                markupStream.skipRawMarkup();
            }

            IMarkupFragment markup = Border.this.getMarkup();
            MarkupStream stream = new MarkupStream(markup);
            ComponentTag tag = stream.getTag();
            stream.next();
            super.onComponentTagBody(stream, tag);
        }

        protected void onRender() {
            this.rendering = true;

            try {
                super.onRender();
            } finally {
                this.rendering = false;
            }

        }

        public IMarkupFragment getMarkup() {
            if (this.markup == null) {
                this.markup = this.findByName(this.getParent().getMarkup((Component) null), "body");
            }

            return this.markup;
        }

        private IMarkupFragment findByName(IMarkupFragment markup, String name) {
            Args.notEmpty(name, "name");
            MarkupStream stream = new MarkupStream(markup);
            stream.skipUntil(ComponentTag.class);
            stream.next();

            while (stream.skipUntil(ComponentTag.class)) {
                ComponentTag tag = stream.getTag();
                if ((tag.isOpen() || tag.isOpenClose()) && TagUtils.isWicketBodyTag(tag)) {
                    return stream.getMarkupFragment();
                }

                stream.next();
            }

            return null;
        }

        public IMarkupFragment getMarkup(Component child) {
            IMarkupFragment markup = Border.this.getMarkup();
            if (markup == null) {
                return null;
            } else {
                return child == null ? markup : markup.find(child.getId());
            }
        }

        public DequeueContext newDequeueContext() {
            Border border = (Border) this.findParent(Border.class);
            IMarkupFragment fragment = border.getMarkup();
            return fragment == null ? null : new DequeueContext(fragment, this, true);
        }

        public Component findComponentToDequeue(ComponentTag tag) {
            Component component = super.findComponentToDequeue(tag);
            if (component != null) {
                return component;
            } else {
                for (MarkupContainer cursor = this.getParent(); cursor != null; cursor = cursor.getParent()) {
                    component = cursor.findComponentToDequeue(tag);
                    if (component != null) {
                        return component;
                    }

                    if (cursor instanceof BorderBodyContainer) {
                        break;
                    }
                }

                return null;
            }
        }
    }
}

package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.ComponentTag;
import org.apache.causeway.wicketstubs.api.MarkupContainer;
import org.apache.causeway.wicketstubs.api.RequestCycle;
import org.apache.causeway.wicketstubs.api.WebMarkupContainer;

public class TransparentWebMarkupContainer
        extends WebMarkupContainer
        implements IComponentResolver {
    private static final long serialVersionUID = 1L;

    public TransparentWebMarkupContainer(String id) {
        super(id);
    }

    public Component resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {
        if (tag instanceof WicketTag && ((WicketTag) tag).isFragmentTag()) {
            return null;
        } else {
            Component resolvedComponent = this.getParent().get(tag.getId());
            return resolvedComponent != null && this.getPage().wasRendered(resolvedComponent) ? null : resolvedComponent;
        }
    }

    public void internalRenderHead(HtmlHeaderContainer container) {
        if (this.isAjaxRequest() && !this.isParentRendering()) {
            this.renderHeadForInnerSiblings(container);
        }

        super.internalRenderHead(container);
    }

    private boolean isParentRendering() {
        MarkupContainer parent = this.getParent();
        return parent != null && parent.isRendering();
    }

    private boolean isAjaxRequest() {
        Request request = RequestCycle.get().getRequest();
        if (request instanceof WebRequest webRequest) {
            return webRequest.isAjax();
        } else {
            return false;
        }
    }

    private void renderHeadForInnerSiblings(HtmlHeaderContainer container) {
        MarkupStream stream = new MarkupStream(this.getMarkup());

        while (stream.isCurrentIndexInsideTheStream()) {
            MarkupElement childOpenTag = stream.nextOpenTag();
            if (childOpenTag instanceof ComponentTag tag && !stream.atCloseTag()) {
                String id = tag.getId();
                Component component = null;
                if (this.get(id) == null) {
                    component = ComponentResolvers.resolveByComponentHierarchy(this, stream, tag);
                }

                if (component != null) {
                    component.internalRenderHead(container);
                }

                stream.skipToMatchingCloseTag(tag);
            }
        }

    }

    protected Component findChildComponent(ComponentTag tag) {
        Component childComponent = super.findChildComponent(tag);
        return childComponent != null ? childComponent : this.resolve(this, (MarkupStream) null, tag);
    }
}

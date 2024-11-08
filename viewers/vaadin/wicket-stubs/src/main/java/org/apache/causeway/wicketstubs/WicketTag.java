package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.ComponentTag;

public class WicketTag extends ComponentTag {
    public WicketTag(XmlTag tag) {
        super(tag);
    }

    public WicketTag(ComponentTag tag) {
        super(tag.getXmlTag());
        tag.copyPropertiesTo(this);
    }

    public final boolean isContainerTag() {
        return "container".equalsIgnoreCase(this.getName());
    }

    public final boolean isLinkTag() {
        return "link".equalsIgnoreCase(this.getName());
    }

    public final boolean isRemoveTag() {
        return "remove".equalsIgnoreCase(this.getName());
    }

    public final boolean isBodyTag() {
        return "body".equalsIgnoreCase(this.getName());
    }

    public final boolean isChildTag() {
        return "child".equalsIgnoreCase(this.getName());
    }

    public final boolean isExtendTag() {
        return "extend".equalsIgnoreCase(this.getName());
    }

    public final boolean isHeadTag() {
        return "head".equalsIgnoreCase(this.getName());
    }

    public final boolean isHeaderItemsTag() {
        return "header-items".equalsIgnoreCase(this.getName());
    }

    public final boolean isMessageTag() {
        return "message".equalsIgnoreCase(this.getName());
    }

    public final boolean isPanelTag() {
        return "panel".equalsIgnoreCase(this.getName());
    }

    public final boolean isBorderTag() {
        return "border".equalsIgnoreCase(this.getName());
    }

    public final boolean isFragmentTag() {
        return "fragment".equalsIgnoreCase(this.getName());
    }

    public final boolean isEnclosureTag() {
        return "enclosure".equalsIgnoreCase(this.getName());
    }

    public final boolean isMajorWicketComponentTag() {
        return this.isPanelTag() || this.isBorderTag() || this.isExtendTag();
    }

    public ComponentTag mutable() {
        if (this.xmlTag.isMutable()) {
            return this;
        } else {
            WicketTag tag = new WicketTag(this.xmlTag.mutable());
            this.copyPropertiesTo(tag);
            return tag;
        }
    }
}

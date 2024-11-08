package org.apache.causeway.wicketstubs.api;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.causeway.wicketstubs.HtmlHandler;
import org.apache.causeway.wicketstubs.IValueMap;
import org.apache.causeway.wicketstubs.MarkupElement;
import org.apache.causeway.wicketstubs.MarkupStream;
import org.apache.causeway.wicketstubs.Response;
import org.apache.causeway.wicketstubs.XmlTag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentTag extends MarkupElement {
    private static final Logger log = LoggerFactory.getLogger(ComponentTag.class);
    private static final int AUTOLINK = 1;
    private static final int MODIFIED = 2;
    private static final int IGNORE = 4;
    private static final int AUTO_COMPONENT = 8;
    private static final int NO_CLOSE_TAG = 16;
    public static final int RENDER_RAW = 32;
    public static final int CONTAINS_WICKET_ID = 64;
    private ComponentTag openTag;
    protected final XmlTag xmlTag;
    private int flags;
    private String id;
    private WeakReference<Class<? extends Component>> markupClassRef;
    private List<Behavior> behaviors;
    private Map<String, Object> userData;
    private IAutoComponentFactory autoComponentFactory;

    public ComponentTag(String name, XmlTag.TagType type) {
        this.flags = 0;
        this.markupClassRef = null;
        XmlTag tag = new XmlTag();
        tag.setName(name);
        tag.setType(type);
        this.xmlTag = tag;
    }

    public ComponentTag(XmlTag tag) {
        this.flags = 0;
        this.markupClassRef = null;
        this.xmlTag = tag;
    }

    public ComponentTag(ComponentTag tag) {
        this(tag.getXmlTag());
        tag.copyPropertiesTo(this);
    }

    public final void setFlag(int flag, boolean set) {
        if (set) {
            this.flags |= flag;
        } else {
            this.flags &= ~flag;
        }

    }

    public final boolean getFlag(int flag) {
        return (this.flags & flag) != 0;
    }

    public final void addBehavior(Behavior behavior) {
        Args.notNull(behavior, "behavior");
        if (this.behaviors == null) {
            this.behaviors = Generics.newArrayList();
        }

        this.behaviors.add(behavior);
    }

    public final boolean hasBehaviors() {
        return this.behaviors != null;
    }

    public final Iterator<? extends Behavior> getBehaviors() {
        if (this.behaviors == null) {
            List<Behavior> lst = Collections.emptyList();
            return lst.iterator();
        } else {
            return Collections.unmodifiableCollection(this.behaviors).iterator();
        }
    }

    public final boolean closes(MarkupElement open) {
        if (!(open instanceof ComponentTag)) {
            return false;
        } else {
            return this.openTag == open || this.getXmlTag().closes(((ComponentTag) open).getXmlTag());
        }
    }

    public final void enableAutolink(boolean autolink) {
        this.setFlag(1, autolink);
    }

    public final IValueMap getAttributes() {
        return this.xmlTag.getAttributes();
    }

    public final String getAttribute(String name) {
        return this.xmlTag.getAttributes().getString(name);
    }

    public final String getId() {
        return this.id;
    }

    public final int getLength() {
        return this.xmlTag.getLength();
    }

    public final String getName() {
        return this.xmlTag.getName();
    }

    public final String getNamespace() {
        return this.xmlTag.getNamespace();
    }

    public final ComponentTag getOpenTag() {
        return this.openTag;
    }

    public final int getPos() {
        return this.xmlTag.getPos();
    }

    public final XmlTag.TagType getType() {
        return this.xmlTag.getType();
    }

    public final boolean isAutolinkEnabled() {
        return this.getFlag(1);
    }

    public final boolean isClose() {
        return this.xmlTag.isClose();
    }

    public final boolean isOpen() {
        return this.xmlTag.isOpen();
    }

    public final boolean isOpen(String id) {
        return this.xmlTag.isOpen() && this.id.equals(id);
    }

    public final boolean isOpenClose() {
        return this.xmlTag.isOpenClose();
    }

    public final boolean isOpenClose(String id) {
        return this.xmlTag.isOpenClose() && this.id.equals(id);
    }

    public final void makeImmutable() {
        this.xmlTag.makeImmutable();
    }

    public ComponentTag mutable() {
        if (this.xmlTag.isMutable()) {
            return this;
        } else {
            ComponentTag tag = new ComponentTag(this.xmlTag.mutable());
            this.copyPropertiesTo(tag);
            return tag;
        }
    }

    void copyPropertiesTo(ComponentTag dest) {
        dest.id = this.id;
        dest.flags = this.flags;
        dest.autoComponentFactory = this.autoComponentFactory;
        if (this.markupClassRef != null) {
            dest.setMarkupClass((Class) this.markupClassRef.get());
        }

        if (this.behaviors != null) {
            dest.behaviors = new ArrayList(this.behaviors);
        }

        if (this.userData != null) {
            dest.userData = new HashMap(this.userData);
        }

    }

    public final void put(String key, boolean value) {
        this.xmlTag.put(key, value);
        this.setModified(true);
    }

    public final void put(String key, int value) {
        this.xmlTag.put(key, value);
        this.setModified(true);
    }

    public final void put(String key, CharSequence value) {
        this.checkIdAttribute(key);
        this.putInternal(key, value);
    }

    public final void putInternal(String key, CharSequence value) {
        this.xmlTag.put(key, value);
        this.setModified(true);
    }

    private void checkIdAttribute(String key) {
        if ("id".equalsIgnoreCase(key)) {
            log.warn("Please use component.setMarkupId(String) to change the tag's 'id' attribute.");
        }

    }

    public final void append(String key, CharSequence value, String separator) {
        String current = this.getAttribute(key);
        if (Strings.isEmpty(current)) {
            this.xmlTag.put(key, value);
        } else {
            this.xmlTag.put(key, current + separator + value);
        }

        this.setModified(true);
    }

    public final void put(String key, StringValue value) {
        this.xmlTag.put(key, value);
        this.setModified(true);
    }

    public final void putAll(Map<String, Object> map) {
        this.xmlTag.putAll(map);
        this.setModified(true);
    }

    public final void remove(String key) {
        this.xmlTag.remove(key);
        this.setModified(true);
    }

    public final boolean requiresCloseTag() {
        if (this.getNamespace() == null) {
            return HtmlHandler.requiresCloseTag(this.getName());
        } else {
            String var10000 = this.getNamespace();
            return HtmlHandler.requiresCloseTag(var10000 + ":" + this.getName());
        }
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final void setName(String name) {
        this.xmlTag.setName(name);
    }

    public final void setNamespace(String namespace) {
        this.xmlTag.setNamespace(namespace);
    }

    public final void setOpenTag(ComponentTag tag) {
        this.openTag = tag;
        this.getXmlTag().setOpenTag(tag.getXmlTag());
    }

    public final void setType(XmlTag.TagType type) {
        if (type != this.xmlTag.getType()) {
            this.xmlTag.setType(type);
            this.setModified(true);
        }

    }

    public final void writeSyntheticCloseTag(Response response) {
        response.write("</");
        if (this.getNamespace() != null) {
            response.write(this.getNamespace());
            response.write(":");
        }

        response.write(this.getName());
        response.write(">");
    }

/*    public CharSequence toCharSequence() {
        return this.xmlTag.toCharSequence();
    }*/

    public final String toString() {
        return this.toCharSequence().toString();
    }

    public final void writeOutput(Response response, boolean stripWicketAttributes, String namespace) {
        response.write("<");
        if (this.getType() == XmlTag.TagType.CLOSE) {
            response.write("/");
        }

        if (this.getNamespace() != null) {
            response.write(this.getNamespace());
            response.write(":");
        }

        response.write(this.getName());
        String namespacePrefix = null;
        if (stripWicketAttributes) {
            namespacePrefix = namespace + ":";
        }

        if (this.getAttributes().size() > 0) {
            Iterator var5 = this.getAttributes().keySet().iterator();

            label41:
            while (true) {
                String key;
                do {
                    do {
                        if (!var5.hasNext()) {
                            break label41;
                        }

                        key = (String) var5.next();
                    } while (key == null);
                } while (namespacePrefix != null && key.startsWith(namespacePrefix));

                response.write(" ");
                response.write(key);
                CharSequence value = this.getAttribute(key);
                if (value != null) {
                    response.write("=\"");
                    CharSequence value2 = Strings.escapeMarkup(value);
                    response.write(value2);
                    response.write("\"");
                }
            }
        }

        if (this.getType() == XmlTag.TagType.OPEN_CLOSE) {
            response.write("/");
        }

        response.write(">");
    }

    public final String toUserDebugString() {
        return this.xmlTag.toUserDebugString();
    }

    public final XmlTag getXmlTag() {
        return this.xmlTag;
    }

    public final void setModified(boolean modified) {
        this.setFlag(2, modified);
    }

    final boolean isModified() {
        return this.getFlag(2);
    }

    public boolean hasNoCloseTag() {
        return this.getFlag(16);
    }

    public void setHasNoCloseTag(boolean hasNoCloseTag) {
        this.setFlag(16, hasNoCloseTag);
    }

    public void setContainsWicketId(boolean containsWicketId) {
        this.setFlag(64, containsWicketId);
    }

    public boolean containsWicketId() {
        return this.getFlag(64);
    }

    public Class<? extends Component> getMarkupClass() {
        return this.markupClassRef == null ? null : (Class) this.markupClassRef.get();
    }

    public <C extends Component> void setMarkupClass(Class<C> wicketHeaderClass) {
        if (wicketHeaderClass == null) {
            this.markupClassRef = null;
        } else {
            this.markupClassRef = new WeakReference(wicketHeaderClass);
        }

    }

    public boolean equalTo(MarkupElement element) {
        if (element instanceof ComponentTag that) {
            return this.getXmlTag().equalTo(that.getXmlTag());
        } else {
            return false;
        }
    }

    public boolean isIgnore() {
        return this.getFlag(4);
    }

    public void setIgnore(boolean ignore) {
        this.setFlag(4, ignore);
    }

    public boolean isAutoComponentTag() {
        return this.getFlag(8);
    }

    public void setAutoComponentTag(boolean auto) {
        this.setFlag(8, auto);
    }

    public Object getUserData(String key) {
        return this.userData == null ? null : this.userData.get(key);
    }

    public void setUserData(String key, Object value) {
        if (this.userData == null) {
            this.userData = new HashMap();
        }

        this.userData.put(key, value);
    }

    public void onBeforeRender(Component component, MarkupStream markupStream) {
    }

    public IAutoComponentFactory getAutoComponentFactory() {
        return this.autoComponentFactory;
    }

    public void setAutoComponentFactory(IAutoComponentFactory autoComponentFactory) {
        this.autoComponentFactory = autoComponentFactory;
    }

    public interface IAutoComponentFactory {
        Component newComponent(MarkupContainer var1, ComponentTag var2);
    }
}

package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IPageRequestHandler;

import java.util.Collection;

public interface IPartialPageRequestHandler extends IPageRequestHandler {
    void add(Component var1, String var2);

    void add(Component... var1);

    void addChildren(MarkupContainer var1, Class<?> var2);

    void appendJavaScript(CharSequence var1);

    void prependJavaScript(CharSequence var1);

    void focusComponent(Component var1);

    Collection<? extends Component> getComponents();

    IHeaderResponse getHeaderResponse();
}

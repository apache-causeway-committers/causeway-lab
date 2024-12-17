package org.apache.causeway.wicketstubs.api;

import java.util.Collection;

import org.apache.causeway.wicketstubs.IPageRequestHandler;

public interface IPartialPageRequestHandler extends IPageRequestHandler {
    void add(Component var1, String var2);

    void add(Component... var1);

    void appendJavaScript(CharSequence var1);

    Collection<? extends Component> getComponents();

}

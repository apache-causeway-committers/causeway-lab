package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.PageParameters;

public interface IRequestablePage extends IRequestableComponent, IManageablePage {
    void renderPage();

    boolean isBookmarkable();

    int getRenderCount();

    boolean wasCreatedBookmarkable();

    PageParameters getPageParameters();
}
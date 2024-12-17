package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.CssReferenceHeaderItem;
import org.apache.causeway.wicketstubs.Response;

public interface IHeaderResponse {

    void render(CssReferenceHeaderItem cssReferenceHeaderItem);

    void render(Object o);

    Response getResponse();

    boolean isClosed();
}

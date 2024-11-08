package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.MarkupContainer;

public interface IVisitFilter {
    IVisitFilter ANY = null; //FIXME

    boolean visitObject(MarkupContainer current);
}

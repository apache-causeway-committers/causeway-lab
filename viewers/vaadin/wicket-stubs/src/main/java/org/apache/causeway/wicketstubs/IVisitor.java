package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.MarkupContainer;

public interface IVisitor<C, R> {
    void component(MarkupContainer current, Visit<R> visit);
}

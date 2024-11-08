package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.MarkupContainer;

//FIXME
public class Visits {
    public static <R, S extends Component> R visitChildren(MarkupContainer components, IVisitor<S,R> visitor, ClassVisitFilter classVisitFilter) {
        return null;
    }

    public static <R> R visitChildren(MarkupContainer components, IVisitor<Component,R> visitor) {
        return null;
    }
}

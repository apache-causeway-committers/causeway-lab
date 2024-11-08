package org.apache.causeway.wicketstubs;

import java.util.Set;

public class CircularDependencyException extends Throwable {
    public CircularDependencyException(Set<HeaderItem> depsDone, HeaderItem curDependency) {
    }
}

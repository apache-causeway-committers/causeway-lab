package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.MarkupContainer;

import java.util.concurrent.ExecutorService;

//FIXME
public class MarkupFactory {
    public static MarkupContainer get() {
        return null;
    }

    public boolean hasMarkupCache() {
        return false;
    }

    public ExecutorService getMarkupCache() {
        return null;
    }
}

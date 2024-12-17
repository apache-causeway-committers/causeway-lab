package org.apache.causeway.wicketstubs;

import java.util.Collection;
import java.util.Collections;

import org.apache.causeway.wicketstubs.api.Component;

public abstract class PartialPageUpdate {
    protected HtmlHeaderContainer header = null;

    public boolean isEmpty() {
        return false;
    }

    public boolean equals(Object o) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public final void add(Component component, String markupId) {
    }

    public final Collection<? extends Component> getComponents() {
        return Collections.emptyList();
    }

    public void detach(IRequestCycle requestCycle) {
    }

    public static final class ResponseBuffer extends WebResponse {
    }

}

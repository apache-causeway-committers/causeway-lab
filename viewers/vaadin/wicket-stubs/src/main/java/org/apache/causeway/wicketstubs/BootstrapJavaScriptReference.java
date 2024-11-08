package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.JavaScriptHeaderItem;

import java.util.List;

public class BootstrapJavaScriptReference extends WebjarsJavaScriptResourceReference {
    private static final long serialVersionUID = 1L;

    public static BootstrapJavaScriptReference instance() {
        return BootstrapJavaScriptReference.Holder.INSTANCE;
    }

    private BootstrapJavaScriptReference() {
        super("/bootstrap/current/js/bootstrap.js");
    }

    public List<HeaderItem> getDependencies() {
        return Dependencies.combine(super.getDependencies(),
                new HeaderItem[]{JavaScriptHeaderItem.forReference(Bootstrap.getSettings().getPopperJsResourceReference())});
    }

    private static final class Holder {
        private static final BootstrapJavaScriptReference INSTANCE = new BootstrapJavaScriptReference();

        private Holder() {
        }
    }
}

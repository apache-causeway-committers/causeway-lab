package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.Application;
import org.apache.causeway.wicketstubs.DebugSettings;
import org.apache.causeway.wicketstubs.JavaScriptLibrarySettings;

public class CoreLibrariesContributor {
    public CoreLibrariesContributor() {
    }

    public static void contribute(Application application, IHeaderResponse response) {
        JavaScriptLibrarySettings jsLibrarySettings = application.getJavaScriptLibrarySettings();
        ResourceReference wicketAjaxReference = jsLibrarySettings.getWicketAjaxReference();
        response.render(JavaScriptHeaderItem.forReference(wicketAjaxReference));
    }

    public static void contributeAjax(Application application, IHeaderResponse response) {
        JavaScriptLibrarySettings jsLibrarySettings = application.getJavaScriptLibrarySettings();
        ResourceReference wicketAjaxReference = jsLibrarySettings.getWicketAjaxReference();
        response.render(JavaScriptHeaderItem.forReference(wicketAjaxReference));
        DebugSettings debugSettings = application.getDebugSettings();
        if (debugSettings.isAjaxDebugModeEnabled()) {
            response.render(JavaScriptHeaderItem.forScript("Wicket.Log.enabled=true;", "wicket-ajax-debug-enable"));
        }

    }
}

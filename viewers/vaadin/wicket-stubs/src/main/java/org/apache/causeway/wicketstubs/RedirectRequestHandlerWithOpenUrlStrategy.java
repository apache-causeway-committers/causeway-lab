package org.apache.causeway.wicketstubs;

import lombok.NonNull;

import org.apache.causeway.applib.value.OpenUrlStrategy;

public class RedirectRequestHandlerWithOpenUrlStrategy implements IRequestHandler {
    public RedirectRequestHandlerWithOpenUrlStrategy(String string) {
    }

    public RedirectRequestHandlerWithOpenUrlStrategy(@NonNull String effectivePath, @NonNull OpenUrlStrategy openUrlStrategy) {
    }

    public String getRedirectUrl() {
        return null;
    }

    public OpenUrlStrategy getOpenUrlStrategy() {
        return null;
    }
}

package org.apache.causeway.wicketstubs.api;

public interface IAjaxCallListener {
    default CharSequence getInitHandler(Component component) {
        return null;
    }

    default CharSequence getBeforeHandler(Component component) {
        return null;
    }

    default CharSequence getPrecondition(Component component) {
        return null;
    }

    default CharSequence getBeforeSendHandler(Component component) {
        return null;
    }

    default CharSequence getAfterHandler(Component component) {
        return null;
    }

    default CharSequence getSuccessHandler(Component component) {
        return null;
    }

    default CharSequence getFailureHandler(Component component) {
        return null;
    }

    default CharSequence getCompleteHandler(Component component) {
        return null;
    }

    default CharSequence getDoneHandler(Component component) {
        return null;
    }
}

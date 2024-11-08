package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.ILoggableRequestHandler;

import java.util.Map;

public interface AjaxRequestTarget extends IPartialPageRequestHandler, ILoggableRequestHandler {
    void addListener(IListener var1);

    void registerRespondListener(ITargetRespondListener var1);

    String getLastFocusedElementId();

    Page getPage();

    @FunctionalInterface
    public interface ITargetRespondListener {
        void onTargetRespond(AjaxRequestTarget var1);
    }

    public interface IListener {
        default void onBeforeRespond(Map<String, Component> map, AjaxRequestTarget target) {
        }

        default void onAfterRespond(Map<String, Component> map, AjaxRequestTarget target) {
        }

        default void updateAjaxAttributes(AbstractDefaultAjaxBehavior behavior, AjaxRequestAttributes attributes) {
        }
    }
}
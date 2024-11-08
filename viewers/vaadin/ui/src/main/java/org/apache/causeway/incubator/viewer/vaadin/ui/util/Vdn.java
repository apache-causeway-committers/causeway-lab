package org.apache.causeway.incubator.viewer.vaadin.ui.util;

import org.apache.causeway.incubator.viewer.vaadin.ui.components.layout.bs.BSGridPanel;
import org.apache.causeway.incubator.viewer.vaadin.ui.pages.PageAbstract;
import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;
import org.apache.causeway.wicketstubs.api.MarkupContainer;
import org.apache.causeway.wicketstubs.api.WebMarkupContainer;

public class Vdn {
    public static void javaScriptAdd(IHeaderResponse response, Object focusFirstParameter, Object markupId) {
    }

    public static MarkupContainer containerAdd(PageAbstract pageAbstract, String idTheme) {
        return null;//FIXME
    }

    public static Object cssNormalize(String applicationName) {
        return null;//FIXME
    }

    public static void cssAppend(WebMarkupContainer themeDiv, Object o) {
    }

    public static void labelAdd(PageAbstract pageAbstract, String idPageTitle, String s) {
    }

    public static Object javaScriptAsOnDomReadyHeaderItem(String s) {
        return null;//FIXME
    }

    public static void cssAppend(BSGridPanel bsGridPanel, String cssClass) {
        //FIXME
    }

    public static void javaScriptAdd(AjaxRequestTarget target, EventTopic eventTopic, Object markupId) {
        //FIXME
    }

    public static void cssAppend(MarkupContainer themeDiv, Object o) {
        //FIXME
    }


    public enum EventTopic {
        FOCUS_FIRST_PROPERTY,
        FOCUS_FIRST_PARAMETER,
        OPEN_SELECT2,
        //CLOSE_SELECT2,
    }

}

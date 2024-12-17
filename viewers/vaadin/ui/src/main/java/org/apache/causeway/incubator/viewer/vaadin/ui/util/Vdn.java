package org.apache.causeway.incubator.viewer.vaadin.ui.util;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.Identifier;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.collectioncontents.ajaxtable.CollectionContentsAsAjaxTablePanel;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.layout.bs.BSGridPanel;
import org.apache.causeway.incubator.viewer.vaadin.ui.components.widgets.bootstrap.ModalDialog;
import org.apache.causeway.incubator.viewer.vaadin.ui.pages.PageAbstract;
import org.apache.causeway.wicketstubs.AjaxLink;
import org.apache.causeway.wicketstubs.Label;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.IHeaderResponse;
import org.apache.causeway.wicketstubs.api.IModel;
import org.apache.causeway.wicketstubs.api.MarkupContainer;

public class Vdn {
    public static void javaScriptAdd(IHeaderResponse response, Object focusFirstParameter, Object markupId) {
    }

    public static MarkupContainer containerAdd(PageAbstract pageAbstract, String idTheme) {
        return null;//FIXME
    }

    /**
     * If {@code cssClass} is empty, does nothing.
     */
    public static <T extends Component> T cssAppend(final Component component, final @Nullable String cssClass) {
        return null;
    }

    public static <T extends Component> T cssAppend(final T component, final @Nullable IModel<String> cssClassModel) {
        if (cssClassModel != null) {
            cssAppend(component, cssClassModel.getObject());
        }
        return component;
    }

    public static <T extends Component> T cssAppend(final T component, final Identifier identifier) {
        return null;
    }

    public static void cssAppend(AjaxLink<ManagedObject> link, String prototype) {
    }

    public static <T> void containerAdd(ModalDialog<T> tModalDialog, String contentId) {
    }

    public static void addIfElseHide(boolean searchSupported, CollectionContentsAsAjaxTablePanel collectionContentsAsAjaxTablePanel, String idTableFilterBar, Object o) {

    }

    private IModel<String> cssNormalize(Identifier identifier) {
        return null;
    }

    public static void labelAdd(MarkupContainer pageAbstract, String idPageTitle, String s) {
    }

    public static Object javaScriptAsOnDomReadyHeaderItem(String s) {
        return null;
    }

    public static void cssAppend(BSGridPanel bsGridPanel, String cssClass) {
    }

    public static void cssAppend(MarkupContainer themeDiv, Object o) {
    }

    public static Label labelAdd(MarkupContainer themeDiv, String idActionName, IModel<String> actionName) {
        return null;
    }

    public static void behaviorAddConfirm(Component uiComponent, Object confirmationConfig) {
    }

    public enum EventTopic {
        FOCUS_FIRST_PROPERTY,
        FOCUS_FIRST_PARAMETER,
        OPEN_SELECT2,
        //CLOSE_SELECT2,
    }

}

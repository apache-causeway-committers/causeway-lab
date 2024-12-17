package org.apache.causeway.incubator.viewer.vaadin.ui.util;

import java.util.Optional;

import org.apache.causeway.wicketstubs.Strings2;
import org.apache.causeway.wicketstubs.api.Page;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions.FluentException;
import org.apache.causeway.viewer.commons.model.components.UiComponentType;
import org.apache.causeway.wicketstubs.api.AjaxRequestTarget;
import org.apache.causeway.wicketstubs.api.Args;
import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.MarkupContainer;
import org.apache.causeway.wicketstubs.api.WebMarkupContainer;
import org.apache.causeway.wicketstubs.Label;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VdnComponents {

    // -- FINDING

    /**
     * Searches given container for children of given id.
     */
    public Optional<Component> findById(
            final @Nullable MarkupContainer container,
            final @Nullable String id) {
        if (container == null
                || _Strings.isNullOrEmpty(id)) {
            return Optional.empty();
        }
        return container.streamChildren()
                .filter(child -> id.equals(child.getId()))
                .findFirst();
    }

    /**
     * Searches given container for children of given id
     * and satisfying requiredType.
     */
    public <T extends Component> Optional<T> findById(
            final @Nullable MarkupContainer container,
            final @Nullable String id,
            final @NonNull Class<T> requiredType) {
        return findById(container, id)
                .filter(requiredType::isInstance)
                .map(requiredType::cast);
    }

    // -- HIDING

    /**
     * Permanently hides by replacing with a {@link Label} that has an empty
     * string for its caption.
     */
    public void permanentlyHide(final MarkupContainer container, final String... ids) {
        for (final String id : ids) {
            permanentlyHideSingle(container, id);
        }
    }

    /**
     * @see #permanentlyHide(MarkupContainer, String...)
     */
    public void permanentlyHide(final MarkupContainer container, final UiComponentType... componentIds) {
        for (final UiComponentType uiComponentType : componentIds) {
            permanentlyHideSingle(container, uiComponentType.getId());
        }
    }

    /**
     * Not overloaded because - although compiles ok on JDK6u20 (Mac), fails to
     * on JDK6u18 (Ubuntu)
     */
    private void permanentlyHideSingle(final MarkupContainer container, final String id) {
        final WebMarkupContainer invisible = new WebMarkupContainer(id);
        invisible.setVisible(false);
        container.addOrReplace(invisible);
    }

    /**
     * Sets the visibility of the child component(s) within the supplied
     * container.
     */
    public void setVisible(final MarkupContainer container, final boolean visibility, final String... ids) {
        for (final String id : ids) {
            setVisible(container, visibility, id);
        }
    }

    /**
     * @see #setVisible(MarkupContainer, boolean, String...)
     */
    public void setVisible(final MarkupContainer container, final boolean visibility, final UiComponentType... componentTypes) {
        for (final UiComponentType uiComponentType : componentTypes) {
            setVisible(container, visibility, uiComponentType.getId());
        }
    }

    private void setVisible(final MarkupContainer container, final boolean visibility, final String wicketId) {
        final Component childComponent = container.get(wicketId);
        childComponent.setVisible(visibility);
    }

    public boolean isRenderedComponent(final Component component) {
        return (component.getOutputMarkupId() && !(component instanceof Page));
    }

    public void addToAjaxRequest(final AjaxRequestTarget target, final Component component) {

        if (target == null || component == null) {
            return;
        }

        //TODO as of Wicket-8 we (for lack of a better solution) silently ignore
        // java.lang.IllegalArgumentException ...

        try {
            target.add(component);
        } catch (IllegalArgumentException cause) {
            FluentException.of(cause)
                    .suppressIfMessageContains("Cannot update component because its page is not the same");
        }

    }

    public CharSequence getMarkupId(final Component component) {
        return Strings2.getMarkupId(Args.notNull(component, "component"));
    }

}

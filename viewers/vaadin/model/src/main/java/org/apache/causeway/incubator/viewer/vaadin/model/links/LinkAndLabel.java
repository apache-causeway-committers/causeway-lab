package org.apache.causeway.incubator.viewer.vaadin.model.links;

import java.io.Serializable;
import java.util.Optional;

import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedAction;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.incubator.viewer.vaadin.model.models.ActionModel;
import org.apache.causeway.viewer.commons.model.action.HasManagedAction;
import org.apache.causeway.viewer.commons.model.mixin.HasUiComponent;
import org.apache.causeway.wicketstubs.AjaxLink;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LinkAndLabel
        implements
        Menuable,
        HasUiComponent<AjaxLink<ManagedObject>>,
        HasManagedAction,
        Serializable {

    public static LinkAndLabel of(
            final ActionModel actionModel,
            final ActionLinkUiComponentFactoryVdn uiComponentFactory) {
        _Assert.assertNotNull(actionModel.getAction(), "LinkAndLabel requires an Action");
        return new LinkAndLabel(actionModel, uiComponentFactory);
    }

    @Getter private final ActionModel actionModel;
    protected final ActionLinkUiComponentFactoryVdn uiComponentFactory;

    @Override
    public ManagedAction getManagedAction() {
        return actionModel.getManagedAction();
    }

    @Override
    public ObjectAction getAction() {
        return actionModel.getAction();
    }

    @Getter private String named;

    @Getter(lazy = true, onMethod_ = {@Override})
    private final AjaxLink<ManagedObject> uiComponent = uiComponentFactory
            .newActionLinkUiComponent(actionModel);

    @Override
    public String toString() {
        return Optional.ofNullable(named).orElse("") +
                " ~ " + getAction().getFeatureIdentifier().getFullIdentityString();
    }

    public boolean isVisible() {
        return actionModel.getVisibilityConsent().isAllowed();
    }

    public boolean isEnabled() {
        return actionModel.getUsabilityConsent().isAllowed();
    }

    @Override
    public Kind menuableKind() {
        return null;
    }

    public String isRenderOutlined() {
        return null;
    }
}

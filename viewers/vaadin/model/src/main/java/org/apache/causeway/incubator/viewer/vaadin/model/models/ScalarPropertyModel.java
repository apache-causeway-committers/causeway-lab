/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.causeway.incubator.viewer.vaadin.model.models;

import lombok.Getter;
import lombok.val;

import org.apache.causeway.applib.locale.UserLocale;
import org.apache.causeway.applib.services.ascii.AsciiIdentifierService;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.i18n.TranslationService;
import org.apache.causeway.applib.services.iactnlayer.InteractionService;
import org.apache.causeway.applib.services.inject.ServiceInjector;
import org.apache.causeway.applib.services.menu.MenuBarsService;
import org.apache.causeway.applib.services.message.MessageService;
import org.apache.causeway.applib.services.placeholder.PlaceholderRenderService;
import org.apache.causeway.applib.services.registry.ServiceRegistry;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.services.title.TitleService;
import org.apache.causeway.applib.services.wrapper.WrapperFactory;
import org.apache.causeway.applib.services.xactn.TransactionService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.config.CausewayConfiguration;
import org.apache.causeway.core.config.environment.CausewaySystemEnvironment;
import org.apache.causeway.core.config.viewer.web.WebAppContextPath;
import org.apache.causeway.core.metamodel.commons.ViewOrEditMode;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.core.metamodel.execution.MemberExecutorService;
import org.apache.causeway.core.metamodel.facets.object.icon.ObjectIconService;
import org.apache.causeway.core.metamodel.interactions.managed.InteractionVeto;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedProperty;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedValue;
import org.apache.causeway.core.metamodel.interactions.managed.PropertyNegotiationModel;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.objectmanager.ObjectManager;
import org.apache.causeway.core.metamodel.services.message.MessageBroker;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.core.metamodel.spec.feature.OneToOneAssociation;
import org.apache.causeway.core.metamodel.specloader.SpecificationLoader;
import org.apache.causeway.core.security.authentication.manager.AuthenticationManager;
import org.apache.causeway.core.security.authorization.manager.AuthorizationManager;
import org.apache.causeway.incubator.viewer.vaadin.model.models.interction.prop.UiPropertyVdn;
import org.apache.causeway.viewer.commons.model.hints.HasRenderingHints;
import org.apache.causeway.viewer.commons.model.hints.RenderingHint;
import org.apache.causeway.viewer.commons.model.scalar.HasUiProperty;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
//import org.apache.causeway.viewer.wicket.model.models.interaction.prop.UiPropertyVdn;

public class ScalarPropertyModel
    extends ScalarModel
    implements HasUiProperty {

    private static final long serialVersionUID = 1L;

    @Getter(onMethod_={@Override})
    private UiPropertyVdn uiProperty;

    public static ScalarPropertyModel wrap(
            final UiPropertyVdn uiProperty,
            final ViewOrEditMode viewOrEdit,
            final RenderingHint renderingHint) {
        return new ScalarPropertyModel(uiProperty, viewOrEdit, renderingHint);
    }

    /**
     * Creates a model representing a property of a parent object, with the
     * {@link #getObject() value of this model} to be current value of the
     * property.
     */
    private ScalarPropertyModel(
            final UiPropertyVdn uiProperty,
            final ViewOrEditMode viewOrEdit,
            final RenderingHint renderingHint) {
        super(UiObjectVdn.ofAdapter(uiProperty.getOwner()),
                viewOrEdit, renderingHint);
        this.uiProperty = uiProperty;
    }

    /** @return new instance bound to the same delegate */
    public ScalarPropertyModel copyHaving(
            final ViewOrEditMode viewOrEdit,
            final RenderingHint renderingHint) {
        return wrap(uiProperty, viewOrEdit, renderingHint);
    }

    @Override
    public String validate(final ManagedObject proposedNewValue) {
        return getManagedProperty()
                .checkValidity(proposedNewValue)
                .flatMap(InteractionVeto::getReasonAsString)
                .orElse(null);
    }

    @Override
    public String toStringOf() {
        val featureId = uiProperty.getMetaModel().getFeatureIdentifier();
        return getFriendlyName() + ": " +
                featureId.getLogicalTypeName() + "#" + featureId.getMemberLogicalName();

    }

    public String getReasonInvalidIfAny() {
        return getPendingPropertyModel().getValidationMessage().getValue();
    }

    /**
     * Apply changes to the underlying adapter (possibly returning a new adapter).
     *
     * @return adapter, which may be different from the original
     */
    public ManagedObject applyValueThenReturnOwner() {
        getPendingPropertyModel().submit();
        return getOwner();
    }

    @Override
    public ManagedValue proposedValue() {
        return getPendingPropertyModel();
    }

    @Override
    protected Can<ObjectAction> calcAssociatedActions() {
        return getManagedProperty().getAssociatedActions();
    }

    @Override
    public boolean isWithinInlinePrompt() {
        return super.isWithinInlinePrompt();
    }

    @Override
    public Optional<ScalarParameterModel> getAssociatedParameter() {
        return super.getAssociatedParameter();
    }

    @Override
    public boolean isViewingMode() {
        return super.isViewingMode();
    }

    @Override
    public boolean isEditingMode() {
        return super.isEditingMode();
    }

    @Override
    public HasRenderingHints toEditingMode() {
        return super.toEditingMode();
    }

    @Override
    public HasRenderingHints toViewingMode() {
        return super.toViewingMode();
    }

    @Override
    public ManagedObject getParentObject() {
        return super.getParentObject();
    }

    @Override
    public ManagedObject getOwner() {
        return HasUiProperty.super.getOwner();
    }

    @Override
    public OneToOneAssociation getMetaModel() {
        return HasUiProperty.super.getMetaModel();
    }

    @Override
    public PropertyNegotiationModel getPendingPropertyModel() {
        return HasUiProperty.super.getPendingPropertyModel();
    }

    @Override
    public ManagedProperty getManagedProperty() {
        return HasUiProperty.super.getManagedProperty();
    }

    @Override
    public String getIdentifier() {
        return HasUiProperty.super.getIdentifier();
    }

    @Override
    public String getCssClass() {
        return HasUiProperty.super.getCssClass();
    }

    @Override
    public int getAutoCompleteMinLength() {
        return HasUiProperty.super.getAutoCompleteMinLength();
    }

    @Override
    public boolean isRequired() {
        return super.isRequired();
    }

    @Override
    public ObjectSpecification getElementType() {
        return super.getElementType();
    }

    @Override
    public boolean hasChoices() {
        return HasUiProperty.super.hasChoices();
    }

    @Override
    public boolean hasAutoComplete() {
        return HasUiProperty.super.hasAutoComplete();
    }

    @Override
    public boolean hasObjectAutoComplete() {
        return super.hasObjectAutoComplete();
    }

    @Override
    public ManagedObject getDefault() {
        return HasUiProperty.super.getDefault();
    }

    @Override
    public Can<ManagedObject> getChoices() {
        return HasUiProperty.super.getChoices();
    }

    @Override
    public Can<ManagedObject> getAutoComplete(String searchArg) {
        return HasUiProperty.super.getAutoComplete(searchArg);
    }

    @Override
    public ChoiceProviderSort getChoiceProviderSort() {
        return super.getChoiceProviderSort();
    }

    @Override
    public boolean whetherHidden() {
        return HasUiProperty.super.whetherHidden();
    }

    @Override
    public Optional<InteractionVeto> disabledReason() {
        return HasUiProperty.super.disabledReason();
    }

    @Override
    public String getFriendlyName() {
        return super.getFriendlyName();
    }

    @Override
    public boolean isSingular() {
        return super.isSingular();
    }

    @Override
    public boolean isPlural() {
        return super.isPlural();
    }

    @Override
    public boolean isProperty() {
        return super.isProperty();
    }

    @Override
    public boolean isParameter() {
        return super.isParameter();
    }

    @Override
    public Optional<String> getDescribedAs() {
        return super.getDescribedAs();
    }

    @Override
    public String getFileAccept() {
        return super.getFileAccept();
    }

    @Override
    public MetaModelContext getMetaModelContext() {
        return super.getMetaModelContext();
    }

    @Override
    public CausewaySystemEnvironment getSystemEnvironment() {
        return super.getSystemEnvironment();
    }

    @Override
    public CausewayConfiguration getConfiguration() {
        return super.getConfiguration();
    }

    @Override
    public ServiceInjector getServiceInjector() {
        return super.getServiceInjector();
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return super.getServiceRegistry();
    }

    @Override
    public FactoryService getFactoryService() {
        return super.getFactoryService();
    }

    @Override
    public MemberExecutorService getMemberExecutor() {
        return super.getMemberExecutor();
    }

    @Override
    public SpecificationLoader getSpecificationLoader() {
        return super.getSpecificationLoader();
    }

    @Override
    public TranslationService getTranslationService() {
        return super.getTranslationService();
    }

    @Override
    public AuthorizationManager getAuthorizationManager() {
        return super.getAuthorizationManager();
    }

    @Override
    public AuthenticationManager getAuthenticationManager() {
        return super.getAuthenticationManager();
    }

    @Override
    public TitleService getTitleService() {
        return super.getTitleService();
    }

    @Override
    public RepositoryService getRepositoryService() {
        return super.getRepositoryService();
    }

    @Override
    public ManagedObject getHomePageAdapter() {
        return super.getHomePageAdapter();
    }

    @Override
    public TransactionService getTransactionService() {
        return super.getTransactionService();
    }

    @Override
    public ObjectIconService getObjectIconService() {
        return super.getObjectIconService();
    }

    @Override
    public MessageService getMessageService() {
        return super.getMessageService();
    }

    @Override
    public ObjectManager getObjectManager() {
        return super.getObjectManager();
    }

    @Override
    public WrapperFactory getWrapperFactory() {
        return super.getWrapperFactory();
    }

    @Override
    public PlaceholderRenderService getPlaceholderRenderService() {
        return super.getPlaceholderRenderService();
    }

    @Override
    public WebAppContextPath getWebAppContextPath() {
        return super.getWebAppContextPath();
    }

    @Override
    public MenuBarsService getMenuBarsService() {
        return super.getMenuBarsService();
    }

    @Override
    public InteractionService getInteractionService() {
        return super.getInteractionService();
    }

    @Override
    public Optional<UserLocale> currentUserLocale() {
        return super.currentUserLocale();
    }

    @Override
    public Optional<ObjectSpecification> specForType(Class<?> type) {
        return super.specForType(type);
    }

    @Override
    public ObjectSpecification specForTypeElseFail(Class<?> type) {
        return super.specForTypeElseFail(type);
    }

    @Override
    public <T> Optional<T> lookupService(Class<T> serviceClass) {
        return super.lookupService(serviceClass);
    }

    @Override
    public <T> T lookupServiceElseFail(Class<T> serviceClass) {
        return super.lookupServiceElseFail(serviceClass);
    }

    @Override
    public <T> T lookupServiceElseFallback(Class<T> serviceClass, Supplier<T> fallback) {
        return super.lookupServiceElseFallback(serviceClass, fallback);
    }

    @Override
    public <T> T loadServiceIfAbsent(Class<T> type, T instanceIfAny) {
        return super.loadServiceIfAbsent(type, instanceIfAny);
    }

    @Override
    public <T> T injectServicesInto(T pojo) {
        return super.injectServicesInto(pojo);
    }

    @Override
    public Optional<MessageBroker> getMessageBroker() {
        return super.getMessageBroker();
    }

    @Override
    public AsciiIdentifierService getAsciiIdentifierService() {
        return super.getAsciiIdentifierService();
    }

    @Override
    public ManagedObject lookupServiceAdapterById(String serviceId) {
        return super.lookupServiceAdapterById(serviceId);
    }

    @Override
    public Stream<ManagedObject> streamServiceAdapters() {
        return super.streamServiceAdapters();
    }
}

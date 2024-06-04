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
package demoapp.webapp.vaadin;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.i18n.TranslationService;
import org.apache.causeway.applib.services.menu.MenuBarsLoaderService;
import org.apache.causeway.applib.services.menu.MenuBarsMarshallerService;
import org.apache.causeway.applib.services.menu.MenuBarsService;
import org.apache.causeway.commons.internal.os._OsUtil;
import org.apache.causeway.commons.internal.proxy._ProxyFactoryService;
import org.apache.causeway.core.codegen.bytebuddy.services.ProxyFactoryServiceByteBuddy;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.config.util.SpringProfileUtil;
import org.apache.causeway.core.metamodel.execution.MemberExecutorService;
import org.apache.causeway.core.metamodel.services.grid.GridLoaderServiceDefault;
import org.apache.causeway.core.metamodel.services.ixn.InteractionDtoFactory;
import org.apache.causeway.core.runtimeservices.bookmarks.BookmarkServiceDefault;
import org.apache.causeway.core.runtimeservices.command.CommandDtoFactoryDefault;
import org.apache.causeway.core.runtimeservices.command.SchemaValueMarshallerDefault;
import org.apache.causeway.core.runtimeservices.eventbus.EventBusServiceSpring;
import org.apache.causeway.core.runtimeservices.executor.MemberExecutorServiceDefault;
import org.apache.causeway.core.runtimeservices.factory.FactoryServiceDefault;
import org.apache.causeway.core.runtimeservices.i18n.po.TranslationServicePo;
import org.apache.causeway.core.runtimeservices.interaction.InteractionDtoFactoryDefault;
import org.apache.causeway.core.runtimeservices.jaxb.JaxbServiceDefault;
import org.apache.causeway.core.runtimeservices.locale.LanguageProviderDefault;
import org.apache.causeway.core.runtimeservices.menubars.MenuBarsLoaderServiceDefault;
import org.apache.causeway.core.runtimeservices.menubars.bootstrap.MenuBarsMarshallerServiceBootstrap;
import org.apache.causeway.core.runtimeservices.menubars.bootstrap.MenuBarsServiceBootstrap;
import org.apache.causeway.core.runtimeservices.message.MessageServiceDefault;
import org.apache.causeway.core.runtimeservices.publish.EntityChangesPublisherDefault;
import org.apache.causeway.core.runtimeservices.publish.EntityPropertyChangePublisherDefault;
import org.apache.causeway.core.runtimeservices.serializing.SerializingAdapterDefault;
import org.apache.causeway.core.runtimeservices.session.InteractionIdGeneratorDefault;
import org.apache.causeway.core.runtimeservices.session.InteractionServiceDefault;
import org.apache.causeway.core.runtimeservices.sitemap.SitemapServiceDefault;
import org.apache.causeway.core.runtimeservices.spring.SpringBeansService;
import org.apache.causeway.core.runtimeservices.wrapper.WrapperFactoryDefault;
import org.apache.causeway.core.security.authentication.manager.AuthenticationManager;
import org.apache.causeway.core.transaction.changetracking.EntityPropertyChangePublisher;
import org.apache.causeway.extensions.commandlog.applib.CausewayModuleExtCommandLogApplib;
import org.apache.causeway.extensions.secman.encryption.spring.CausewayModuleExtSecmanEncryptionSpring;
import org.apache.causeway.extensions.secman.integration.CausewayModuleExtSecmanIntegration;
import org.apache.causeway.extensions.secman.integration.authenticator.AuthenticatorSecman;
import org.apache.causeway.extensions.secman.integration.authorizor.AuthorizorSecman;
import org.apache.causeway.extensions.secman.jpa.CausewayModuleExtSecmanPersistenceJpa;
import org.apache.causeway.extensions.secman.jpa.user.dom.ApplicationUserRepository;
import org.apache.causeway.extensions.secman.jpa.util.RegexReplacer;
import org.apache.causeway.incubator.viewer.vaadin.ui.auth.VaadinAuthenticationHandler;
import org.apache.causeway.incubator.viewer.vaadin.viewer.CausewayModuleIncViewerVaadinViewer;
import org.apache.causeway.persistence.commons.integration.repository.RepositoryServiceDefault;
import org.apache.causeway.persistence.jpa.eclipselink.CausewayModulePersistenceJpaEclipselink;
import org.apache.causeway.security.shiro.CausewayModuleSecurityShiro;
import org.apache.causeway.security.shiro.authorization.AuthorizorShiro;
import org.apache.causeway.security.spring.authentication.AuthenticatorSpring;
import org.apache.causeway.valuetypes.asciidoc.metamodel.CausewayModuleValAsciidocMetaModel;
import org.apache.causeway.valuetypes.asciidoc.persistence.jpa.CausewayModuleValAsciidocPersistenceJpa;
import org.apache.causeway.valuetypes.markdown.metamodel.CausewayModuleValMarkdownMetaModel;
import org.apache.causeway.valuetypes.markdown.persistence.jpa.CausewayModuleValMarkdownPersistenceJpa;
import org.apache.causeway.valuetypes.vega.metamodel.CausewayModuleValVegaMetaModel;
import org.apache.causeway.valuetypes.vega.persistence.jpa.CausewayModuleValVegaPersistenceJpa;
import org.apache.causeway.viewer.restfulobjects.viewer.webmodule.auth.AuthenticationStrategyUsingSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Bootstrap the application.
 */
@SpringBootApplication
@Import({
        CausewayModuleValAsciidocMetaModel.class,
        CausewayModuleValMarkdownMetaModel.class,
        CausewayModuleValVegaMetaModel.class,
        //TODO CausewayModuleExtFullCalendarVaadin.class,

        // INCUBATING
        CausewayModuleIncViewerVaadinViewer.class, // vaadin viewer

        // Persistence (JPA)
        CausewayModuleValAsciidocPersistenceJpa.class,
        CausewayModuleValMarkdownPersistenceJpa.class,
        CausewayModuleValVegaPersistenceJpa.class,

        // WICKET INTEGRATION ... to allow side by side comparison
        //CausewayModuleViewerWicketViewer.class, // wicket viewer
        //CausewayModuleExtSseWicket.class, // server sent events
        //CausewayModuleValAsciidocUiWkt.class, // ascii-doc rendering support (for Wicket)

        InteractionServiceDefault.class,
        InteractionIdGeneratorDefault.class,
        EventBusServiceSpring.class,
        FactoryServiceDefault.class,
        MessageServiceDefault.class,
        TranslationServicePo.class,
        LanguageProviderDefault.class,
        JaxbServiceDefault.class,
        MenuBarsServiceBootstrap.class,
        MenuBarsLoaderServiceDefault.class,
        MenuBarsMarshallerServiceBootstrap.class,
        SitemapServiceDefault.class,
        RepositoryServiceDefault.class,
        WrapperFactoryDefault.class,
        ProxyFactoryServiceByteBuddy.class,

        AuthorizorShiro.class,
        CausewayModuleSecurityShiro.class,
        CausewayModulePersistenceJpaEclipselink.class,

        SerializingAdapterDefault.class,
        BookmarkServiceDefault.class,
        SpringBeansService.class,
        MemberExecutorServiceDefault.class,
        InteractionDtoFactoryDefault.class,
        CommandDtoFactoryDefault.class,
        SchemaValueMarshallerDefault.class,
        EntityPropertyChangePublisherDefault.class,
        EntityChangesPublisherDefault.class,

})
public class DemoAppVaadin extends SpringBootServletInitializer {

    /**
     *
     * @param args
     * @implNote this is to support the <em>Spring Boot Maven Plugin</em>, which auto-detects an
     * entry point by searching for classes having a {@code main(...)}
     */
    public static void main(final String[] args) {

        // activates when sys-env THERE_CAN_BE_ONLY_ONE=true
        _OsUtil.thereCanBeOnlyOne();

        CausewayPresets.prototyping();
        //CausewayPresets.logging(WebRequestCycleForCauseway.class, "debug");

        SpringProfileUtil.removeActiveProfile("demo-jpa"); // just in case
        // SpringProfileUtil.addActiveProfile("demo-jdo");

        CausewayModuleExtCommandLogApplib.honorSystemEnvironment();

        SpringApplication.run(new Class[] { DemoAppVaadin.class }, args);
    }

}

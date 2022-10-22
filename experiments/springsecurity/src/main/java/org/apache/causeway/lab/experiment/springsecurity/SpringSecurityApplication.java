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
package org.apache.causeway.lab.experiment.springsecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;

import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.security.spring.CausewayModuleSecuritySpring;

import org.apache.causeway.extensions.secman.encryption.spring.CausewayModuleExtSecmanEncryptionSpring;
import org.apache.causeway.extensions.secman.integration.CausewayModuleExtSecmanIntegration;
import org.apache.causeway.extensions.secman.integration.authorizor.AuthorizorSecman;
import org.apache.causeway.extensions.secman.jpa.CausewayModuleExtSecmanPersistenceJpa;
import org.apache.causeway.extensions.spring.security.oauth2.CausewayModuleExtSpringSecurityOAuth2;
import org.apache.causeway.extensions.spring.security.oauth2.authconverters.AuthenticationConverterOfOAuth2UserPrincipal;
import org.apache.causeway.persistence.jpa.eclipselink.CausewayModulePersistenceJpaEclipselink;
import org.apache.causeway.testing.fixtures.applib.CausewayModuleTestingFixturesApplib;
import org.apache.causeway.testing.h2console.ui.CausewayModuleTestingH2ConsoleUi;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;
import org.apache.causeway.viewer.wicket.viewer.integration.WebRequestCycleForCauseway;

@SpringBootApplication//(exclude = { CsrfConfigurer.class })
@Import({
    CausewayModuleCoreRuntimeServices.class, // Apache Causeway Runtime
    CausewayModulePersistenceJpaEclipselink.class, // EclipseLink as JPA provider for Spring Data
    CausewayModuleViewerWicketViewer.class, // UI (Wicket Viewer)
    CausewayModuleTestingH2ConsoleUi.class, // enables the H2 console menu item
    CausewayModuleSecuritySpring.class, // Authorization using Spring Security
    CausewayModuleExtSpringSecurityOAuth2.class, // Spring Security OAuth2 support

    // Security Manager Extension (SecMan)
    AuthorizorSecman.class,
    CausewayModuleExtSecmanIntegration.class,
    //CausewayModuleExtSecmanRealmShiro.class,
    CausewayModuleExtSecmanPersistenceJpa.class,
    CausewayModuleExtSecmanEncryptionSpring.class,

    // Default Admin/User/Role Seeding Support for SecMan
    CausewayModuleTestingFixturesApplib.class,
})
@EntityScan(basePackageClasses = {
        Employee.class,
})
public class SpringSecurityApplication {

    public static void main(final String[] args) {

        CausewayPresets.logging(FilterChainProxy.class, "debug");

        CausewayPresets.logging(org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.context.SecurityContextHolderFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.header.HeaderWriterFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.csrf.CsrfFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.authentication.logout.LogoutFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.savedrequest.RequestCacheAwareFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.oauth2.client.web.OAuth2AuthorizationCodeGrantFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.session.SessionManagementFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.access.ExceptionTranslationFilter.class, "debug");
        CausewayPresets.logging(org.springframework.security.web.access.intercept.FilterSecurityInterceptor.class, "debug");

        CausewayPresets.logging(WebRequestCycleForCauseway.class, "debug");

        SpringApplication.run(SpringSecurityApplication.class);
    }

    @Bean
    public CommandLineRunner loadData(final EmployeeRepository repository) {
        return (args) -> {
            if(repository.count()<4L) {
                repository.save(new Employee("Bill", "Gates"));
                repository.save(new Employee("Mark", "Zuckerberg"));
                repository.save(new Employee("Sundar", "Pichai"));
                repository.save(new Employee("Jeff", "Bezos"));
            }
        };
    }

    /**
     * For demo we always replace the user id to 'sven',
     * as the 'sven' user is seeded with permissions to actually use this demo app.
     */
    @Component
    @javax.annotation.Priority(PriorityPrecedence.EARLY)
    public static class DemoAuthenticationConverter
    extends AuthenticationConverterOfOAuth2UserPrincipal {

        @Override
        protected String usernameFrom(final OAuth2User oAuth2User) {
            return "sven";
        }

    }


}

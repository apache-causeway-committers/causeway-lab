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
package org.apache.isis.lab.experiment.springsecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.FilterChainProxy;

import org.apache.isis.core.config.presets.IsisPresets;
import org.apache.isis.core.runtimeservices.IsisModuleCoreRuntimeServices;
import org.apache.isis.extensions.secman.encryption.jbcrypt.IsisModuleExtSecmanEncryptionJbcrypt;
import org.apache.isis.extensions.secman.integration.IsisModuleExtSecmanIntegration;
import org.apache.isis.extensions.secman.integration.authorizor.AuthorizorSecman;
import org.apache.isis.extensions.secman.jpa.IsisModuleExtSecmanPersistenceJpa;
import org.apache.isis.persistence.jpa.eclipselink.IsisModulePersistenceJpaEclipselink;
import org.apache.isis.security.spring.IsisModuleSecuritySpring;
import org.apache.isis.testing.fixtures.applib.IsisModuleTestingFixturesApplib;
import org.apache.isis.testing.h2console.ui.IsisModuleTestingH2ConsoleUi;
import org.apache.isis.viewer.wicket.viewer.IsisModuleViewerWicketViewer;
import org.apache.isis.viewer.wicket.viewer.integration.WebRequestCycleForIsis;

@SpringBootApplication//(exclude = { CsrfConfigurer.class })
@Import({
    IsisModuleCoreRuntimeServices.class, // Apache Isis Runtime
    IsisModulePersistenceJpaEclipselink.class, // EclipseLink as JPA provider for Spring Data
    IsisModuleViewerWicketViewer.class, // UI (Wicket Viewer)
    IsisModuleTestingH2ConsoleUi.class, // enables the H2 console menu item
    IsisModuleSecuritySpring.class, // Authorization using Spring Security

    // Security Manager Extension (SecMan)
    AuthorizorSecman.class,
    IsisModuleExtSecmanIntegration.class,
    //IsisModuleExtSecmanRealmShiro.class,
    IsisModuleExtSecmanPersistenceJpa.class,
    IsisModuleExtSecmanEncryptionJbcrypt.class,

    // Default Admin/User/Role Seeding Support for SecMan
    IsisModuleTestingFixturesApplib.class,
})
@EntityScan(basePackageClasses = {
        Employee.class,
})
public class SpringSecurityApplication {

    public static void main(final String[] args) {

        IsisPresets.logging(FilterChainProxy.class, "debug");

        IsisPresets.logging(org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.context.SecurityContextPersistenceFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.header.HeaderWriterFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.csrf.CsrfFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.authentication.logout.LogoutFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.savedrequest.RequestCacheAwareFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.oauth2.client.web.OAuth2AuthorizationCodeGrantFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.session.SessionManagementFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.access.ExceptionTranslationFilter.class, "debug");
        IsisPresets.logging(org.springframework.security.web.access.intercept.FilterSecurityInterceptor.class, "debug");

        IsisPresets.logging(WebRequestCycleForIsis.class, "debug");

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

}

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
package org.apache.isis.lab.tutorial.secman;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.apache.isis.core.runtimeservices.IsisModuleCoreRuntimeServices;
import org.apache.isis.extensions.modelannotation.metamodel.IsisModuleExtModelAnnotation;
import org.apache.isis.extensions.secman.api.SecmanConfiguration;
import org.apache.isis.extensions.secman.api.permission.PermissionsEvaluationService;
import org.apache.isis.extensions.secman.api.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.apache.isis.extensions.secman.encryption.jbcrypt.IsisModuleExtSecmanEncryptionJbcrypt;
import org.apache.isis.extensions.secman.jpa.IsisModuleExtSecmanPersistenceJpa;
import org.apache.isis.extensions.secman.model.IsisModuleExtSecmanModel;
import org.apache.isis.extensions.secman.shiro.IsisModuleExtSecmanRealmShiro;
import org.apache.isis.persistence.jpa.eclipselink.IsisModuleJpaEclipselink;
import org.apache.isis.security.shiro.IsisModuleSecurityShiro;
import org.apache.isis.testing.fixtures.applib.IsisModuleTestingFixturesApplib;
import org.apache.isis.testing.h2console.ui.IsisModuleTestingH2ConsoleUi;
import org.apache.isis.viewer.wicket.viewer.IsisModuleViewerWicketViewer;

@SpringBootApplication
@Import({
    IsisModuleCoreRuntimeServices.class, // Apache Isis Runtime
    IsisModuleJpaEclipselink.class, // EclipseLink as JPA provider for Spring Data 
    IsisModuleExtModelAnnotation.class, // @Model support
    IsisModuleViewerWicketViewer.class, // UI (Wicket Viewer)
    IsisModuleTestingH2ConsoleUi.class, // enables the H2 console menu item
    IsisModuleSecurityShiro.class, // Security using Shiro

    // Security Manager Extension (SecMan)
    IsisModuleExtSecmanModel.class,
    IsisModuleExtSecmanRealmShiro.class,
    IsisModuleExtSecmanPersistenceJpa.class,
    IsisModuleExtSecmanEncryptionJbcrypt.class,
    
    // Default Admin/User/Role Seeding Support for SecMan
    IsisModuleTestingFixturesApplib.class, 
})
@EntityScan(basePackageClasses = {
        Employee.class,
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(EmployeeRepository repository) {
        return (args) -> {
            repository.save(new Employee("Bill", "Gates"));
            repository.save(new Employee("Mark", "Zuckerberg"));
            repository.save(new Employee("Sundar", "Pichai"));
            repository.save(new Employee("Jeff", "Bezos"));
        };
    }
    
    @Bean
    public SecmanConfiguration securityModuleConfigBean() {
        return SecmanConfiguration.builder()
                .adminUserName("sven")
                .adminAdditionalNamespacePermission("isisLab")
                .build();
    }

    @Bean
    public PermissionsEvaluationService permissionsEvaluationService() {
        return new PermissionsEvaluationServiceAllowBeatsVeto();
    }
    
}

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
package org.apache.isis.lab.tutorial.springdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.apache.isis.core.runtimeservices.IsisModuleCoreRuntimeServices;
import org.apache.isis.extensions.modelannotation.metamodel.IsisModuleExtModelAnnotation;
import org.apache.isis.persistence.jpa.eclipselink.IsisModuleJpaEclipselink;
import org.apache.isis.security.bypass.IsisModuleSecurityBypass;
import org.apache.isis.viewer.wicket.viewer.IsisModuleViewerWicketViewer;

@SpringBootApplication
@Import({
    IsisModuleCoreRuntimeServices.class, // Apache Isis Runtime
    IsisModuleJpaEclipselink.class, // EclipseLink as JPA provider for Spring Data 
    IsisModuleExtModelAnnotation.class, // @Model support
    IsisModuleViewerWicketViewer.class, // UI (Wicket Viewer)
    IsisModuleSecurityBypass.class // Security (Bypass, grants all access)
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
}

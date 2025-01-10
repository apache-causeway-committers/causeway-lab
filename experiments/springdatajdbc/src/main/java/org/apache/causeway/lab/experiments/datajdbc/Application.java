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
package org.apache.causeway.lab.experiments.datajdbc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.persistence.jdbc.CausewayModulePersistenceJdbc;
import org.apache.causeway.security.bypass.CausewayModuleSecurityBypass;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

@SpringBootApplication
@Import({
    CausewayModuleCoreRuntimeServices.class, // Apache Causeway Runtime
    CausewayModulePersistenceJdbc.class, // Spring Data JDBC integration
    CausewayModuleViewerWicketViewer.class, // UI (Wicket Viewer)
    CausewayModuleSecurityBypass.class // Security (Bypass, grants all access)
})
@EntityScan(basePackageClasses = {
        Employee.class,
})
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(final EmployeeRepository repository) {
        return (args) -> {
            repository.save(new Employee("Bill", "Gates"));
            repository.save(new Employee("Mark", "Zuckerberg"));
            repository.save(new Employee("Sundar", "Pichai"));
            repository.save(new Employee("Jeff", "Bezos"));
        };
    }
}

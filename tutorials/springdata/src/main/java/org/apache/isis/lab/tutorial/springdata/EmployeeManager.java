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

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;

@DomainObject(nature=Nature.VIEW_MODEL, logicalTypeName = "isisLab.EmployeeManager")
//@RequiredArgsConstructor(onConstructor_ = {@Inject}) //XXX not supported for view models yet
public class EmployeeManager {

    @Inject private EmployeeRepository employeeRepo;

    public String title() {
        return "Employee Manager";
    }
    
    @Collection
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

}
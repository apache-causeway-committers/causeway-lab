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
package org.apache.causeway.incubator.viewer.vaadin.ui.actionresponse;

import org.springframework.lang.Nullable;

import org.apache.causeway.wicketstubs.IRequestablePage;
import org.apache.causeway.wicketstubs.api.EntityPage;
import org.apache.causeway.wicketstubs.api.PageParameters;
import org.apache.causeway.wicketstubs.api.RequestCycle;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageRedirectRequest<T extends IRequestablePage> {

    private final @NonNull Class<T> pageClass = null;
    private final @Nullable PageParameters pageParameters = null;
    private final @Nullable IRequestablePage pageInstance = null;

    public PageRedirectRequest(Class<EntityPage> pageClass, @NonNull PageParameters pageParameters, Object pageInstance) {
    }

    public <T extends IRequestablePage> PageRedirectRequest(@NonNull Class<T> pageClass, Object pageParameters, Object pageInstance) {
    }

    public static <T extends IRequestablePage> PageRedirectRequest<T> forPageClass(
            final Class<EntityPage> pageClass,
            final @NonNull PageParameters pageParameters) {
        return new PageRedirectRequest<>(pageClass, pageParameters, null);
    }

    public static <T extends IRequestablePage> PageRedirectRequest<T> forPageClass(
            final @NonNull Class<T> pageClass) {
        return new PageRedirectRequest<>(pageClass, null, null);
    }

    public static <T extends IRequestablePage> PageRedirectRequest<T> forPage(
            final @NonNull Class<T> pageClass,
            final @NonNull T pageInstance) {
        return new PageRedirectRequest<>(pageClass, null, pageInstance);
    }

    public void applyTo(
            final @Nullable RequestCycle requestCycle) {
        if (requestCycle == null) {
            return;
        }
        if (pageParameters != null) {
            requestCycle.setResponsePage(pageClass);
            return;
        }
        if (pageInstance != null) {
            requestCycle.setResponsePage(pageInstance);
            return;
        }
        requestCycle.setResponsePage(pageClass);
    }

    @Override
    public String toString() {
        return String.format("PageRedirectRequest[pageClass=%s,pageParameters=%s]",
                pageClass.getName(), pageParameters);
    }

}

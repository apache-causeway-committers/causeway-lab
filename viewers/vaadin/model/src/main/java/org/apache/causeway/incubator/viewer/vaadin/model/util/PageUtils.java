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
package org.apache.causeway.incubator.viewer.vaadin.model.util;

import lombok.experimental.UtilityClass;

import org.apache.causeway.wicketstubs.IRequestablePage;
import org.apache.causeway.wicketstubs.api.ListenerRequestHandler;
import org.apache.causeway.wicketstubs.api.RequestCycle;

@UtilityClass
public class PageUtils {

    // -- PAGE RELOAD

    public void pageReload() {
        var requestCycle = RequestCycle.get();
        if(requestCycle==null) return;
        var handler = requestCycle.getActiveRequestHandler();
        if(handler instanceof ListenerRequestHandler) {
            var currentPage = ((ListenerRequestHandler)handler).getPage();
            requestCycle.setResponsePage((IRequestablePage) currentPage);
        }
    }

    // -- PAGE REDIRECT

    public void pageRedirect(final IRequestablePage page) {
        final RequestCycle requestCycle = RequestCycle.get();
        if(requestCycle==null) return;
        requestCycle.setResponsePage(page);
    }

}

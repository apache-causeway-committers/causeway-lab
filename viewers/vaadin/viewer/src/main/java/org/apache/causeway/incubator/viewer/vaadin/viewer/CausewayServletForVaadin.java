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
package org.apache.causeway.incubator.viewer.vaadin.viewer;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.vaadin.flow.spring.SpringServlet;

import org.springframework.context.ApplicationContext;

import org.apache.causeway.applib.services.iactn.Interaction;
import org.apache.causeway.applib.services.iactnlayer.InteractionService;
import org.apache.causeway.incubator.viewer.vaadin.ui.auth.AuthSessionStoreUtil;

import lombok.NonNull;
import lombok.val;
import lombok.extern.log4j.Log4j2;

/**
 * An extension of {@link SpringServlet} to support {@link Interaction} life-cycle management.
 * @since Mar 14, 2020
 *
 */
@Log4j2
public class CausewayServletForVaadin extends SpringServlet {

    private static final long serialVersionUID = 1L;

    private final InteractionService interactionService;

    public CausewayServletForVaadin(
            final @NonNull InteractionService interactionService,
            final @NonNull ApplicationContext context,
            final boolean forwardingEnforced) {
        super(context, forwardingEnforced);
        this.interactionService = interactionService;
    }


    @Override
    protected void service(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException, IOException {

        val authentication = AuthSessionStoreUtil.get(request.getSession(true))
                .orElse(null);

        log.debug("new request incoming (authentication={})", authentication);

        if(authentication!=null) {
            interactionService.run(authentication, ()->{
                super.service(request, response);
            });
        } else {
            // do not open an CausewayInteraction, instead redirect to login page
            // this should happen afterwards by means of the VaadinAuthenticationHandler
            super.service(request, response);
        }

        log.debug("request was successfully serviced (authentication={})", authentication);

        if(interactionService.isInInteraction()) {
            interactionService.closeInteractionLayers();
            log.warn("after servicing current request some interactions have been closed forcefully (authentication={})", authentication);
        }

    }

}

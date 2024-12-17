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
package org.apache.causeway.incubator.viewer.vaadin.ui.app.logout;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.iactnlayer.InteractionLayerTracker;
import org.apache.causeway.core.interaction.session.CausewayInteraction;
import org.apache.causeway.core.security.authentication.logout.LogoutHandler;
import org.apache.causeway.wicketstubs.AuthenticatedWebSession;
import org.apache.causeway.wicketstubs.api.RequestCycle;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class LogoutHandlerVdn implements LogoutHandler {

    final InteractionLayerTracker interactionLayerTracker;

    @Override
    public void logout() {

        if(RequestCycle.get()==null) {
            return; // logout is only permissive if within the context of a Wicket request-cycle
        }

        val currentVdnSession = AuthenticatedWebSession.get();
        if(currentVdnSession==null) {
            return;
        }

        if(interactionLayerTracker.isInInteraction()) {
            interactionLayerTracker.currentInteraction()
            .map(CausewayInteraction.class::cast)
            .ifPresent(interaction->
                interaction.setOnClose(currentVdnSession::invalidateNow));

        } else {
            currentVdnSession.invalidateNow();
        }
    }

}

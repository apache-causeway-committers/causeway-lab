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
package org.apache.causeway.incubator.viewer.vaadin.ui.auth;

import java.util.concurrent.Callable;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.theme.Theme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.apache.causeway.applib.services.iactnlayer.InteractionContext;
import org.apache.causeway.applib.services.iactnlayer.InteractionService;
import org.apache.causeway.commons.functional.ThrowingRunnable;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.core.security.authentication.AuthenticationRequest;
import org.apache.causeway.core.security.authentication.fixtures.AuthenticationRequestLogonFixture;
import org.apache.causeway.incubator.viewer.vaadin.ui.pages.login.VaadinLoginView;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.log4j.Log4j2;

/**
 * Hooks into Vaadin's routing, such that unauthorized access is redirected to the login view.
 *
 * @since Mar 9, 2020
 */
@Component
//@PWA(name = "Example Project", shortName = "Example Project")
@Theme("causeway")
@Log4j2
@RequiredArgsConstructor
public class VaadinAuthenticationHandler
        implements
        AppShellConfigurator,
        VaadinServiceInitListener {

    @Component
    record VaadinAuthenticationHandlerConfig(@Value("${causeway.autologin:false}") boolean automaticLogin) {
    }

    private final InteractionService interactionService;
    private final MetaModelContext metaModelContext;
    private final VaadinAuthenticationHandlerConfig vaadinAuthenticationHandlerConfig;

    @Override
    public void serviceInit(ServiceInitEvent event) {

        log.info("service init event: {} config: {}", event.getSource(), vaadinAuthenticationHandlerConfig);

        event.getSource().addUIInitListener(uiEvent -> {
            uiEvent.getUI().addBeforeEnterListener(this::beforeEnter);
            uiEvent.getUI().addBeforeLeaveListener(this::beforeLeave);
        });
    }

    /**
     * @param authenticationRequest
     * @return whether login was successful
     */
    public boolean loginToSession(AuthenticationRequest authenticationRequest) {
        val authentication = metaModelContext.getAuthenticationManager()
                .authenticate(authenticationRequest);

        if (authentication != null) {
            log.debug("logging in {}", authentication.getUser().getName());
            AuthSessionStoreUtil.put(authentication);
            return true;
        }
        return false;
    }

    /**
     * Executes a piece of code in a new (possibly nested) CausewayInteraction, using the
     * current Authentication, as, at this point, should be stored in the
     * current VaadinSession.
     *
     * @param callable - the piece of code to run
     */
    public <R> R callAuthenticated(Callable<R> callable) {
        return AuthSessionStoreUtil.get()
                .map(authentication -> interactionService.call(authentication, callable))
                .orElse(null); // TODO redirect to login
    }

    /**
     * Variant of {@link #callAuthenticated(Callable)} that takes a runnable.
     *
     * @param runnable
     */
    public void runAuthenticated(ThrowingRunnable runnable) {
        final Callable<Void> callable = () -> {
            runnable.run();
            return null;
        };
        callAuthenticated(callable);
    }

    // -- HELPER

    private void beforeEnter(BeforeEnterEvent event) {
        val targetView = event.getNavigationTarget();
        log.debug("detected a routing event to {}", targetView);

        val authentication = AuthSessionStoreUtil.get().orElse(null);
        final InteractionContext finalAuthentication;
        if (vaadinAuthenticationHandlerConfig.automaticLogin && authentication == null) {
            final var autoLogin = doAutoLogin();
            finalAuthentication = autoLogin;
         } else {
            finalAuthentication = authentication;
        }

        if (finalAuthentication != null) {
            interactionService.openInteraction(finalAuthentication);
            return; // access granted
        }

        // otherwise redirect to login page
        if (!VaadinLoginView.class.equals(targetView)) {
            event.rerouteTo(VaadinLoginView.class);
        }
    }

    private InteractionContext doAutoLogin() {
        log.debug("automatic login");
        val roles = new String[]{};
        val autoLogin = metaModelContext.getAuthenticationManager()

                .authenticate(AuthenticationRequestLogonFixture.of("sven",roles));
        AuthSessionStoreUtil.put(autoLogin);
        return autoLogin;
    }

    private void beforeLeave(BeforeLeaveEvent event) {
        //causewayInteractionFactory.closeSessionStack();
    }

}

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
package org.apache.causeway.incubator.viewer.javafx.ui.main;

import java.util.Optional;

import jakarta.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import org.apache.causeway.applib.services.iactn.Interaction;
import org.apache.causeway.commons.internal.debug._Probe;
import org.apache.causeway.core.interaction.scope.TransactionBoundaryAware;
import org.apache.causeway.incubator.viewer.javafx.headless.PrimaryStageInitializer;
import org.apache.causeway.incubator.viewer.javafx.model.events.JavaFxViewerConfig;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Log4j2
public class UiBuilderFx implements PrimaryStageInitializer, TransactionBoundaryAware {

    private final ApplicationContext springContext;
    private final JavaFxViewerConfig viewerConfig;

    @SneakyThrows
    @Override
    public void onPrimaryStageReady(final Stage primaryStage) {
        log.info("JavaFX primary stage is ready");
        val layoutUrl = this.viewerConfig.getUiLayout().getURL();
        val fxmlLoader = new FXMLLoader(layoutUrl);
        fxmlLoader.setControllerFactory(springContext::getBean);
        val uiRoot = (Parent)fxmlLoader.load();
        val scene = new Scene(uiRoot);
        scene.getStylesheets().add("/ui.css");

        primaryStage.setScene(scene);
        setupTitle(primaryStage);
        setupIcon(primaryStage);
    }

    @Override
    public void beforeEnteringTransactionalBoundary(final Interaction interaction) {
        //TODO this would be the place to indicate to the user, that a long running task has started
        //scene.getRoot().cursorProperty().set(Cursor.WAIT);
        _Probe.errOut("Transaction HAS_STARTED conversationId=%s", interaction.getInteractionId());
    }

    @Override
    public void afterLeavingTransactionalBoundary(final Interaction interaction) {
        //TODO this would be the place to indicate to the user, that a long running task has ended
        //scene.getRoot().cursorProperty().set(Cursor.DEFAULT);
        _Probe.errOut("Transaction IS_ENDING interactionId=%s", interaction.getInteractionId());
    }

    // -- HELPER

    private void setupTitle(final Stage stage) {
        val title = Optional.ofNullable(viewerConfig.getApplicationTitle())
                .orElse("Unknonw Title");
        stage.setTitle(title);
    }

    private void setupIcon(final Stage stage) {
        val icon = viewerConfig.getApplicationIcon();
        if(icon==null) {
            return;
        }
        stage.getIcons().add(icon);
    }


}

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
package demoapp.javafx.integtest;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import org.apache.causeway.incubator.viewer.javafx.model.context.UiContextFx;
import org.apache.causeway.incubator.viewer.javafx.viewer.CausewayModuleIncViewerJavaFxViewer;
import org.apache.causeway.security.bypass.CausewayModuleSecurityBypass;
import org.apache.causeway.testing.integtestsupport.applib.CausewayIntegrationTestAbstract;

import demoapp.dom.DemoModuleJdo;
import jakarta.inject.Inject;

@SpringBootTest(
        classes = {
                DemoModuleJdo.class,
                DemoFxTestConfig_usingJdo.class,

                // INCUBATING
                CausewayModuleSecurityBypass.class,
                CausewayModuleIncViewerJavaFxViewer.class,
        }
)
public abstract class DemoFxTestAbstract extends CausewayIntegrationTestAbstract {

    @Inject protected UiContextFx uiContext;

    @BeforeAll
    static void beforeAll() {
       //JavafxViewer.launch(DemoAppJavaFx.class, _Constants.emptyStringArray);
    }

}

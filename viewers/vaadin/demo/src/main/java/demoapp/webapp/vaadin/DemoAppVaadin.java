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
package demoapp.webapp.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import org.apache.causeway.commons.internal.os._OsUtil;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.config.util.SpringProfileUtil;
import org.apache.causeway.extensions.commandlog.applib.CausewayModuleExtCommandLogApplib;
import org.apache.causeway.extensions.sse.wicket.CausewayModuleExtSseWicket;
import org.apache.causeway.incubator.viewer.vaadin.viewer.CausewayModuleIncViewerVaadinViewer;
import org.apache.causeway.valuetypes.asciidoc.metamodel.CausewayModuleValAsciidocMetaModel;
import org.apache.causeway.valuetypes.asciidoc.persistence.jdo.dn.CausewayModuleValAsciidocPersistenceJdoDn;
import org.apache.causeway.valuetypes.asciidoc.ui.wkt.CausewayModuleValAsciidocUiWkt;
import org.apache.causeway.valuetypes.markdown.metamodel.CausewayModuleValMarkdownMetaModel;
import org.apache.causeway.valuetypes.markdown.persistence.jdo.dn.CausewayModuleValMarkdownPersistenceJdoDn;
import org.apache.causeway.valuetypes.vega.metamodel.CausewayModuleValVegaMetaModel;
import org.apache.causeway.valuetypes.vega.persistence.jdo.dn.CausewayModuleValVegaPersistenceJdoDn;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import demoapp.dom.DemoModuleCommon;
import demoapp.web.DemoAppManifestJdo;

/**
 * Bootstrap the application.
 */
@SpringBootApplication
@Import({
    DemoModuleCommon.class,
    DemoAppManifestJdo.class,

    // Metamodel
    CausewayModuleValAsciidocMetaModel.class,
    CausewayModuleValMarkdownMetaModel.class,
    CausewayModuleValVegaMetaModel.class,
    //TODO CausewayModuleExtFullCalendarVaadin.class,

    // INCUBATING
    CausewayModuleIncViewerVaadinViewer.class, // vaadin viewer

    // Persistence (JDO/DN5)
    CausewayModuleValAsciidocPersistenceJdoDn.class,
    CausewayModuleValMarkdownPersistenceJdoDn.class,
    CausewayModuleValVegaPersistenceJdoDn.class,


    // WICKET INTEGRATION ... to allow side by side comparison
    CausewayModuleViewerWicketViewer.class, // wicket viewer
    CausewayModuleExtSseWicket.class, // server sent events
    CausewayModuleValAsciidocUiWkt.class, // ascii-doc rendering support (for Wicket)

})
public class DemoAppVaadin extends SpringBootServletInitializer {

    /**
     *
     * @param args
     * @implNote this is to support the <em>Spring Boot Maven Plugin</em>, which auto-detects an
     * entry point by searching for classes having a {@code main(...)}
     */
    public static void main(final String[] args) {

        // activates when sys-env THERE_CAN_BE_ONLY_ONE=true
        _OsUtil.thereCanBeOnlyOne();

        CausewayPresets.prototyping();
        //CausewayPresets.logging(WebRequestCycleForCauseway.class, "debug");

        SpringProfileUtil.removeActiveProfile("demo-jpa"); // just in case
        SpringProfileUtil.addActiveProfile("demo-jdo");

        CausewayModuleExtCommandLogApplib.honorSystemEnvironment();

        SpringApplication.run(new Class[] { DemoAppVaadin.class }, args);
    }

}

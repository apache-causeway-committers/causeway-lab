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
package org.apache.causeway.lab.experiments.wktajax;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.CssResourceReference;

import org.apache.causeway.lab.experiments.wktajax.sample.BasePage;

import lombok.Getter;
import lombok.val;
import lombok.experimental.Accessors;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.BootstrapBaseBehavior;
import de.agilecoders.wicket.core.markup.html.references.BootstrapJavaScriptReference;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

@ApplicationInitExtension
public class WicketAjaxApplicationConfiguration
implements WicketApplicationInitConfiguration {

  @Override
  public void init(final WebApplication webApplication) {

      final IBootstrapSettings settings = new BootstrapSettings();
      settings.setDeferJavascript(false);
      Bootstrap.install(webApplication, settings);

      webApplication.getCspSettings().blocking().disabled();

      webApplication.getHeaderContributorListeners().add(new IHeaderContributor() {
          private static final long serialVersionUID = 1L;

          @Override
          public void renderHead(final IHeaderResponse response) {
              new BootstrapBaseBehavior().renderHead(settings, response);
              response.render(new PriorityHeaderItem(JavaScriptHeaderItem
                      .forReference(BootstrapJavaScriptReference.instance())));
              response.render(CssHeaderItem.forReference(FontAwesomeCssReferenceWkt.instance()));
              response.render(CssHeaderItem.forReference(CoreCssResourceReference.instance()));
          }
      });

      val jsSettings = webApplication.getJavaScriptLibrarySettings();
      // jsSettings.setJQueryReference(JQueryResourceReference.getV3());
      jsSettings.setJQueryReference(new WebjarsJavaScriptResourceReference("/webjars/jquery/3.6.1/jquery.js"));

      webApplication.mountPage("bootstrap", BasePage.class);
  }


  static class FontAwesomeCssReferenceWkt extends WebjarsCssResourceReference {
      private static final long serialVersionUID = 1L;
      public static final String FONTAWESOME_RESOURCE = "font-awesome/6.2.0/css/all.min.css";

      @Getter(lazy = true) @Accessors(fluent = true)
      private static final FontAwesomeCssReferenceWkt instance = new FontAwesomeCssReferenceWkt();

      private FontAwesomeCssReferenceWkt() {
          super(FONTAWESOME_RESOURCE);
      }

  }

  static class CoreCssResourceReference extends CssResourceReference {
      private static final long serialVersionUID = 1L;

      @Getter(lazy = true) @Accessors(fluent = true)
      private static final CoreCssResourceReference instance = new CoreCssResourceReference();

      private CoreCssResourceReference() {
          super(WicketAjaxApplicationConfiguration.class, "css/wv-core.css");
      }
  }

}

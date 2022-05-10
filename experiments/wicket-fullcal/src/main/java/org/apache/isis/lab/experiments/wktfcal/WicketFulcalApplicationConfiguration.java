package org.apache.isis.lab.experiments.wktfcal;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.CssResourceReference;

import lombok.Getter;
import lombok.experimental.Accessors;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.BootstrapBaseBehavior;
import de.agilecoders.wicket.core.markup.html.references.BootstrapJavaScriptReference;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;

@ApplicationInitExtension
public class WicketFulcalApplicationConfiguration
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
              response.render(CssHeaderItem.forReference(FontAwesomeCssReferenceWkt.instance()));
              response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(BootstrapJavaScriptReference.instance())));
              response.render(CssHeaderItem.forReference(CoreCssResourceReference.instance()));
          }
      });

  }

  static class FontAwesomeCssReferenceWkt extends WebjarsCssResourceReference {
      private static final long serialVersionUID = 1L;
      public static final String FONTAWESOME_RESOURCE = "font-awesome/6.1.0/css/all.min.css";

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
          super(WicketFulcalApplicationConfiguration.class, "css/wv-core.css");
      }
  }

}
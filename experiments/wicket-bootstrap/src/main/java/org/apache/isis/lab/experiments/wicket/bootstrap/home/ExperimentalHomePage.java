package org.apache.isis.lab.experiments.wicket.bootstrap.home;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@WicketHomePage
public class ExperimentalHomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public ExperimentalHomePage() {

        val container = new WebMarkupContainer("selfLinkContainer");
        add(container);

        for(val ds:DemoSelect.values()) {
            container.add(addLink(container, ds));
        }

        add(new Label("demoTitle", getPageParameters().get("demo")));

    }

    // -- HELPER

    @RequiredArgsConstructor
    static enum DemoSelect {
        STRING("linkToString", "String"),
        MULTILNIE("linkToMultiline", "Multiline"),
        ;
        @Getter private final String linkId;
        @Getter private final String discriminator;
    }

    private Button addLink(final WebMarkupContainer container, final DemoSelect select){
        val button = new Button(select.linkId) {
            private static final long serialVersionUID = 1L;
            @Override
            public void onSubmit() {
                val pageParameters = new PageParameters();
                pageParameters.add("demo", select.discriminator);
                setResponsePage(ExperimentalHomePage.class, pageParameters);
            }
        };
        return button;
    }


}
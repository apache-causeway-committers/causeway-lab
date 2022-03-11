package org.apache.isis.lab.experiments.wicket.bootstrap.home;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Optional;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import org.apache.isis.lab.experiments.wicket.bootstrap.sampler.ShowCaseModel;
import org.apache.isis.lab.experiments.wicket.bootstrap.sampler.ShowCasePanel;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.FormFieldPanel.FormatModifer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@WicketHomePage
public class ShowCasePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public ShowCasePage(final PageParameters parameters) {

        val container = new WebMarkupContainer("showCaseSelect");
        add(container);

        for(val ds:ShowCase.values()) {
            container.add(addLink(container, ds));
        }

        val showCaseIfAny = ShowCase.parse(parameters.get("showCase"));

        val _showCaseModel = showCaseIfAny
                .map(showCase->{
                    val showCaseModel = new ShowCaseModel(showCase.getType());
                    showCaseModel.setTitle(showCase.getTitle());
                    showCaseModel.setValueObject(showCase.getInitialValue());
                    showCaseModel.setFormatModifers(showCase.getFormatModifers());
                    return showCaseModel;
                })
                .orElseGet(()->{
                    val showCaseModel = new ShowCaseModel(Void.class);
                    showCaseModel.setTitle("No show case selected.");
                    return showCaseModel;
                });

        add(new ShowCasePanel("showCasePanel", _showCaseModel));

    }

    // -- HELPER

    @RequiredArgsConstructor
    static enum ShowCase {
        STRING("linkToString", "String", String.class, EnumSet.noneOf(FormatModifer.class), "A String"),
        MULTILNIE("linkToMultiline", "Multiline", String.class, EnumSet.of(FormatModifer.MULITLINE),
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
                + "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
                + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris "
                + "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in "
                + "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla "
                + "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa "
                + "qui officia deserunt mollit anim id est laborum."),
        ;
        @Getter private final String linkId;
        @Getter private final String title;
        @Getter private final Class<?> type;
        @Getter private final EnumSet<FormatModifer> formatModifers;
        @Getter private final Serializable initialValue;
        static Optional<ShowCase> parse(final StringValue stringValue) {
            return Optional.ofNullable(stringValue.toOptionalString())
                    .map(ShowCase::valueOf);
        }
    }

    private Component addLink(final WebMarkupContainer container, final ShowCase showCase){
        val button = new Link<Void>(showCase.linkId) {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick() {
                val pageParameters = new PageParameters();
                pageParameters.add("showCase", showCase.name());
                setResponsePage(ShowCasePage.class, pageParameters);
            }
        };
        return button;
    }


}
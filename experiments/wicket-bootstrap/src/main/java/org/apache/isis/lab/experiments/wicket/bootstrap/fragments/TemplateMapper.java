package org.apache.isis.lab.experiments.wicket.bootstrap.fragments;

import java.util.function.Function;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;

public interface TemplateMapper {
    FragmentMapper getTemplate();

    default <T extends Component> T createComponent(
            final MarkupContainer targetContainer,
            final Function<String, T> componentFactory) {
        return new BootstrapFragment("container-" + getTemplate().getId())
            .createComponent(targetContainer, getTemplate(), componentFactory);
    }

    default Fragment createFragment(
            final MarkupContainer targetContainer) {
        return new BootstrapFragment("container-" + getTemplate().getId())
            .createFragment(targetContainer, getTemplate());
    }

}
package org.apache.isis.lab.experiments.wktfcal.fragments;

import java.util.function.Function;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;

public interface FragmentMapper {
    String getId();
    String getVariant();
    default String getContainerId() { return "container-" + getId();}
    default String getFragmentId() { return "fragment-" + getId() + "-" + getVariant();}

    default <T extends Component> T createComponent(
            final MarkupContainer targetContainer,
            final Function<String, T> componentFactory) {
        return new BootstrapFragment(this.getContainerId())
            .createComponent(targetContainer, this, componentFactory);
    }

    default Fragment createFragment(
            final MarkupContainer targetContainer) {
        return new BootstrapFragment(this.getContainerId())
            .createFragment(targetContainer, this);
    }

    default RepeatingView createRepeatingView(
            final MarkupContainer targetContainer) {
        return createComponent(targetContainer, RepeatingView::new);
    }

}
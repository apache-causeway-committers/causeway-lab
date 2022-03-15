package org.apache.isis.lab.experiments.wicket.bootstrap.fragments;

import java.util.function.Function;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

public class BootstrapFragment extends Panel {

    private static final long serialVersionUID = 1L;

    @RequiredArgsConstructor
    public static enum OutputTemplate implements FragmentMapper {
        LABEL("formatOutput", "label");
        @Getter private final String id;
        @Getter private final String variant;
    }

    @RequiredArgsConstructor
    public static enum LinkToTemplate implements FragmentMapper {
        EDIT("linkToEdit", "default"),
        COPY("linkToCopy", "default");
        @Getter private final String id;
        @Getter private final String variant;
    }

    @RequiredArgsConstructor
    public static enum InputTemplate implements FragmentMapper {
        TEXT("formValueInput", "text"),
        TEXTAREA("formValueInput", "textarea");
        @Getter private final String id;
        @Getter private final String variant;
    }

    @RequiredArgsConstructor
    public static enum FeedbackTemplate implements FragmentMapper {
        DEFAULT("validationFeedback", "default");
        @Getter private final String id;
        @Getter private final String variant;
    }

    @RequiredArgsConstructor
    public static enum ButtonGroupTemplate implements FragmentMapper {
        OUTLINE("buttons", "btnOutline"),
        RIGHT_BELOW("buttons", "btnRightBelow");
        @Getter private final String id;
        @Getter private final String variant;
    }

    @RequiredArgsConstructor
    public static enum ButtonTemplate implements FragmentMapper {
        SAVE_OUTLINED("linkToSave", "btnOutline"),
        SAVE_GROUPED("linkToSave", "btnGroup"),

        CANCEL_OUTLINED("linkToCancel", "btnOutline"),
        CANCEL_GROUPED("linkToCancel", "btnGroup");
        @Getter private final String id;
        @Getter private final String variant;
    }


    public BootstrapFragment(final String id) {
        super(id);
    }

    public Fragment createFragment(
            final MarkupContainer targetContainer,
            final FragmentMapper template) {
        targetContainer.add(this);
        return createFragment(template.getFragmentId());
    }

    public <T extends Component> T createComponent(
            final MarkupContainer targetContainer,
            final FragmentMapper template,
            final Function<String, T> componentFactory) {
        targetContainer.add(this);
        val fragment = createFragment(template.getFragmentId());
        val component = componentFactory.apply(template.getId());
        fragment.add(component);
        return component;
    }

    // -- HELPER

    private Fragment createFragment(final String fragmentId) {
        val fragment = new Fragment("container-bootstrap", fragmentId, this);
        add(fragment);
        return fragment;
    }



}

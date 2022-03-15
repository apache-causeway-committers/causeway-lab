package org.apache.isis.lab.experiments.wicket.bootstrap.fragments;

public interface FragmentMapper {
    String getId();
    String getVariant();
    default String getContainerId() { return "container-" + getId();}
    default String getFragmentId() { return "fragment-" + getId() + "-" + getVariant();}


//    default Fragment createFragment(final MarkupContainer markupProvider) {
//        return createFragment(markupProvider, markupProvider);
//    }
//
//    default Fragment createFragment(final MarkupContainer markupProvider, final MarkupContainer container) {
//        val fragment = new Fragment(getContainerId(), getFragmentId(), markupProvider);
//        container.add(fragment);
//        return fragment;
//    }
//
//    default <T extends Component> T createComponent(
//            final MarkupContainer markupProvider,
//            final Function<String, T> componentFactory) {
//        return createComponent(markupProvider, markupProvider, componentFactory);
//    }
//
//    default <T extends Component> T createComponent(
//            final MarkupContainer markupProvider,
//            final MarkupContainer container,
//            final Function<String, T> componentFactory) {
//        val fragment = createFragment(markupProvider, container);
//        val component = componentFactory.apply(getId());
//        fragment.add(component);
//        return component;
//    }
}
package org.apache.isis.lab.experiments.layout.views;


import java.util.List;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;

import org.apache.isis.lab.experiments.layout.views.about.AboutView;
import org.apache.isis.lab.experiments.layout.views.flex.GridLayoutView;
import org.apache.isis.lab.experiments.layout.views.helloworld.HelloWorldView;

import lombok.val;

/**
 * The main view is a top-level placeholder for other views.
 */
public class PageLayout extends AppLayout {

    private static final long serialVersionUID = 1L;

    private H1 viewTitle = null;

    public PageLayout() {
        val menuPosition = MenuPosition.SIDE;

        switch (menuPosition) {
        case TOP:
            addToNavbar(_TopMenuLayout.createHeaderContent(createMenuItems(menuPosition)));
            break;
        case SIDE:
            setPrimarySection(Section.DRAWER);
            addToNavbar(true, _SideMenuLayout.createHeaderContent(viewTitle = new H1()));
            addToDrawer(_SideMenuLayout.createDrawerContent(createMenuItems(menuPosition)));
            break;
        default:
            throw new IllegalArgumentException("Unexpected value: " + menuPosition);
        }

    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        if(viewTitle!=null) {
            viewTitle.setText(getCurrentPageTitle());
        }
    }

    // -- HELPER

    private List<MenuItemInfo> createMenuItems(final MenuPosition menuPosition) {
        return List.of(
                new MenuItemInfo(menuPosition, "Hello World", "la la-globe", HelloWorldView.class),
                new MenuItemInfo(menuPosition, "Grid Layout", "la la-file", GridLayoutView.class),
                new MenuItemInfo(menuPosition, "About", "la la-file", AboutView.class));
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

}

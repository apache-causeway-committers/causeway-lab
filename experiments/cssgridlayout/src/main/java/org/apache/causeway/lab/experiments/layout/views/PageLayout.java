package org.apache.causeway.lab.experiments.layout.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * The main view is a top-level placeholder for other views.
 */
public class PageLayout extends AppLayout {

    private static final long serialVersionUID = 1L;

    private H1 viewTitle = null;

    public PageLayout(
            @Autowired final MenuPosition menuPosition,
            @Autowired final MenuModel menuModel) {

        menuModel.setup();

        switch (menuPosition) {
        case TOP:
            addToNavbar(_TopMenuLayout.createHeaderContent(menuModel));
            break;
        case SIDE:
            setPrimarySection(Section.DRAWER);
            addToNavbar(true, _SideMenuLayout.createHeaderContent(viewTitle = new H1()));
            addToDrawer(_SideMenuLayout.createDrawerContent(menuModel));
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

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

}

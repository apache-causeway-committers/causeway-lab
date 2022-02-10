package org.apache.isis.lab.experiments.layout.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouterLink;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * A simple navigation item component, based on ListItem element.
 */
@RequiredArgsConstructor
public class MenuItemInfo extends ListItem {

    private static final long serialVersionUID = 1L;

    private final MenuPosition menuPosition;
    private final String menuTitle;
    private final String iconClass;

    @Getter
    private final Class<? extends Component> view;

    public void setup() {

        val routerLink = new RouterLink();
        add(routerLink);
        routerLink.setRoute(view);

        val menuTitleSpan = new Span(menuTitle);
        routerLink.add(new LineAwesomeIcon(iconClass), menuTitleSpan);

        // Use Lumo classnames for various styling
        switch (menuPosition) {
        case TOP:
            routerLink.addClassNames("flex", "h-m", "items-center", "px-s", "relative", "text-secondary");
            menuTitleSpan.addClassNames("font-medium", "text-s", "whitespace-nowrap");
            break;
        case SIDE:
            routerLink.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
            menuTitleSpan.addClassNames("font-medium", "text-s");
            break;
        default:
            throw new IllegalArgumentException("Unexpected value: " + menuPosition);
        }
    }

    /**
     * Simple wrapper to create icons using LineAwesome iconset. See
     * https://icons8.com/line-awesome
     */
    @NpmPackage(value = "line-awesome", version = "1.3.0")
    public static class LineAwesomeIcon extends Span {

        private static final long serialVersionUID = 1L;

        public LineAwesomeIcon(final String lineawesomeClassnames) {
            // Use Lumo classnames for suitable font size and margin
            addClassNames("me-s", "text-l");
            if (!lineawesomeClassnames.isEmpty()) {
                addClassNames(lineawesomeClassnames);
            }
        }
    }

}
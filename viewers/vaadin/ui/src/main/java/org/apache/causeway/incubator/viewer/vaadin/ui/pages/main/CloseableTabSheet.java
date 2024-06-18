package org.apache.causeway.incubator.viewer.vaadin.ui.pages.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;

/**
 * Customized tab sheet that allows to close tabs.
 */
public class CloseableTabSheet extends TabSheet {

    @Override
    public Tab add(Tab tab, Component content) {
        Button button = new Button(VaadinIcon.CLOSE_SMALL.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        tab.add(button);
        button.addClickListener(e -> {
            remove(tab);
        });
        return this.add(tab, content, -1);
    }
}

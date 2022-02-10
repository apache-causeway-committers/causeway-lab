package org.apache.isis.lab.experiments.layout;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.isis.lab.experiments.layout.views.MenuItemInfo;
import org.apache.isis.lab.experiments.layout.views.MenuModel;
import org.apache.isis.lab.experiments.layout.views.MenuPosition;
import org.apache.isis.lab.experiments.layout.views.about.AboutView;
import org.apache.isis.lab.experiments.layout.views.flex.GridLayoutView;
import org.apache.isis.lab.experiments.layout.views.helloworld.HelloWorldView;

@Configuration
public class LayoutConfig {

    @Bean
    public MenuPosition getMenuPosition() {
        return MenuPosition.TOP;
    }

    @Bean
    public MenuModel getMenuModel(final MenuPosition menuPosition) {
        return new MenuModel(
                List.of(
                    new MenuItemInfo(menuPosition, "Hello World", "la la-globe", HelloWorldView.class),
                    new MenuItemInfo(menuPosition, "Grid Layout", "la la-file", GridLayoutView.class),
                    new MenuItemInfo(menuPosition, "About", "la la-file", AboutView.class)));
    }

}

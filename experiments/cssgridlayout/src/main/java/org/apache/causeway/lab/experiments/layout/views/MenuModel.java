package org.apache.causeway.lab.experiments.layout.views;

import java.util.Iterator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuModel implements Iterable<MenuItemInfo> {

    private final Iterable<MenuItemInfo> menuItems;

    @Override
    public Iterator<MenuItemInfo> iterator() {
        return menuItems.iterator();
    }

    public void setup() {
        menuItems.forEach(MenuItemInfo::setup);
    }

}

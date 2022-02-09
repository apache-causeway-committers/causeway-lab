package org.apache.isis.lab.experiments.layout.views.flex;

import com.github.appreciated.css.grid.GridLayoutComponent;
import com.github.appreciated.css.grid.sizes.Flex;
import com.github.appreciated.layout.FluentGridLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.apache.isis.lab.experiments.layout.views.MainLayout;

@PageTitle("Grid Layout")
@Route(value = "grid", layout = MainLayout.class)
public class GridLayoutView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public GridLayoutView() {
        Component alignTestComponent = new ExampleCard();
        FluentGridLayout layout = new FluentGridLayout()
                .withTemplateRows(new Flex(1), new Flex(1), new Flex(1))
                .withTemplateColumns(new Flex(1), new Flex(1), new Flex(1))
                .withColumnAlign(alignTestComponent, GridLayoutComponent.ColumnAlign.END)
                .withRowAlign(alignTestComponent, GridLayoutComponent.RowAlign.END)
                .withRowAndColumn(alignTestComponent, 1, 1, 1, 3)
                .withRowAndColumn(new ExampleCard(), 2, 1)
                .withRowAndColumn(new ExampleCard(), 2, 2)
                .withRowAndColumn(new ExampleCard(), 1, 3, 3, 3);
        layout.setSizeFull();
        layout.getElement().getStyle().set("display", "grid");
        setSizeFull();
        add(layout);
    }
}


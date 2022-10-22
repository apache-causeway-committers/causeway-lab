package org.apache.causeway.lab.experiments.layout.views.flex;

import java.util.concurrent.ThreadLocalRandom;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

public class ExampleCard extends Div {

    private static final long serialVersionUID = 1L;

    public ExampleCard() {
        this.setClassName("item");
        int nextInt = ThreadLocalRandom.current().nextInt(0xffffff + 1);
        String colorCode = java.lang.String.format("#%06x", nextInt);
        this.add(new Label(colorCode));
        this.getStyle().set("background", colorCode).set("padding", "20px");
    }

}

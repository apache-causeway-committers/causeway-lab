package org.apache.causeway.lab.experiments.wktajax.home;

import java.time.LocalTime;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import org.apache.causeway.lab.experiments.wktajax.home.HomePage.PersonModel;

public class PersonPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public PersonPanel(final String id, final PersonModel personModel) {
        super(id);
        System.err.printf("%s%n", "ne person panel");
        add(new Label("time", new TimeModel()));
        add(new Label("first", personModel.first()));
        add(new Label("last", personModel.map(Person::last)));
        setOutputMarkupId(true);
    }

    private static class TimeModel implements IModel<String>  {
        private static final long serialVersionUID = 1L;

        @Override
        public String getObject() {
            return "" + LocalTime.now();
        }
    }

    record LabelModel(String label) implements IModel<String> {

        LabelModel(final String label) {
            this.label = label;
            System.err.printf("%s%n", "new label model constructed");
        }

        @Override
        public String getObject() {
            return label;
        }
    }

}

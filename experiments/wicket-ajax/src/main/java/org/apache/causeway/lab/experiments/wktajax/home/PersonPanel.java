package org.apache.causeway.lab.experiments.wktajax.home;

import java.time.LocalTime;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class PersonPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public PersonPanel(final String id, final PersonIModel2 personModel) {
        super(id);
        System.err.printf("%s%n", "new person panel");
        add(new Label("time", new TimeModel()));
        add(new Label("firstName", personModel.firstNameModel()));
        add(new Label("lastName", personModel.lastNameModel()));
        setOutputMarkupId(true);
    }

    record TimeModel() implements IModel<String>  {
        @Override
        public String getObject() {
            return "" + LocalTime.now();
        }
    }

}

package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;

public class ScalarModelHolder<T>
extends ChainingModel<ScalarModel<T>>
implements
    IModel<ScalarModel<T>> {

    private static final long serialVersionUID = 1L;

    public ScalarModelHolder(final ScalarModel<T> modelObject) {
        super(modelObject);
        // TODO Auto-generated constructor stub
    }


}

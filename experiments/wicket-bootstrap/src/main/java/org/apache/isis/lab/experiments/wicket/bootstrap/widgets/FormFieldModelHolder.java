package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;

public class FormFieldModelHolder<T>
extends ChainingModel<FormFieldModel<T>>
implements
    IModel<FormFieldModel<T>> {

    private static final long serialVersionUID = 1L;

    public FormFieldModelHolder(final FormFieldModel<T> modelObject) {
        super(modelObject);
        // TODO Auto-generated constructor stub
    }


}

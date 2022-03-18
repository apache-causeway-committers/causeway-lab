package org.apache.isis.lab.experiments.wktbs.widgets.field;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;

public class FieldModelHolder<T>
extends ChainingModel<FieldModel<T>>
implements
    IModel<FieldModel<T>> {

    private static final long serialVersionUID = 1L;

    public FieldModelHolder(final FieldModel<T> modelObject) {
        super(modelObject);
        // TODO Auto-generated constructor stub
    }


}

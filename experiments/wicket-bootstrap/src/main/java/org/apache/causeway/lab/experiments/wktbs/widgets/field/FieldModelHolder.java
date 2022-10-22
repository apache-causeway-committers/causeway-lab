package org.apache.causeway.lab.experiments.wktbs.widgets.field;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;

import org.apache.causeway.lab.experiments.wktbs.widgets.field.model.FieldModel;

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

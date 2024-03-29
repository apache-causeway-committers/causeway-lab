package org.apache.causeway.lab.experiments.wktbs.widgets.field.model;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.wicket.model.IModel;

public interface FieldModel<T> extends OutputModel<T> {

    IModel<T> getPendingValue();
    IModel<String> getValidationFeedback();
    void submitPendingValue();

    @Override
    <R> FieldModel<R> map(Class<R> resultType, Function<T, R> mapper, Function<R, T> reverseMapper);

    @Override
    default FieldModel<Boolean> asTriState() {
        if(isBoolean()) {
            return _Casts.uncheckedCast(this);
        }
        throw _Exceptions.illegalArgument("%s cannot be converted to Boolean", getType());
    }

    @Override
    default FieldModel<Boolean> asBinaryState() {
        return asTriState().map(Boolean.class,
            // forward conversion
            t->t==null
                ? Boolean.FALSE
                : t
            ,
            // reverse conversion
            UnaryOperator.identity()
        );
    }

}

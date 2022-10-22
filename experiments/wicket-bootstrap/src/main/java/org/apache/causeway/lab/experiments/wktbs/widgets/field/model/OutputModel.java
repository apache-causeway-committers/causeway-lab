package org.apache.causeway.lab.experiments.wktbs.widgets.field.model;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.wicket.model.IModel;

import org.apache.causeway.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;

public interface OutputModel<T> extends Serializable {

    EnumSet<FormatModifer> getFormatModifers();

    Class<T> getType();

    IModel<T> getValue();

    <R> OutputModel<R> map(Class<R> resultType, Function<T, R> mapper, Function<R, T> reverseMapper);

    default boolean isBoolean() {
        return getType().equals(Boolean.class)
                || getType().equals(boolean.class);
    }

    default OutputModel<Boolean> asTriState() {
        if(isBoolean()) {
            return _Casts.uncheckedCast(this);
        }
        throw _Exceptions.illegalArgument("%s cannot be converted to Boolean", getType());
    }

    default OutputModel<Boolean> asBinaryState() {
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

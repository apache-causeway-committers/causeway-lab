package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.apache.wicket.model.IModel;

import org.apache.isis.commons.internal.base._Casts;
import org.apache.isis.commons.internal.exceptions._Exceptions;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

public interface ScalarModel<T> extends Serializable {

    EnumSet<FormatModifer> getFormatModifers();

    Class<T> getType();

    IModel<T> getValue();
    IModel<T> getPendingValue();
    String validatePendingValue();
    IModel<String> getValidationFeedback();
    void submitPendingValue();

    <R> ScalarModel<R> map(Class<R> resultType, Function<T, R> mapper, Function<R, T> reverseMapper);

    default boolean isBoolean() {
        return getType().equals(Boolean.class)
                || getType().equals(boolean.class);
    }

    default ScalarModel<Boolean> asTriState() {
        if(getType().equals(boolean.class)
                ||getType().equals(Boolean.class)) {
            return _Casts.uncheckedCast(this);
        }
        throw _Exceptions.illegalArgument("%s cannot be converted to Boolean", getType());
    }

    default ScalarModel<Boolean> asBinaryState() {
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

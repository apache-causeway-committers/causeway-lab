package org.apache.isis.lab.experiments.wicket.bootstrap.util;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;

import org.apache.isis.commons.internal.base._Casts;
import org.apache.isis.commons.internal.functions._Functions.SerializableFunction;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WktUtil {

    public static <T, R> IModel<R> mapModel(
            final IModel<T> source,
            final SerializableFunction<T, R> mapper,
            final SerializableFunction<R, T> reverseMapper) {

        return new ChainingModel<R>(source) {

            private static final long serialVersionUID = 1L;

            @Override
            public R getObject() {
                return mapper.apply(source().getObject());
            }

            @Override
            public void setObject(final R object) {
                source().setObject(reverseMapper.apply(object));
            }

            // -- HELPER

            private IModel<T> source() {
                return _Casts.uncheckedCast(getTarget());
            }

        };
    }

}

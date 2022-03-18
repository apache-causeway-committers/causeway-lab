package org.apache.isis.lab.experiments.wktbs.widgets.field.model;

import java.util.EnumSet;
import java.util.function.Function;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;

import org.apache.isis.commons.internal.base._Casts;
import org.apache.isis.lab.experiments.wktbs.widgets.field.FieldPanel.FormatModifer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
abstract class FieldModelMappable<T> implements FieldModel<T> {

    private static final long serialVersionUID = 1L;

    @Getter(onMethod_ = {@Override}) private final @NonNull Class<T> type;
    @Getter(onMethod_ = {@Override}) private final @NonNull EnumSet<FormatModifer> formatModifers;

    @Override
    public final <R> FieldModel<R> map(
            final Class<R> resultType,
            final Function<T, R> mapper,
            final Function<R, T> reverseMapper) {
        return new FieldModelMappable<R>(resultType, formatModifers) {

            private static final long serialVersionUID = 1L;

            @Getter(onMethod_ = {@Override}, lazy = true)
            private final IModel<R> value = mapModel(mappedFrom().getValue());

            @Getter(onMethod_ = {@Override}, lazy = true)
            private final IModel<R> pendingValue = mapModel(mappedFrom().getPendingValue());

            @Override
            public IModel<String> getValidationFeedback() {
                return mappedFrom().getValidationFeedback();
            }

            @Override
            public void submitPendingValue() {
                mappedFrom().submitPendingValue();
            }

            // -- HELPER

            protected FieldModel<T> mappedFrom() {
                return FieldModelMappable.this;
            }

            private IModel<R> mapModel(final IModel<T> source) {
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

        };
    }

}

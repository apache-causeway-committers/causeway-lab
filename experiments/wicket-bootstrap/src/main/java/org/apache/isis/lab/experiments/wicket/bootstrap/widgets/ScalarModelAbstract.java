package org.apache.isis.lab.experiments.wicket.bootstrap.widgets;

import java.util.EnumSet;
import java.util.function.Function;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;

import org.apache.isis.commons.internal.base._Casts;
import org.apache.isis.lab.experiments.wicket.bootstrap.widgets.ScalarPanel.FormatModifer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ScalarModelAbstract<T> implements ScalarModel<T> {

    private static final long serialVersionUID = 1L;

    @Getter(onMethod_ = {@Override}) private final @NonNull Class<T> type;
    @Getter(onMethod_ = {@Override}) private final @NonNull EnumSet<FormatModifer> formatModifers;

    @Override
    public final <R> ScalarModel<R> map(
            final Class<R> resultType,
            final Function<T, R> mapper,
            final Function<R, T> reverseMapper) {
        return new ScalarModelAbstract<R>(resultType, formatModifers) {

            private static final long serialVersionUID = 1L;

            @Getter(onMethod_ = {@Override}, lazy = true)
            private final IModel<R> value = mapModel(mappedFrom().getValue());

            @Getter(onMethod_ = {@Override}, lazy = true)
            private final IModel<R> pendingValue = mapModel(mappedFrom().getPendingValue());

            @Override
            public String validatePendingValue() {
                return mappedFrom().validatePendingValue();
            }

            @Override
            public IModel<String> getValidationFeedback() {
                return mappedFrom().getValidationFeedback();
            }

            @Override
            public void submitPendingValue() {
                mappedFrom().submitPendingValue();
            }

            // -- HELPER

            protected ScalarModel<T> mappedFrom() {
                return ScalarModelAbstract.this;
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

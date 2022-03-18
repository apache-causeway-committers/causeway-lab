package org.apache.isis.lab.experiments.wktbs.util;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;
import org.danekja.java.misc.serializable.SerializableRunnable;
import org.danekja.java.util.function.serializable.SerializableConsumer;
import org.danekja.java.util.function.serializable.SerializableFunction;

import org.apache.isis.commons.internal.base._Casts;

import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WktUtil {

    public AjaxLink<Void> createLink(
            final String id,
            final SerializableConsumer<AjaxRequestTarget> onClick) {
        val link = new AjaxLink<Void>(id){
            private static final long serialVersionUID = 1L;
            @Override public void onClick(final AjaxRequestTarget target) {
                onClick.accept(target);
            }
        };
        return link;
    }

    public Form<Void> createForm(
            final String id,
            final SerializableRunnable onSubmit) {
        val form = new Form<Void>(id){
            private static final long serialVersionUID = 1L;
            @Override protected void onSubmit() {
                onSubmit.run();
            }
        };
        return form;
    }

    // use Lambda model instead
    public <T, R> IModel<R> mapModel(
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

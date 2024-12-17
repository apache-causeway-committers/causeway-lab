package org.apache.causeway.incubator.viewer.vaadin.model.util;

import java.util.Optional;

import org.apache.causeway.applib.services.bookmark.Bookmark;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAction;
import org.apache.causeway.wicketstubs.api.PageParameters;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageParameterUtils {

    public static final String CAUSEWAY_NO_HEADER_PARAMETER_NAME = "causeway.no.header";

    public PageParameters createPageParametersForObject(final ManagedObject adapter) {
        return null;
    }

    public PageParameters createPageParametersForAction(
            final ManagedObject adapter,
            final ObjectAction objectAction,
            final Can<ManagedObject> paramValues) {
        return null;
    }

    public Optional<Bookmark> toBookmark(final PageParameters pageParameters) {
        return Optional.empty();
    }

}

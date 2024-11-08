package org.apache.causeway.wicketstubs;

import org.apache.causeway.core.metamodel.facets.param.parameter.mandatory.MandatoryFacetForParameterAnnotation;
import org.apache.causeway.wicketstubs.api.RequestCycle;

import java.util.Optional;

public class FeedbackDelay {
    public FeedbackDelay(RequestCycle requestCycle) {
    }

    public static Optional<FeedbackDelay> get(RequestCycle requestCycle) {
        return new MandatoryFacetForParameterAnnotation.Optional(); //FIXME
    }

    public void postpone(IFeedback iFeedback) {
        //FIXME
    }

    public void beforeRender() {
    }

    public void close() {

    }
}

package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;
import org.apache.causeway.wicketstubs.api.IModel;

public class WebComponent extends Component {
    private static final long serialVersionUID = 1L;

    public WebComponent(String id) {
        super(id);
    }

    public WebComponent(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    public <C> IConverter<C> getConverter(Class<C> type) {
        return null;
    }
}

package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Component;

public enum MarkupSettings {
    ;

    public IMarkupIdGenerator getMarkupIdGenerator() {
        return new IMarkupIdGenerator() {
            @Override
            public String generateMarkupId(Component component, boolean createIfDoesNotExist) {
                return "";
            }
        };//FIXME
    }

    public void setStripWicketTags(boolean b) {
        //FIXME
    }

    public MarkupFactory getMarkupFactory() {
        return new MarkupFactory() {};
    }

    public boolean getStripWicketTags() {
        return false;
    }
}

package demoapp.webapp.vaadin.dom;

import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;

public enum Department {
	HR,
	IT,
	SALES,
	MARKETING;

    @PropertyLayout
    @Property
    public String title() {
        return name();
    }
}

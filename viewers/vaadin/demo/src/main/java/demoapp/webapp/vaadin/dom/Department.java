package demoapp.webapp.vaadin.dom;

import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Title;
import org.apache.causeway.applib.annotation.Value;

import lombok.Getter;

@Value
@Getter
public enum Department {
    HR("Human Resources"),
    IT("Information Technology"),
    SALES("Sales"),
    MARKETING("Marketing");

    private final String title;

    Department(String title) {
        this.title = title;
    }

    @Title
    @PropertyLayout(sequence = "1")
    @Property
    public String getTitle() {
        return title;
    }
}


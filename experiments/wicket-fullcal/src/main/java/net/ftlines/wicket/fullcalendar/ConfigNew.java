package net.ftlines.wicket.fullcalendar;

import com.fasterxml.jackson.annotation.JsonRawValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConfigNew implements Serializable {

    private Header headerToolbar = new Header();
    private List<EventSource> eventSources = new ArrayList<EventSource>();
    private String themeSystem;
    private boolean selectable = true;

    // events
    private String eventClick;
    private String select;

    public Header getHeaderToolbar() {
        return headerToolbar;
    }

    public ConfigNew add(EventSource eventSource) {
        eventSources.add(eventSource);
        return this;
    }

    public Collection<EventSource> getEventSources() {
        return Collections.unmodifiableList(eventSources);
    }

    public String getThemeSystem() {
        return themeSystem;
    }

    public void setThemeSystem(String themeSystem) {
        this.themeSystem = themeSystem;
    }

    @JsonRawValue
    public String getEventClick() {
        return eventClick;
    }

    public void setEventClick(String eventClick) {
        this.eventClick = eventClick;
    }

    @JsonRawValue
    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public boolean isSelectable() {
        return selectable;
    }
}

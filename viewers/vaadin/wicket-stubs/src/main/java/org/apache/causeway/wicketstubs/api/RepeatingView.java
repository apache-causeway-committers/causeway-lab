package org.apache.causeway.wicketstubs.api;

public class RepeatingView {
    public RepeatingView(String idRows) {
    }

    public String newChildId() {
        return "RepeatingView_" + hashCode();
    }

    public void add(WebMarkupContainer row) {
    }
}

package org.apache.causeway.wicketstubs;

import org.apache.causeway.applib.value.Markup;
import org.apache.causeway.wicketstubs.api.ComponentTag;

//FIXME
public class MarkupStream {
    public MarkupStream(IMarkupFragment markup) {
    }

    public MarkupStream(Markup markup) {
    }

    public boolean isCurrentIndexInsideTheStream() {
        return false;
    }

    public MarkupElement nextOpenTag() {
        return null;
    }

    public boolean atCloseTag() {
        return false;
    }

    public void skipToMatchingCloseTag(ComponentTag tag) {
    }

    public IMarkupFragment getMarkupFragment() {
        return null;
    }

    public void throwMarkupException(String s) {
    }

    public MarkupElement get() {
        return null;
    }

    public void next() {

    }

    public int getCurrentIndex() {
        return 0;
    }

    public void setCurrentIndex(int index) {
    }

    public ComponentTag getTag() {
        return null;
    }

    public void skipComponent() {

    }

    public ComponentTag getPreviousTag() {
        return null;
    }

    public void skipRawMarkup() {

    }

    public Object get(int i) {
        return null;
    }
}

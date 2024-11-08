package org.apache.causeway.wicketstubs;

public interface IMarkupFragment {
    int size(); 

    MarkupElement get(int i);

    MarkupResourceStream getMarkupResourceStream();

    IMarkupFragment find(String id);
}

package org.apache.causeway.wicketstubs;

public interface IMarkupFragment {
    int size(); 

    MarkupElement get(int i);

    IMarkupFragment find(String id);
}

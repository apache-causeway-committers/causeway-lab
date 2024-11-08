package org.apache.causeway.wicketstubs;

public interface IWrappedHeaderItem {
    HeaderItem wrap(HeaderItem bundle);

    Object getWrapped();
}

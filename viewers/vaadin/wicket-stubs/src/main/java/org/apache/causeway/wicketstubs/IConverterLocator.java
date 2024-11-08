package org.apache.causeway.wicketstubs;

public interface IConverterLocator {
    <C> IConverter<C> getConverter(Class<C> type);
}

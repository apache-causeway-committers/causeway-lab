package org.apache.causeway.wicketstubs.api;

import org.apache.causeway.wicketstubs.IEventSource;

public interface IEvent<T> {

    boolean isStop();

    Broadcast getType();

    IEventSource getSource();

    T getPayload();
}

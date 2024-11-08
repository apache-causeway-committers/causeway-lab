package org.apache.causeway.wicketstubs.api;

//package org.apache.wicket.event;

import org.apache.causeway.wicketstubs.IEventSource;

public interface IEvent<T> {
    void stop();

    boolean isStop();

    void dontBroadcastDeeper();

    Broadcast getType();

    IEventSource getSource();

    T getPayload();
}

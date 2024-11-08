package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Broadcast;
import org.apache.causeway.wicketstubs.api.Component;

final class ComponentEventSender implements IEventSource {
    public ComponentEventSender(Component component, FrameworkSettings frameworkSettings) {
    }

    public <T> void send(IEventSink sink, Broadcast type, T payload) {

    }
}
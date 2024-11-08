package org.apache.causeway.wicketstubs;

public interface ISessionStore {
    void registerUnboundListener(Application application);//FIXME

    void destroy();

    Session lookup(Request request);

    public interface UnboundListener {
    }
}

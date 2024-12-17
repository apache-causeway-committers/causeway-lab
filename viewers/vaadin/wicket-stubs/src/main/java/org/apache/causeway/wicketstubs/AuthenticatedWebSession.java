package org.apache.causeway.wicketstubs;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.causeway.wicketstubs.api.AbstractAuthenticatedWebSession;

public abstract class AuthenticatedWebSession
        extends AbstractAuthenticatedWebSession {
    private static final long serialVersionUID = 1L;
    private final AtomicBoolean signedIn = new AtomicBoolean(false);

    public static AuthenticatedWebSession get() {
        return null;
    }

    public AuthenticatedWebSession(Request request) {
        super(request);
    }

    public final boolean signIn(String username, String password) {
        boolean authenticated = this.authenticate(username, password);
        if (!authenticated && this.signedIn.get()) {
            this.signOut();
        } else if (authenticated && this.signedIn.compareAndSet(false, true)) {
            this.bind();
        }

        return authenticated;
    }

    protected abstract void bind();

    protected abstract boolean authenticate(String var1, String var2);

    protected final void signIn(boolean value) {
        this.signedIn.set(value);
    }

    public final boolean isSignedIn() {
        return this.signedIn.get();
    }

    public void signOut() {
        this.invalidate();
    }

    public void invalidate() {
        this.signedIn.set(false);
        super.invalidate();
    }

    public void invalidateNow() {
    }
}

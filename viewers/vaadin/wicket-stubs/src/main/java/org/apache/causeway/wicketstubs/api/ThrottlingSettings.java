package org.apache.causeway.wicketstubs.api;

import java.time.Duration;

public class ThrottlingSettings implements IClusterable {
    private static final long serialVersionUID = 1L;
    private Duration delay;
    private final String id;
    private boolean postponeTimerOnUpdate;

    public ThrottlingSettings(Duration delay) {
        this((String) null, delay, false);
    }

    public ThrottlingSettings(Duration delay, boolean postponeTimerOnUpdate) {
        this((String) null, delay, postponeTimerOnUpdate);
    }

    public ThrottlingSettings(String id, Duration delay) {
        this(id, delay, false);
    }

    public ThrottlingSettings(String id, Duration delay, boolean postponeTimerOnUpdate) {
        this.id = id;
        this.delay = (Duration) Args.notNull(delay, "delay");
        this.postponeTimerOnUpdate = postponeTimerOnUpdate;
    }

    public Duration getDelay() {
        return this.delay;
    }

    public void setDelay(Duration delay) {
        this.delay = (Duration) Args.notNull(delay, "delay");
    }

    public String getId() {
        return this.id;
    }

    public boolean getPostponeTimerOnUpdate() {
        return this.postponeTimerOnUpdate;
    }

    public void setPostponeTimerOnUpdate(boolean postponeTimerOnUpdate) {
        this.postponeTimerOnUpdate = postponeTimerOnUpdate;
    }
}


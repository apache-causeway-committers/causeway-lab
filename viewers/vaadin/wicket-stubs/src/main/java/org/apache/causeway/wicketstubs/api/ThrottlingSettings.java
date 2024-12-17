package org.apache.causeway.wicketstubs.api;

import java.time.Duration;

public class ThrottlingSettings implements IClusterable {
    private Duration delay = Duration.ofSeconds(1);
    private final String id = null;

    public ThrottlingSettings(Duration delay) {
        this(null, delay, false);
    }

    public ThrottlingSettings(Duration delay, boolean postponeTimerOnUpdate) {
        this(null, delay, postponeTimerOnUpdate);
    }

    public ThrottlingSettings(String id, Duration delay) {
        this(id, delay, false);
    }

    public ThrottlingSettings(String id, Duration delay, boolean postponeTimerOnUpdate) {
    }

    public Duration getDelay() {
        return this.delay;
    }

    public void setDelay(Duration delay) {
        this.delay = Args.notNull(delay, "delay");
    }

    public String getId() {
        return this.id;
    }

}


package org.apache.causeway.wicketstubs;

import org.apache.causeway.wicketstubs.api.Behavior;
import org.apache.causeway.wicketstubs.api.Component;

import java.util.List;

public class Behaviors {
    public static void onRemove(Component component) {
    }

    public static void detach(Component component) {
    }

    public static <M extends Behavior> List<M> getBehaviors(Component component, Class<M> type) {
        return null;
    }

    public static void remove(Component component, Behavior behavior) {
    }

    public static Behavior getBehaviorById(Component component, int id) {
        return null;
    }

    public static int getBehaviorId(Component component, Behavior behavior) {
        return 0;
    }

    public static void add(Component component, Behavior[] behaviors) {

    }
}

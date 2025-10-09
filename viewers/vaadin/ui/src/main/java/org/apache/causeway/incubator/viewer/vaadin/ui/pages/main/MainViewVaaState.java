package org.apache.causeway.incubator.viewer.vaadin.ui.pages.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.vaadin.flow.spring.annotation.UIScope;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.interactions.managed.ManagedAction;
import org.apache.causeway.core.metamodel.object.ManagedObject;

import lombok.Data;
import lombok.val;

@UIScope
@Data
@org.springframework.stereotype.Component
public class MainViewVaaState {

    public interface NavigationState {
        UUID id();

        record ActionResultState(UUID id, ManagedObject actionResult, ManagedAction managedAction, Can<ManagedObject> params) implements NavigationState {
        }

        record ObjectState(UUID id, ManagedObject object) implements NavigationState {
        }
    }

    private List<NavigationState> navigationStates = new ArrayList<>();

    public NavigationState.ObjectState addManagedObject(ManagedObject object) {
        val id = UUID.randomUUID();
        val state = new NavigationState.ObjectState(id, object);
        navigationStates.add(state);
        return state;
    }

    public NavigationState.ActionResultState addActionResult(ManagedObject actionResult, ManagedAction managedAction, Can<ManagedObject> params) {
        val id = UUID.randomUUID();
        val state = new NavigationState.ActionResultState(id, actionResult, managedAction, params);
        navigationStates.add(state);
        return state;
    }

    public void removeNavigationState(UUID id) {
        navigationStates.removeIf(ns -> Objects.equals(id,ns.id()));
    }

    public List<NavigationState> getNavigationStates() {
        return List.copyOf(navigationStates);
    }
}


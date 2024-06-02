package org.apache.causeway.incubator.viewer.vaadin.ui.pages.main;

import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Data;

@UIScope
@Data
@org.springframework.stereotype.Component
public class MainViewVaaState {
    // FIXME Alf useful?
    public interface NavigationState {
        record ExecuteAction(String actionUrlId) implements NavigationState {
        }

        record ActionResult(String navigationTarget) implements NavigationState {
        }

        record ObjectView(String navigationTarget) implements NavigationState {
        }

        record Initial() implements NavigationState {
        }
    }


    private NavigationState navigationState = new NavigationState.Initial();
}


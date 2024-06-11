package org.apache.causeway.incubator.viewer.vaadin.ui.pages.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.StyleSheet;

@Tag("i")
@StyleSheet("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css")
public class FaIcon extends Component {

    public FaIcon(String classNames) {
        addClassNames(classNames);
    }
}

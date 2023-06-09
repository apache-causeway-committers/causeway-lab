/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.causeway.incubator.viewer.javafx.model.util;

import java.util.function.Predicate;

import org.springframework.lang.Nullable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class _fx {

    // -- OBSERVABLES

    public static ObservableValue<String> newStringReadonly(String value) {
        return new ReadOnlyStringWrapper(value);
    }

    public static <T> ObservableValue<T> newObjectReadonly(T value) {
        return new ReadOnlyObjectWrapper<T>(value);
    }

    // -- COMPONENT FACTORIES

    public static <T extends Node> T add(Pane container, T component) {
        container.getChildren().add(component);
        return component;
    }

    public static Label newLabel(Pane container, String label) {
        val component = new Label(label);
        container.getChildren().add(component);
        return component;
    }

    public static Label newValidationFeedback(Pane container) {
        val component = new Label();
        container.getChildren().add(component);
        visibilityLayoutFix(component);
        component.setStyle("-fx-color: red");
        component.visibleProperty().addListener((e, o, visible)->{
            if(visible) {
                borderDashed(container, Color.RED);
            } else {
                container.setBorder(null);
            }
        });
        return component;
    }

    public static Button newButton(Pane container, String label, EventHandler<ActionEvent> eventHandler) {
        val component = new Button(label);
        container.getChildren().add(component);
        component.setOnAction(eventHandler);
        return component;
    }

    public static HBox newHBox(Pane container) {
        val component = new HBox();
        container.getChildren().add(component);
        _fx.padding(component, 4, 8);
        _fx.borderDashed(component, Color.BLUE); // debug
        return component;
    }

    public static VBox newVBox(Pane container) {
        val component = new VBox();
        container.getChildren().add(component);
        _fx.padding(component, 4, 8);
        _fx.borderDashed(component, Color.GREEN); // debug
        return component;
    }

    public static VBox newVBox(TitledPane container) {
        val component = new VBox();
        container.setContent(component);
        return component;
    }

    public static FlowPane newFlowPane(@Nullable Pane container) {
        val component = new FlowPane();
        if(container!=null) {
            container.getChildren().add(component);
        }
        _fx.padding(component, 4, 8);
        _fx.hideUntilPopulated(component);
        return component;
    }

    public static GridPane newGrid(Pane container) {
        val component = new GridPane();
        container.getChildren().add(component);
        return component;
    }

    public static GridPane newGrid(TitledPane container) {
        val component = new GridPane();
        container.setContent(component);
        return component;
    }

    public static <T extends Node> T addGridCell(GridPane container, T cellNode, int column, int row) {
        container.add(cellNode, column, row);
        return cellNode;
    }

    public static TabPane newTabGroup(Pane container) {
        val component = new TabPane();
        container.getChildren().add(component);
        return component;
    }

    public static Tab newTab(TabPane container, String label) {
        val component = new Tab(label);
        container.getTabs().add(component);
        return component;
    }

    public static Accordion newAccordion(Pane container) {
        val component = new Accordion();
        container.getChildren().add(component);
        return component;
    }

    public static Menu newMenu(MenuBar container, String label) {
        val component = new Menu(label);
        container.getMenus().add(component);
        return component;
    }

    public static MenuItem newMenuItem(Menu container, String label) {
        val component = new MenuItem(label);
        container.getItems().add(component);
        return component;
    }

    public static TitledPane newTitledPane(Accordion container, String label) {
        val component = new TitledPane();
        container.getPanes().add(component);
        component.setText(label);
        component.setAnimated(true);
        return component;
    }


    /**
     * @param <S> The type of the TableView generic type (i.e. S == TableView&lt;S&gt;)
     * @param <T> The type of the content in all cells in this TableColumn.
     * @param tableView
     * @param columnLabel
     * @param columnType
     * @return a new column as added to the {@code tableView}
     */
    public static <S, T> TableColumn<S, T> newColumn(TableView<S> tableView, String columnLabel, Class<T> columnType) {
        val column = new TableColumn<S, T>(columnLabel);
        tableView.getColumns().add(column);
        return column;
    }

    // -- VISIBILITY

    /**
     * Do not consider node for layout calculations, when not visible
     * @param node
     */
    public static Node visibilityLayoutFix(Node node) {
        node.managedProperty().bind(node.visibleProperty());
        return node;
    }

    // -- ICONS

    public static Image imageFromClassPath(@NonNull Class<?> cls, String resourceName) {
        return new Image(cls.getResourceAsStream(resourceName));
    }

    public static ImageView iconForImage(Image image, int width, int height) {
        val icon = new ImageView(image);
        icon.setFitWidth(width);
        icon.setFitHeight(height);
        return icon;
    }

    public static Button bottonForImage(Image image, int width, int height) {
        val icon = new ImageView(image);
        icon.setPreserveRatio(true);
        icon.setFitWidth(width-2);
        icon.setFitHeight(height-2);

        val btn = new Button();
        btn.setGraphic(icon);
        btn.setMaxSize(width, height);
        btn.setMinSize(width, height);
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        return btn;
    }

    // -- LAYOUTS

    public static void padding(Region region, int hPadding, int vPadding) {
        region.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));
    }

    public static void toolbarLayoutPropertyAssociated(FlowPane component) {
        component.setPadding(new Insets(0, 12, 0, 12));
        component.setHgap(10);
        component.setVgap(10);
    }

    public static void toolbarLayout(FlowPane component) {
        component.setPadding(new Insets(15, 12, 15, 12));
        component.setHgap(10);
        component.setVgap(10);

        _fx.borderDashed(component, Color.RED); // debug
        _fx.backround(component, Color.FLORALWHITE);
    }

    public static void vcausewaytDepthFirst(Node component, Predicate<Node> onNode) {
        val doContinue = onNode.test(component);
        if(doContinue && (component instanceof Pane)) {
            ((Pane) component).getChildrenUnmodifiable()
            .forEach(child->vcausewaytDepthFirst(child, onNode));
        }
    }

//XXX Superseded by visibilityLayoutFix(Node node)
//    private static boolean isEmptyOrHidden(Node component) {
//        if(component instanceof Pane) {
//            val children = ((Pane) component).getChildrenUnmodifiable();
//            if(children.isEmpty()) {
//                return true; // empty
//            }
//            //return true only if all children are empty or hidden
//            val atLeastOneIsNonEmptyOrVisible = children.stream()
//                    .anyMatch(child->!isEmptyOrHidden(child));
//            return !atLeastOneIsNonEmptyOrVisible;
//        }
//        return !component.isVisible();
//    }


    public static void hideUntilPopulated(Pane component) {
        component.setVisible(false);
        component.getChildren().addListener((Change<? extends Node> change) -> {
            if(change.next() && change.wasAdded()) {

//                change.getAddedSubList().stream()
//                .filter(_Predicates.instanceOf(Pane.class))
//                .map(Pane.class::cast)
//                .forEach(newPane->{
//                    _Probe.errOut("newPane %s", ""+newPane);
//                });


                component.setVisible(true);
            }
        });

//        component.setOnMouseClicked(e->{
//
//            _Probe.errOut("event %s", ""+e);
//
//            vcausewaytDepthFirst(component, node->{
//                _Probe.errOut("node %s", ""+node);
//                return true;
//            });
//
//        });

    }


    // -- STYLES

    public static <T extends Labeled> T h1(T component) {
        component.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        return component;
    }

    public static <T extends Labeled> T h2(T component) {
        component.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        return component;
    }

    public static <T extends Labeled> T h3(T component) {
        component.setFont(Font.font("Verdana", FontWeight.NORMAL, 17));
        return component;
    }

    public static <T extends Region> T backround(T region, Color color) {
        region.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        return region;
    }

    public static <T extends Region> T borderDashed(T region, Color color) {
        region.setBorder(new Border(new BorderStroke(
                color,
                BorderStrokeStyle.DASHED,
                new CornerRadii(3),
                new BorderWidths(1))));
        return region;
    }

    // -- HACKS

    /**
     * {@code menu.setOnAction(action)} does nothing if the menu has no menu items
     */
    public static void setMenuOnAction(Menu menu, EventHandler<ActionEvent> action) {

        _fx.newMenuItem(menu, "dummy");
        menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
        menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());

        menu.setOnAction(action);
    }


}

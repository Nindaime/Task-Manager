/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;

/**
 *
 * @author PETER
 */
public class PageController extends StackPane {
    public static HashMap<String, Pane> screens = new HashMap<>();
    public static StackPane childrenNodes = new StackPane();

    public PageController() {
        super();
    }

    public void addPage(String name, Pane page) {
        screens.put(name, page);
    }

    public Pane getPage(String name) {
        return screens.get(name);
    }

    public boolean loadPage(String name, String resource) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource(resource));
            Pane loadPage = (Pane) pageLoader.load();
            ControlledPage controlledPage = ((ControlledPage) pageLoader.getController());
            controlledPage.setPageParent(this);
            addPage(name, loadPage);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setPage(final String name) {
        Pane pane = screens.get(name);
        if (pane != null) {
            final DoubleProperty opacity = opacityProperty();

            if (name == "ViewAssignedTask") {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAssignedTask.fxml"));
                    Pane viewAssignedTaskPane = (Pane) loader.load();
                    ViewAssignedTaskController controller = (ViewAssignedTaskController) loader.getController();
                    controller.setDisplay();

                    this.setup(viewAssignedTaskPane, opacity);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return true;
            }

            if (name == "ViewStaffs") {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStaffs.fxml"));
                    Pane ViewStaffsPane = (Pane) loader.load();
                    ViewStaffsController controller = (ViewStaffsController) loader.getController();
                    controller.setDisplay();

                    this.setup(ViewStaffsPane, opacity);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return true;
            }

            if (name == "ViewSubordinates") {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSubordinates.fxml"));
                    Pane viewSubordinatesPane = (Pane) loader.load();
                    ViewSubordinatesController controller = (ViewSubordinatesController) loader.getController();
                    controller.setDisplay();

                    this.setup(viewSubordinatesPane, opacity);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return true;
            }


            if (name == "MonitorTask") {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MonitorTask.fxml"));
                    Pane monitorTaskPane = (Pane) loader.load();
                    // MonitorTaskController controller = (MonitorTaskController) loader.getController();
                    // controller.setAssignee();

                    this.setup(monitorTaskPane, opacity);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return true;
            }


            if (name == "AssignTask") {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignTask.fxml"));
                    Pane assignTaskPane = (Pane) loader.load();
                    AssignTaskController controller = (AssignTaskController) loader.getController();
                    // controller.setAssignee();
                    controller.runSetup();

                    this.setup(assignTaskPane, opacity);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return true;
            }

            this.setup(pane, opacity);
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
    }

    public void setup(Pane pane, DoubleProperty opacity) {
        if (!getChildren().isEmpty()) { // if there is more than one screen
            Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                    new KeyFrame(Duration.millis(400), (ActionEvent t) -> {
                        getChildren().remove(0); // remove the displayed screen
                        getChildren().add(0, pane); // add the screen
                        getChildren().get(0).requestFocus();
                        Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(Duration.millis(200), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }, new KeyValue(opacity, 0.0)));
            fade.play();

        } else {
            setOpacity(0.0);
            getChildren().add(pane); // no one else been displayed, then just show
            Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(opacity, 1.0)));
            fadeIn.play();
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen doesn't exist");
            return false;
        } else
            return true;
    }

}

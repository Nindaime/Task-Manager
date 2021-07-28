/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.util.Duration;
import project.TaskModel.AssignedTask;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class TaskLabelController implements Initializable {

    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem start;
    private MenuItem stop;
    private MenuItem pause;
    private MenuItem suspend;

    private static ArrayList<ContextMenu> contextMenus = new ArrayList<>();

    private AssignedTask task;
    private Label timerLabel;


    public TaskLabelController(AssignedTask task) {
        this.task = task;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        this.contextMenus.add(contextMenu);
    }

    @FXML
    private void startTask(ActionEvent event) throws ClassNotFoundException, SQLException {
        MainPageController.setSelectedTask(task);

        task.startTask();
//        start.setDisable(true);
//        stop.setDisable(false);
//        pause.setDisable(false);
//        suspend.setDisable(false);

//        for (ContextMenu contextMenu : contextMenus) {
//            if (contextMenu == this.contextMenu) {
//                break;
//            } else {
//                contextMenu.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
//            }
//        }
    }

    private void enableContextMenus() {
        for (ContextMenu contextMenu : contextMenus) {
            if (contextMenu == this.contextMenu) {
                break;
            } else {
                contextMenu.addEventFilter(ContextMenuEvent.ANY, Event::consume);
            }
        }
    }

    @FXML
    private void cancelTask(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, ParseException {

        task.cancelTask();
//        enableContextMenus();
//        start.setDisable(false);
//        stop.setDisable(true);
//        pause.setDisable(false);
//        suspend.setDisable(false);
        LoginPageController.prepareMainPage(User.getUsername());
    }



    @FXML
    private void suspendTask(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, ParseException {
        task.suspendTask();
//        enableContextMenus();
//        start.setDisable(false);
//        stop.setDisable(false);
//        pause.setDisable(false);
//        suspend.setDisable(true);
        LoginPageController.prepareMainPage(User.getUsername());
    }
    @FXML
    private void finishTask(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, ParseException {
        task.finishTask();
//        enableContextMenus();
//        start.setDisable(false);
//        stop.setDisable(false);
//        pause.setDisable(false);
//        suspend.setDisable(true);
        LoginPageController.prepareMainPage(User.getUsername());
    }


}

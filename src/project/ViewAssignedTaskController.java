/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.Component.AssignedTaskDetailController;
import project.Navigation.Navigation;
import project.TaskModel.AssignedTask;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class ViewAssignedTaskController implements Initializable, ControlledPage {
    PageController myController;
    Connection connection;

    public ViewAssignedTaskController() {
    }

    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }

    @FXML
    private VBox displayBox;

    @FXML
    private Button closeButton;

    @FXML
    private BorderPane messageContent;

    @FXML
    private void backToSend(ActionEvent event) {
        myController.setPage(Navigation.SendMessage);
        messageContent.getChildren().clear();
        loaded = false;
    }

    ArrayList<String> contents = new ArrayList<>();
    ArrayList<String> contentSub = new ArrayList<>();
    public static boolean loaded;

    @FXML
    private void initializeMessage(MouseEvent event) throws ClassNotFoundException, SQLException, ParseException {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void hover_out(MouseEvent event) {
    }

    @FXML
    private void hover_in(MouseEvent event) {
    }

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        messageContent.getChildren().clear();
        stage.close();
    }

    public void setDisplay() {
        ArrayList<AssignedTask> tasks;
        try {
            tasks = User.getAssignedTask();
            this.getAssignedTaskUI(tasks);
        } catch (ClassNotFoundException | SQLException | ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void getAssignedTaskUI(ArrayList<AssignedTask> tasks) {
        tasks.forEach(task -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./Component/AssignedTaskDetail.fxml"));
                
                loader.setController(new AssignedTaskDetailController(task));
                
                loader.load();

                displayBox.getChildren().add((Pane) loader.getRoot());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }
        });
    }

}

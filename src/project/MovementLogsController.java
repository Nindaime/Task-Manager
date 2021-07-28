/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class MovementLogsController implements Initializable, project.ControlledPage {
    PageController myController;

    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }
    
    @FXML
    private void goToLogin(ActionEvent event) {
        myController.setPage(Project.LoginPage);
    }

    @FXML
    private Button closeButton;
    
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
        stage.close();
    }
    
}

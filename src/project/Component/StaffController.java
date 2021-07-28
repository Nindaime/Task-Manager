package project.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import project.AssignTaskController;
import project.Navigation.Navigation;
import project.TaskModel.Person;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class StaffController implements Initializable {

    private Person person;
    @FXML
    private Label username;
    @FXML
    Image userImage;
    @FXML
    private Label name;

    @FXML
    private Label jobTitle;

    @FXML
    private ImageView imageView;

    @FXML
    private Pane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            double width = 34;
            username.setText(person.getUsername());
            name.setText(person.getName());
            jobTitle.setText(person.getJobTitle());
            imageView.setImage(new Image(person.getBlob().getBinaryStream()));

            Circle clip = new Circle(width, width, width);
            imageView.setClip(clip);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML

    public void selectUser() {

        AssignTaskController.setTaskAssignee(person.getUsername());

        Navigation.closeCurrentSubMenu(pane);
        System.out.println("Sub menu has been successfully closed!!!");


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("show assign task");
                Navigation.displaySubMenu(Navigation.AssignTask);
            }
        });

    }

    public StaffController(Person person) {

        this.person = person;

    }

}

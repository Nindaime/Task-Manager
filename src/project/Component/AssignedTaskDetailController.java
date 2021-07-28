package project.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import project.TaskModel.AssignedTask;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class AssignedTaskDetailController implements Initializable {

    private AssignedTask task;
    @FXML
    private Label username;
    @FXML
    Image userImage;
    @FXML
    private Label taskName;
    @FXML
    private Label taskStatus;
    @FXML
    private Label startTime;
    @FXML
    private Label deadline;
    @FXML
    ImageView imageView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            double width = 34;
            username.setText(task.getPerson().getUsername());
            taskName.setText(task.getDetail().getName());
            deadline.setText(task.getDetail().getTime().getFormattedDeadline());
            taskStatus.setText(task.getDetail().getStatus());
            startTime.setText(task.getDetail().getTime().getFormattedStartTime());

            imageView.setImage(new Image(task.getPerson().getBlob().getBinaryStream()));


            Circle clip = new Circle(width, width, width);
            imageView.setClip(clip);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
//            e.printStackTrace();
        }

    }

    public AssignedTaskDetailController(AssignedTask task) {

        this.task = task;

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.Navigation.Navigation;
import project.TaskModel.Person;


/**
 * FXML Controller class
 *
 * @author PETER
 */
public class AssignTaskController implements Initializable, ControlledPage {
    PageController myController;
    Connection connection;

    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }

    private static String selectedUser = "";

    @FXML
    private Button setupMeeting;
    @FXML
    private Button closeButton;

    @FXML
    private ComboBox<Label> taskName;

    @FXML
    private ComboBox<Label> taskAssignee;

    @FXML
    private DatePicker deadlineDay;
    @FXML
    private TextField deadlineTime;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // User should not be able to assign task to his/herself?

    }

    @FXML
    private void onUserSelected(ActionEvent event) {


        try {

            Person person = Person.getPerson(taskAssignee.getValue().getText());
            profileImage.setImage(new Image(person.getBlob().getBinaryStream()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

    public void runSetup() {
        // TODO
        try {

            getTaskAssignee();
            getTaskName();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void submit(ActionEvent event) {

        try {
            String queryString = "insert into tasksasjob set " + "taskName = ? ," + "taskAssignee = ? ,"
                    + "taskDeadline = ? ," + "taskDuration = ?," + "taskAssigner = ?";

            String[] params = new String[5];

            if (taskAssignee.getValue() == null)
                throw new Exception("Task Assignee field cannot be empty");

            if (taskName.getValue() == null)
                throw new Exception("Task Name cannot be empty");

            if (deadlineDay.getValue() == null)
                throw new Exception("Deadline day not selected");

            // TODO
            //compare date with current date for deadline
            // if (deadlineDay.getValue().isBefore(new Date()) )
            //     throw new Exception("Deadline day not selected");

            String[] time = deadlineTime.getText().split(":");

            if (time.length != 2) {
                throw new Exception("Invalid time format");
            }
            int hour = Integer.parseInt(time[0]);
            int min = Integer.parseInt(time[1]);

            if (hour < 0 || hour > 24) {
                throw new Exception("Invalid hour format");
            }

            if (min < 0 || hour > 60) {
                throw new Exception("Invalid min format");
            }

            params[0] = taskName.getValue().getText();
            params[1] = taskAssignee.getValue().getText();
            params[2] = deadlineDay.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            params[3] = deadlineTime.getText();
            params[4] = User.getUsername();

            Database.insertRecordsIntoDB(queryString, params);
            message.setText("Task has been successfully assigned");
            this.closeButton.fire();
        } catch (Exception e) {
            // TODO: handle exception
            message.setText(e.getMessage());
            e.printStackTrace();
        }

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

    private void getTaskAssignee() throws SQLException, ClassNotFoundException {
        String queryString = "select username from staffs";

        ResultSet resultSet = Database.retrieveRecordsFromDB(queryString);

        Ui.assignLabelToComboBox(resultSet, taskAssignee, 250, selectedUser);

    }

    private void getTaskName() throws SQLException, ClassNotFoundException {
        String queryString = "select taskName from tasks";
        ResultSet resultSet = Database.retrieveRecordsFromDB(queryString);
        Ui.assignLabelToComboBox(resultSet, taskName, 250, "");

    }

    public static void setTaskAssignee(String name) {

        selectedUser = name;

    }
}

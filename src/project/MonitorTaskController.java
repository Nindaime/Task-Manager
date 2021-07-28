/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.Component.AssignedTaskDetailController;
import project.TaskModel.*;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class MonitorTaskController implements Initializable, ControlledPage {
    PageController myController;

    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }

    @FXML
    private Button closeButton;
    @FXML
    private Pane Result;
    @FXML
    private Pane Search;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private ComboBox<Label> taskAssignee;
    @FXML
    private VBox displayBox;
    @FXML
    private Label error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            System.out.println("running get tasks assignees");
            getTaskAssignee();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO: handle exception
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

    @FXML
    private void onSubmit(ActionEvent event) {
        ArrayList<AssignedTask> tasks = new ArrayList<>();
        try {

            if (dateFrom.getValue() == null || dateTo.getValue() == null) {
                throw new Exception("Date values can not be empty.");
            }

            if (dateFrom.getValue().compareTo(dateTo.getValue()) > 0) {
                throw new Exception("Date To is less than dateFrom");
            }

            if (taskAssignee.getValue() != null) {

                String queryString = "select staffs.firstname, staffs.lastname,tasks.taskName,taskAssigner,"
                        + "image , staffs.jobTitle, superior,taskDeadline, difficulty,state, " +
                        "startTime, " +
                        "taskDuration, averageTaskDuration, username  from "
                        + "tasksasjob, staffs, tasks where taskAssigner = ? and taskAssignee = ? and "
                        + "timeOfAssignment between ? and ? and staffs.username= taskAssignee and "
                        + "tasks.taskName = tasksasjob.taskName";

                String[] params = new String[4];
                params[0] = User.getUsername();
                params[1] = taskAssignee.getValue().getText();
                params[2] = dateFrom.getValue().toString();
                params[3] = dateTo.getValue().toString();
                ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);

                Integer colorCount = 0;
                while (resultSet.next()) {

                    String firstname = resultSet.getString(1);
                    String lastname = resultSet.getString(2);
                    String taskName = resultSet.getString(3);
                    String taskAssigner = resultSet.getString(4);
                    Blob blob = resultSet.getBlob("image");
                    String jobTitle = resultSet.getString(6);
                    // String superior = resultSet.getString(7);
                    String taskDeadline = resultSet.getString(8);
                    String taskDifficulty = resultSet.getString(9);
                    String state = resultSet.getString(10);
                    String startTime = resultSet.getString(11);
                    String taskDuration = resultSet.getString(12);
                    String averageTaskDuration = resultSet.getString(13);

                    Assigner assigner = new Assigner(taskAssigner, firstname, lastname, blob, jobTitle);
                    TaskTime taskTime = new TaskTime(startTime, taskDeadline, averageTaskDuration, taskDuration);
                    Detail detail = new Detail(taskName, ColorPallete.getColor(colorCount++), taskDifficulty, state, taskTime);


                    tasks.add(new AssignedTask(assigner, detail));
                }
            } else {

                String queryString = "select staffs.firstname, staffs.lastname,tasks.taskName,taskAssigner,"
                        + "image , staffs.jobTitle, superior,taskDeadline, difficulty,state, startTime," +
                        "taskDuration, averageTaskDuration, username  from "
                        + "tasksasjob, staffs, tasks where taskAssigner = ? and timeOfAssignment between "
                        + " ? and ? and staffs.username= taskAssignee and tasks.taskName ="
                        + "tasksasjob.taskName ";
                // String queryString = "select experienceLevel, jobTitle, image from staffs
                // where username = ? ";
                String[] params = new String[3];
                params[0] = User.getUsername();
                params[1] = dateFrom.getValue().toString();
                params[2] = dateTo.getValue().toString();
                ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);

                Integer colorCount = 0;
                while (resultSet.next()) {

                    String firstname = resultSet.getString(1);
                    String lastname = resultSet.getString(2);
                    String taskName = resultSet.getString(3);
                    String taskAssigner = resultSet.getString(4);
                    Blob blob = resultSet.getBlob("image");
                    String jobTitle = resultSet.getString(6);
                    // String superior = resultSet.getString(7);
                    String taskDeadline = resultSet.getString(8);
                    String taskDifficulty = resultSet.getString(9);
                    String state = resultSet.getString(10);
                    String startTime = resultSet.getString(11);
                    String taskDuration = resultSet.getString(12);
                    String averageTaskDuration = resultSet.getString(13);

                    Assignee assignee = new Assignee(taskAssigner, firstname, lastname, blob, jobTitle);
                    TaskTime taskTime = new TaskTime(startTime, taskDeadline, averageTaskDuration, taskDuration);
                    Detail detail = new Detail(taskName, ColorPallete.getColor(colorCount++), taskDifficulty, state, taskTime);


                    tasks.add(new AssignedTask(assignee, detail));
                }
            }

            // return resultSet;

            if (tasks.size() > 0) {
                getAssignedTaskUI(tasks);
            } else {
                error.setText("No data returned.");
                return;
            }

            error.setText("");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            error.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    private void getTaskAssignee() throws SQLException, ClassNotFoundException {
        String queryString = "select username from staffs";

        ResultSet resultSet = Database.retrieveRecordsFromDB(queryString);
        Ui.assignLabelToComboBox(resultSet, taskAssignee, 250, "");
    }

    public void getAssignedTaskUI(ArrayList<AssignedTask> tasks) {
        tasks.forEach(task -> {
            try {
                System.out.println("Setting new tasks: --- ");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./Component/AssignedTaskDetail.fxml"));

                loader.setController(new AssignedTaskDetailController(task));

                loader.load();

                displayBox.getChildren().add((Pane) loader.getRoot());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

}

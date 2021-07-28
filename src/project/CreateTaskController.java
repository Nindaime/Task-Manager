/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class CreateTaskController implements Initializable, ControlledPage {
    PageController myController;
    Connection connection;

    @FXML
    private TextField jobTitle;

    @FXML
    private TextField taskName;

    @FXML
    private ComboBox<Label> taskDifficulty;

    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }

    @FXML
    private Button closeButton;
    @FXML
    private Button setupMeeting;
    @FXML
    private Label error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            connection = Main.ConnectToDB();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            getTaskDiffulty();
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
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
    private void submit(ActionEvent event) {

        try {
            if (taskDifficulty.getValue() == null) {
                throw new Exception("Task Diffulty value must be selected");
            }

            if (taskName.getText().equals("")) {
                throw new Exception("Task Name cannot be empty");
            }

            if (jobTitle.getText().equals("")) {
                throw new Exception("Job Title cannot be empty");
            }

            String queryString = "insert into tasks set " + "taskName = ? ," + "jobTitle = ? ," + "taskOwner = ? ,"
                    + "difficulty = ?";
            PreparedStatement prepStatement = connection.prepareStatement(queryString);

            prepStatement.setString(1, taskName.getText());
            prepStatement.setString(2, jobTitle.getText());
            prepStatement.setString(3, User.getUsername());
            prepStatement.setString(4, taskDifficulty.getValue().getText());

            prepStatement.executeUpdate();

            error.setText("Task created successfully");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            error.setText(e.getMessage());
        }
    }

    private void getTaskDiffulty() throws ClassNotFoundException {

        String[] difficultyLevel = new String[] { "HARD", "NORMAL", "EASY" };

        for (int i = 0; i < difficultyLevel.length; i++) {
            Label result = new Label(difficultyLevel[i]);
            result.setStyle("-fx-background-color: #999; -fx-text-fill: white;");
            result.setPrefSize(320, 30);
            result.setAlignment(Pos.CENTER);
            result.setFont(Font.font("SansSerif", FontWeight.THIN, 15));
            taskDifficulty.getItems().add(result);
        }

    }

}

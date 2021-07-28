/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import project.TaskModel.AssignedTask;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class LoginPageController implements Initializable, ControlledPage {
    PageController myController;

    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }

    @FXML
    private Button closeButton;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    private AnchorPane root;

    @FXML
    private Label warning;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     *
     * @param event
     */
    @FXML
    private void goToRegistration(ActionEvent event) {
        myController.setPage(Project.RegistrationPage);
    }

    PreparedStatement preparedStatement;

    // private ArrayList<Label> labels = new ArrayList<>();

    @FXML
    private void authenticate(ActionEvent event) throws Exception {
        try {
            String queryString = "select username from staffs where username = ? AND password = ?";
            preparedStatement = Main.ConnectToDB().prepareStatement(queryString);
            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, password.getText());

            ResultSet rSet = preparedStatement.executeQuery();

            if (rSet.next()) {
                prepareMainPage(username.getText());
                String qString = "update staffs set available = 'online' where username = ?";
                PreparedStatement prepStatement = Main.ConnectToDB().prepareStatement(qString);
                prepStatement.setString(1, username.getText());
                prepStatement.executeUpdate();
                myController.setPage(Project.MainPage);
            } else {
                warning.setOpacity(0.9);


                new Thread() {
                    @Override
                    public void run() {
                        try {
                            this.sleep(3000);

                            warning.setOpacity(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();

            }

            username.setText("");
            password.setText("");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            warning.setText("Couldn't connect to Database");
        }
    }

    public static void prepareMainPage(String name)
            throws SQLException, ClassNotFoundException, IndexOutOfBoundsException, IOException, ParseException {

        String queryString = "select experienceLevel, jobTitle, image from staffs where username = ? ";
        PreparedStatement preparedStatement = Main.ConnectToDB().prepareStatement(queryString);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        BorderPane mainPageRoot = (BorderPane) PageController.screens.get(Project.MainPage);
        if (resultSet.next()) {
            User.setUsername(name);
            User.setJobTitle(resultSet.getString("jobTitle"));
            User.setBlob(resultSet.getBlob("image"));

            ((Label) mainPageRoot.lookup("#username")).setText(name);
            ((Label) mainPageRoot.lookup("#xp")).setText(resultSet.getString("experienceLevel"));
            ((Label) mainPageRoot.lookup("#jobTitle")).setText(resultSet.getString("jobTitle"));
            ((Label) mainPageRoot.lookup("#timerLabel")).setText("00:00");
            ((Rectangle)mainPageRoot.lookup("#timerAnimation")).setWidth(0);
            ((ImageView) mainPageRoot.lookup("#userImage"))
                    .setImage(new Image(resultSet.getBlob("image").getBinaryStream()));


            ScrollPane scrollPane = ((ScrollPane) mainPageRoot.lookup("#scrollPane"));

            TaskList tkList = new TaskList();
            FlowPane taskList = ((FlowPane) mainPageRoot.lookup("#taskList"));

            taskList.getChildren().clear();
            taskList.getChildren().addAll(tkList.getTaskList().getChildren());


            ArrayList<AssignedTask> assignedTasks = User.getAssignedTask();
            taskList.setPrefWidth(68 * (assignedTasks.size() + 1));


            assignedTasks.forEach(task -> {


                FXMLLoader loader = new FXMLLoader(LoginPageController.class.getResource("TaskLabel.fxml"));

                loader.setController(new TaskLabelController(task));
                try {
                    loader.load();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Label taskLabel = (Label) loader.getRoot();

                taskLabel.setStyle("-fx-background-color:" + task.getDetail().getColor() + ";");
                Tooltip tooltip = new Tooltip(task.getDetail().getName());
                taskLabel.setText(task.getDetail().getShortName());

                taskLabel.setTooltip(tooltip);

                taskList.getChildren().add(taskLabel);

            });

            scrollPane.setContent(taskList);
            scrollPane.setPannable(true);

        }

    }

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    double yOffset = 0;
    double xOffset = 0;

    @FXML
    private void determine(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    @FXML
    private void pick(MouseEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();
        scene.setCursor(Cursor.CLOSED_HAND);
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    private void drop(MouseEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setCursor(Cursor.HAND);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}

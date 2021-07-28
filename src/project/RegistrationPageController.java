/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.sql.*;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class RegistrationPageController implements Initializable, ControlledPage {
    PageController myController;
    Connection connection;
    Constant constant;

    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }

    @FXML
    private Button back;

    @FXML
    private Button register;

    final FileChooser fileChooser = new FileChooser();
    File file;

    @FXML
    private void chooseImage(ActionEvent event) throws FileNotFoundException, IOException, URISyntaxException {
        configureFileChooser(fileChooser);
        file = fileChooser.showOpenDialog((Stage) ((Button) event.getSource()).getScene().getWindow());
        System.out.println(file.getCanonicalPath());

        if (file.isFile())
            userImage.setImage(new Image(new FileInputStream(file.getCanonicalFile())));
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
    }

    @FXML
    private TextField surname;

    @FXML
    private TextField firstname;

    @FXML
    private ComboBox<Label> superior;

    @FXML
    private TextField username;

    @FXML
    private TextField securityAnswer;

    @FXML
    private PasswordField password;

    @FXML
    private TextArea jobDescription;

    @FXML
    private ImageView userImage;

    @FXML
    private ImageView userImageRequired;

    @FXML
    private ImageView firstnameRequired;

    @FXML
    private ImageView usernameRequired;

    @FXML
    private ImageView passwordRequired;

    @FXML
    private ImageView superiorRequired;

    @FXML
    private ImageView surnameRequired;

    @FXML
    private ImageView jobTitleRequired;

    @FXML
    private ImageView securityAnswerRequired;

    @FXML
    private void registerUser(ActionEvent event) throws SQLException, FileNotFoundException, ClassNotFoundException {
        DropShadow effect = new DropShadow(BlurType.THREE_PASS_BOX, Color.RED, 7, 0.2, 0, 0);
        effect.setHeight(15);
        effect.setWidth(15);
        boolean canRegister;
        if (firstname.getText().isEmpty()) {
            firstnameRequired.setOpacity(1);
            firstname.setEffect(effect);
            canRegister = false;
        } else {
            firstnameRequired.setOpacity(0);
            firstname.setEffect(null);
            canRegister = true;
        }
        // if(superior.getValue() == null){
        // superiorRequired.setOpacity(1);
        // superior.setEffect(effect);
        // canRegister = false;
        // }else{
        // superiorRequired.setOpacity(0);
        // superior.setEffect(null);
        // canRegister = true;
        // }
        if (surname.getText().isEmpty()) {
            surnameRequired.setOpacity(1);
            surname.setEffect(effect);
            canRegister = false;
        } else {
            surnameRequired.setOpacity(0);
            surname.setEffect(null);
            canRegister = true;
        }
        if (username.getText().isEmpty()) {
            usernameRequired.setOpacity(1);
            username.setEffect(effect);
            canRegister = false;
        } else {
            usernameRequired.setOpacity(0);
            username.setEffect(null);
            canRegister = true;
        }
        if (password.getText().isEmpty()) {
            passwordRequired.setOpacity(1);
            password.setEffect(effect);
            canRegister = false;
        } else {
            passwordRequired.setOpacity(0);
            password.setEffect(null);
            canRegister = true;
        }
        if (securityAnswer.getText().isEmpty()) {
            securityAnswerRequired.setOpacity(1);
            securityAnswer.setEffect(effect);
            canRegister = false;
        } else {
            securityAnswerRequired.setOpacity(0);
            securityAnswer.setEffect(null);
            canRegister = true;
        }
        if (jobTitle.getValue() == null) {
            jobTitleRequired.setOpacity(1);
            jobTitle.setEffect(effect);
            canRegister = false;
        } else {
            jobTitleRequired.setOpacity(0);
            jobTitle.setEffect(null);
            canRegister = true;
        }
        if (userImage.getImage() == null) {
            userImageRequired.setOpacity(1);
            userImage.setEffect(effect);
            canRegister = false;
        } else {
            userImageRequired.setOpacity(0);
            userImage.setEffect(null);
            canRegister = true;
        }

        if (canRegister) {
            String queryString = "insert into staffs set " + "firstname = ? ," + "lastname = ? ," + "username = ? ,"
                    + "password = ? ," + "superior = ?," + "image = ?," + "jobtitle = ?," + "securityAnswer = ?,"
                    + "securityQuestion = ?," + "available = ?";
            PreparedStatement prepStatement = connection.prepareStatement(queryString);
            prepStatement.setString(1, firstname.getText());
            prepStatement.setString(2, surname.getText());
            prepStatement.setString(3, username.getText());
            prepStatement.setString(4, password.getText());
            if (superior.getValue() == null)
                prepStatement.setString(5, "");
            else
                prepStatement.setString(5, superior.getValue().getText());
            prepStatement.setBlob(6, new FileInputStream(file));
            prepStatement.setString(7, jobTitle.getValue().getText());
            prepStatement.setString(8, securityAnswer.getText());
            System.out.println("leng : "+securityQuestion.getEditor().getText());
            if (securityQuestion.getEditor().getText().equals(""))
                prepStatement.setString(9, securityQuestion.getValue().getText());
            else
                prepStatement.setString(9, securityQuestion.getEditor().getText());
                // prepStatement.setString(9, securityQuestion.getEditor().getText().substring(32));
            prepStatement.setString(10, "online");

            prepStatement.executeUpdate();
            prepareMainPage(username.getText());
            myController.setPage(Project.MainPage);
        }
    }

    public void prepareMainPage(String name) throws SQLException, ClassNotFoundException {
        User.setUsername(name);
        PreparedStatement preparedStatement;
        String queryString = "select experienceLevel, jobTitle, image from staffs where username = ? ";
        preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        BorderPane mainPageRoot = (BorderPane) PageController.screens.get(Project.MainPage);
        if (resultSet.next()) {
            ((Label) mainPageRoot.lookup("#username")).setText(name);
            ((Label) mainPageRoot.lookup("#xp")).setText(resultSet.getString("experienceLevel"));
            ((Label) mainPageRoot.lookup("#jobTitle")).setText(resultSet.getString("jobTitle"));

            ((ImageView) mainPageRoot.lookup("#userImage"))
                    .setImage(new Image(resultSet.getBlob("image").getBinaryStream()));
        }
    }

    @FXML
    private Button closeButton;

    @FXML
    private void goToLogin(ActionEvent event) {
        myController.setPage(Project.LoginPage);
        userImageRequired.setOpacity(0);
        jobTitleRequired.setOpacity(0);
        securityAnswerRequired.setOpacity(0);
        passwordRequired.setOpacity(0);
        usernameRequired.setOpacity(0);
        surnameRequired.setOpacity(0);
        firstnameRequired.setOpacity(0);
        superiorRequired.setOpacity(0);
        username.setText(null);
        password.setText(null);
        firstname.setText(null);
        superior.setValue(null);
        surname.setText(null);
        securityAnswer.setText(null);
        jobDescription.setText(null);
        jobTitle.setValue(null);
        securityQuestion.setValue(null);
        if (userImage.getImage() != null)
            userImage.setImage(new Image(getClass().getResourceAsStream("../img/placeholder-user-photo.png")));
    }

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private ComboBox<Label> jobTitle;

    private void getJobs() throws SQLException, ClassNotFoundException {
        String queryString = "select jobtitle from jobs";
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(queryString);
        while (resultSet.next()) {
            Label result = new Label(resultSet.getString(1));
            result.setStyle("-fx-background-color: #999; -fx-text-fill: white;");
            result.setPrefSize(430, 30);
            result.setAlignment(Pos.CENTER);
            result.setFont(Font.font("SansSerif", FontWeight.THIN, 15));
            jobTitle.getItems().add(result);
        }
    }

    @FXML
    private void selectJobTitle(ActionEvent event) throws SQLException, ClassNotFoundException {
        displayJobDescription(jobTitle.getValue().getText());
    }

    private void displayJobDescription(String jobT) throws SQLException, ClassNotFoundException {
        String queryString = "select jobDescription from jobs where jobTitle = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setString(1, jobT);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            jobDescription.setText(resultSet.getString("jobDescription"));
    }

    @FXML
    private ComboBox<Label> securityQuestion;

    private void getQuestions() throws SQLException, ClassNotFoundException {
        String queryString = "select distinct securityQuestion from staffs";
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(queryString);
        while (resultSet.next()) {
            Label question = new Label(resultSet.getString(1));
            question.setStyle("-fx-background-color: #999; -fx-text-fill: white;");
            question.setPrefSize(440, 30);
            question.setAlignment(Pos.CENTER);
            question.setFont(Font.font("SansSerif", FontWeight.THIN, 15));
            securityQuestion.getItems().add(question);
        }
    }

    private void getSuperiors() throws SQLException, ClassNotFoundException {
        String queryString = "select username from staffs";
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(queryString);
        while (resultSet.next()) {
            Label s = new Label(resultSet.getString(1));
            s.setAlignment(Pos.CENTER);
            s.setStyle("-fx-background-color: #999; -fx-text-fill: white;");
            s.setFont(Font.font("SansSerif", FontWeight.THIN, 14));
            s.setPrefSize(110, 30);
            superior.getItems().add(s);
        }
    }

    @FXML
    private void hover(DragEvent event) {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        jobTitle.setVisibleRowCount(5);
        superior.setVisibleRowCount(5);
        try {
            connection = Main.ConnectToDB();
            getSuperiors();
            getJobs();
            getQuestions();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

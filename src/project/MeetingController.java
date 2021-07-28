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
import java.sql.*;
import java.time.LocalTime;
import java.util.Scanner;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class MeetingController implements Initializable, ControlledPage {
    PageController myController;
    Connection connection;
    
    @FXML
    private Button setupMeeting;
    
    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }
    
    @FXML
    private Button closeButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            connection = Main.ConnectToDB();
             initializeMeeting();
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    private void initializeMeeting() throws SQLException{
        String queryString = "select * from meetings";
        PreparedStatement statement = connection.prepareStatement(queryString);
        ResultSet results = statement.executeQuery();
        
        while(results.next()){
            meetingType.getItems().add(results.getString(1).toUpperCase());
        }
    }
    
    @FXML
    private void submit(ActionEvent event) throws SQLException{
        BorderPane mainPageRoot = (BorderPane)PageController.screens.get(Project.MainPage);
        String queryString = "insert into meeting set "
                + "meetingcreator = ?, "
                + "type = ?, "
                + "status = ?,"
                + "attendees = ?,"
                + "assignedduration = ?,"
                + "starttime = ?,"
                + "topic = ?";
        
        PreparedStatement statement = connection.prepareStatement(queryString);
        String username = ((Label)mainPageRoot.lookup("#username")).getText();
        statement.setString(1, username);
        statement.setString(2, meetingType.getValue());
        statement.setString(3, "UNDONE");
        String[] durationTime = meetingDuration.getText().split(":");
        statement.setTime(5, Time.valueOf(LocalTime.of(Integer.parseInt(durationTime[0]), 
                Integer.parseInt(durationTime[1]), Integer.parseInt(durationTime[2]))));
        String[] date = timeOfDay.getText().split(":");
        statement.setTimestamp(6, Timestamp.valueOf(meetingTime.getValue().atTime(Integer.parseInt(date[0]), Integer.parseInt(date[1]))));
        statement.setString(7, meetingTopic.getText());
        
        if(attendees.getText().contains(";")){
            Scanner scanner = new Scanner(attendees.getText());
            scanner.useDelimiter(";");
            while(scanner.hasNext()){
                statement.setString(4, scanner.next());

                statement.executeUpdate();
                System.out.println("Meeting setup for multiple users");
            }
        
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }else{
            statement.setString(4, attendees.getText());
            statement.executeUpdate();

            System.out.println("Meeting setup for single users");

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    private ComboBox<String> meetingType;
    @FXML
    private TextField attendees;
    @FXML
    private TextField timeOfDay;
    @FXML
    private TextField meetingDuration;
    @FXML
    private TextField meetingTopic;
    @FXML
    private DatePicker meetingTime;

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

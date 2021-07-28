/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.StringReader;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.Navigation.Navigation;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class SendMessagesController implements Initializable, project.ControlledPage {
    PageController myController;
    Connection connection;
    
    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }
    PreparedStatement prepStatement;
    
    @FXML
    private TextField receiverID;
    
    @FXML
    private TextArea content;
    
    @FXML
    private Button closeButton;

    @FXML
    private Button btnSend;
    
    @FXML
    private void sendMessage(ActionEvent event) throws SQLException{
        String queryString = "insert into messages SET sender = ?, receiver = ?, content = ?, status = 'UNREAD'";
        prepStatement = connection.prepareStatement(queryString);
        String username = ((Label)((Group)((Stage)((Stage)((Button)event.getSource())
                .getScene().getWindow()).getOwner()).getScene().getRoot()).lookup("#username")).getText();
        
        prepStatement.setString(1, username);
        prepStatement.setString(2, receiverID.getText());
        prepStatement.setCharacterStream(3, new StringReader(content.getText()));
        prepStatement.executeUpdate();
        
        closeButtonAction(event);
    }
    
    @FXML
    private void viewMessages(ActionEvent event){
        myController.setPage(Navigation.ViewMessage);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            connection = Main.ConnectToDB();
        }catch(SQLException | ClassNotFoundException ex){
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
    
}

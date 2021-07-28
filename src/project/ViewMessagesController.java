/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.Navigation.Navigation;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class ViewMessagesController implements Initializable, ControlledPage {
    PageController myController;
    Connection connection;

    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }

    @FXML
    private Button closeButton;

    @FXML
    private VBox messageContent;

    @FXML
    private void backToSend(ActionEvent event) {
        myController.setPage(Navigation.SendMessage);
        messageContent.getChildren().clear();
        loaded = false;
    }

    ArrayList<String> contents = new ArrayList<>();
    ArrayList<String> contentSub = new ArrayList<>();
    public static boolean loaded;

    @FXML
    private void initializeMessage(MouseEvent event) throws ClassNotFoundException, SQLException {
        messageContent.getChildren().clear();

        if (!loaded) {
            connection = Main.ConnectToDB();
            String queryString = "select image, username, timesent, content from staffs, messages "
                    + "where messages.receiver = ? and username = messages.sender";

            BorderPane mainPageRoot = (BorderPane) PageController.screens.get(Project.MainPage);
            String username = ((Label) mainPageRoot.lookup("#username")).getText();
            PreparedStatement prepStatement = connection.prepareStatement(queryString);
            prepStatement.setString(1, username);

            ResultSet resultSet = prepStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                AnchorPane content = new AnchorPane();
                Label sender = new Label(resultSet.getString("username"));
                Label msgTime = new Label(resultSet.getTimestamp("timesent").toLocalDateTime().toString());
                contents.add(resultSet.getClob("content").getSubString(1, (int) resultSet.getClob("content").length()));
                contentSub.add(
                        contents.get(i).substring(0, (contents.get(i).length() < 40 ? contents.get(i).length() : 40))
                                + "...");
                Text text = new Text(55, 55, contentSub.get(i));
                text.setWrappingWidth(300);
                text.setFont(Font.font("Lato light", 14));
                msgTime.setFont(Font.font("Lato", FontWeight.BOLD, 14));
                sender.setFont(Font.font("Lato", FontWeight.BOLD, 14));
                sender.setLayoutX(55);
                sender.setLayoutY(5);
                msgTime.setLayoutX(55);
                msgTime.setLayoutY(23);
                ImageView senderImage = new ImageView(new Image(resultSet.getBlob("image").getBinaryStream()));
                content.getChildren().addAll(senderImage, sender, msgTime, text);
                text.setStyle("-fx-text-alignment: justify");
                senderImage.setFitHeight(55);
                senderImage.setFitWidth(45);
                senderImage.setPreserveRatio(true);
                senderImage.setLayoutX(5);
                senderImage.setLayoutY(8);
                content.setStyle("-fx-border-color: #666");
                content.setPrefSize(394, 60);
                content.getStyleClass().add("message-content");
                messageContent.getChildren().add(content);

                content.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        System.out.println("Index of anchor child: "
                                + contents.get(messageContent.getChildren().indexOf(((AnchorPane) e.getSource()))));
                        ((Text) ((AnchorPane) e.getSource()).getChildren().get(3)).setText("");
                        ((Text) ((AnchorPane) e.getSource()).getChildren().get(3)).setText(
                                contents.get(messageContent.getChildren().indexOf(((AnchorPane) e.getSource()))));
                        try {
                            messageRead(((Label) ((AnchorPane) e.getSource()).getChildren().get(2)).getText());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        ((Text) ((AnchorPane) e.getSource()).getChildren().get(3)).setText("");
                        ((Text) ((AnchorPane) e.getSource()).getChildren().get(3)).setText(
                                contentSub.get(messageContent.getChildren().indexOf(((AnchorPane) e.getSource()))));
                    }
                });

                i++;
            }
            loaded = true;
        }
    }

    private void messageRead(String tStamp) throws SQLException {
        String queryString = "update messages set status = 'READ' where timesent = ?";
        PreparedStatement statement = connection.prepareCall(queryString);
        statement.setString(1, tStamp);
        statement.executeUpdate();
    }

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
        messageContent.getChildren().clear();
        stage.close();
    }

}

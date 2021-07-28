/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.stage.Stage;
/**
 *
 * @author PETER
 */
public class Project extends Application {

    public static String LoginPage = "LoginPage"; 
    public static String LoginPage_FXML = "LoginPage.fxml"; 
    public static String RegistrationPage = "RegistrationPage"; 
    public static String RegistrationPage_FXML = "RegistrationPage.fxml"; 
    public static String MainPage = "MainPage"; 
    public static String MainPage_FXML = "MainPage.fxml"; 
    public static String ViewStaffs = "ViewStaffs"; 
    public static String ViewStaffs_FXML = "ViewStaffs.fxml"; 
       
    public static PageController mainContainer;
    
    public static Stage window;
    
    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        mainContainer = new PageController();
        mainContainer.loadPage(Project.LoginPage, Project.LoginPage_FXML);
        mainContainer.loadPage(Project.RegistrationPage, Project.RegistrationPage_FXML);
        mainContainer.loadPage(Project.MainPage, Project.MainPage_FXML);
        mainContainer.loadPage(Project.ViewStaffs, Project.ViewStaffs_FXML);

        mainContainer.setPage(LoginPage);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("DropdownList.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        
    }
    
    public static void main(String[] args){
        launch(args);
    }
}

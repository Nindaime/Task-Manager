/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
/**
 *
 * @author PETER
 */
public class TaskList{
    private static final FlowPane taskList = new FlowPane();
    public static int maxPriority;
    public static ArrayList<Task> fullTaskList = new ArrayList<>();
    public static ArrayList<Duration> taskDurations = new ArrayList<>();
    Connection connection;
    PreparedStatement statement;
    
    public TaskList() throws SQLException, ClassNotFoundException{
        
        connection = Main.ConnectToDB();
        
        BorderPane mainPageRoot = (BorderPane) PageController.screens.get(Project.MainPage);
        ((Label) mainPageRoot.lookup("#username")).getText();
        
//        taskList.getChildren().addListener(new ListChangeListener(){
//            
//            @Override
//            public void onChanged(ListChangeListener.Change e){
//                setTaskList();
//            }
//        });
        
        setTaskList();
    }
    
    public FlowPane getTaskList(){
        return taskList;
    }
    
    public final void setTaskList(){
        try{
//            String queryString = "select taskname, taskAssignee, taskAssiger, TimeOfAssignment, State, taskDeadline, taskDuration from taskasjob where taskassignee = ? and not State = 'FINISHED' and not State = 'CANCELLED'";
//            statement = connection.prepareStatement(queryString);
//            statement.setString(1, owner);
//            ResultSet result = statement.executeQuery();
//            while(result.next()){
//                Task task = new Task(result.getString("taskname"), result.getString("taskAssigner"), 
//                        result.getString("timeOfAssignment"), result.getString("taskDeadline"));
////                taskList.getChildren().add(task);
////                taskDurations.add(getDuration(result.getString("taskDuration")));
//            }

            String[][] values = {{"Prepare Document", "Peter", "08:30:00", "08:40:00"}, 
                {"Prepare Document", "Peter", "08:31:00", "08:50:00"}, {"Prepare Document", "Peter", "08:32:00", "09:00:00"},
            {"Prepare Document", "Peter", "08:34:00", "09:10:00"}, {"Prepare Document", "Peter", "08:35:00", "09:20:00"}};
            
            for(String[] s: values){
                Task task = new Task(s[0], s[1], s[2], s[3], taskList);
                taskList.getChildren().add(task);
//                taskDurations.add(getDuration("00:30:00"));
            }
        }catch(SQLException | ClassNotFoundException ex){
        }
        
    }
    
}

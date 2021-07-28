/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
/**
 *
 * @author PETER
 */
public class Task extends Label{
    private String taskName = "";
    private static String[] backgroundColors = {"#FFC40D", "#08C", "#EB3C00", "#00A300", "#9F00A7", "#222"};
    final ContextMenu menu = new ContextMenu();
    private final String myBackgroundColor;
    public Task() {
        this.myBackgroundColor = null;
    }
    
    Task(String taskName, String taskAssigner, String timeOfAssignment, String deadLine, FlowPane taskList) throws SQLException, ClassNotFoundException{
        String[] textValue = taskName.split(taskName);
        myBackgroundColor = backgroundColors[(int)(Math.random() * 5)];
        for(String s: textValue)
            this.taskName += s.charAt(0)+"".toUpperCase();
        
        setText(this.taskName);
        setCursor(Cursor.HAND);
        setBackground(new Background(new BackgroundFill(Color.web(myBackgroundColor), new CornerRadii(0), new Insets(0))));
        setTooltip(new Tooltip(taskName+" from: "+taskAssigner+" @"+timeOfAssignment+"->"+deadLine));
        setTextFill(Color.WHITE);
        InnerShadow shadow = new InnerShadow(BlurType.THREE_PASS_BOX, Color.WHITE, 3.5, 0, 0, 0);
        shadow.setHeight(8);
        shadow.setWidth(8);
        setEffect(shadow);
        setWidth(68);
        setHeight(20);
        setAlignment(Pos.CENTER);
        System.out.println("Built basic");
        
        MenuItem execTask = new MenuItem("Execute Task");
        MenuItem susTask = new MenuItem("Suspend Task");
        MenuItem canTask = new MenuItem("Cancel Task");
        menu.getItems().addAll(execTask, susTask, canTask);
        setContextMenu(menu);
        
        DoubleProperty width = prefWidthProperty();
        execTask.setOnAction(e ->{
            Timeline tween = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(width, this.getPrefWidth())), 
                    new KeyFrame(Duration.seconds(1), new KeyValue(width, 0, Interpolator.EASE_OUT)));
            tween.play();
            taskList.getChildren().remove(this);
        });
        
        ObjectProperty<Background> background = backgroundProperty();
        susTask.setOnAction(e ->{
            Timeline tween = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(width, this.getPrefWidth())), 
                    new KeyFrame(Duration.seconds(1), new KeyValue(width, 0, Interpolator.EASE_BOTH)));
            Timeline warningTween = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(background, this.getBackground())), 
                    new KeyFrame(Duration.millis(200), new KeyValue(background, new Background(new BackgroundFill(Color.web("#FFC40D"), new CornerRadii(0), new Insets(0))), Interpolator.EASE_OUT)));
            warningTween.setCycleCount(5);
            ParallelTransition animation = new ParallelTransition(tween, warningTween);
            animation.play();
            taskList.getChildren().remove(this);
        });
        
        canTask.setOnAction(e ->{
            Timeline tween = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(width, this.getPrefWidth())), 
                    new KeyFrame(Duration.seconds(1), new KeyValue(width, 0, Interpolator.EASE_BOTH)));
            Timeline warningTween = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(background, this.getBackground())), 
                    new KeyFrame(Duration.millis(200), new KeyValue(background, new Background(new BackgroundFill(Color.web(myBackgroundColor), new CornerRadii(0), new Insets(0))), Interpolator.EASE_OUT)));
            warningTween.setCycleCount(5);
            ParallelTransition animation = new ParallelTransition(tween, warningTween);
            animation.play();
            taskList.getChildren().remove(this);
        });
        
//        TaskList.fullTaskList.add(this);
//        if(getPriority() < TaskList.maxPriority)
//        taskList.getChildren().add(this);
        System.out.println("Built task object");
    }
    
    public Task createTask(){
        return this;
    }
    
    Connection connection;
    PreparedStatement statement;
}

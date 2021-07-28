/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import project.Navigation.Navigation;
import project.TaskModel.AssignedTask;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class MainPageController implements Initializable, ControlledPage {
    PageController myController;
    PreparedStatement statement;
    private static AssignedTask selectedTask;
    private static boolean istaskChanged  = false;
    private static long averageTaskDuration;
    private static long taskDuration;
    private static long usedTime;
    private static double timerAnimationDuration;
    private static Timeline animation;
    private DoubleProperty tAWidth;
    private StringProperty timerText;


    @Override
    public void setPageParent(PageController pageParent) {
        myController = pageParent;
    }

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label date;

    @FXML
    private Button btnMessage;

    @FXML
    private Button btnMeeting;

    @FXML
    private Label logout;

    @FXML
    private Label username;

    @FXML
    private Slider slider;

    @FXML
    private void showMessageWindow(ActionEvent event) {
        navigation.displaySubMenu(Navigation.SendMessage);
    }

    @FXML
    private void showMeetingWindow(ActionEvent event) {
        navigation.displaySubMenu(Navigation.Meeting);
    }

    @FXML
    private void showAssignTaskWindow(ActionEvent event) {
        AssignTaskController.setTaskAssignee("");
        navigation.displaySubMenu(Navigation.AssignTask);
    }

    @FXML
    private void showViewAssignedTaskWindow(ActionEvent event) {
        navigation.displaySubMenu(Navigation.ViewAssignedTask);
    }

    @FXML
    private void showCreateTaskWindow(ActionEvent event) {
        navigation.displaySubMenu(Navigation.CreateTask);
    }

    @FXML
    private void showMonitorTaskWindow(ActionEvent event) {
        navigation.displaySubMenu(Navigation.MonitorTask);
    }

    @FXML
    private void showStaffsWindow(ActionEvent event) {
        navigation.displaySubMenu(Navigation.ViewStaffs);
    }

    @FXML
    private void showSubordinatesWindow(ActionEvent event) {
        navigation.displaySubMenu(Navigation.ViewSubordinates);
    }

    @FXML
    private void logout(MouseEvent event) throws SQLException {
        // if((time/3600000) % 24 <= 12)
        // else
        // displaySubMenu(MovementLog, anchorX, anchorY);
        String queryString = "update staffs set available = 'offline' where username = ?";
        PreparedStatement prepStatement = connection.prepareStatement(queryString);
        prepStatement.setString(1, username.getText());
        prepStatement.executeUpdate();
        resetTimer();
        myController.setPage(Project.LoginPage);
    }

    Connection connection;


    public Navigation navigation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            timer();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double ratio = (scrollPane.getHmax() / slider.getMax());
        scrollPane.hvalueProperty().bind(slider.valueProperty().multiply(ratio));

        navigation = new Navigation();

        Calendar currentDateAndTime = new GregorianCalendar();
        date.setText(currentDateAndTime.get(Calendar.DAY_OF_MONTH) + ", "
                + currentDateAndTime.getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, Locale.ENGLISH) + " "
                + currentDateAndTime.get(Calendar.YEAR));

        Timeline clockAnimation = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent e) -> {
            currentDateAndTime.setTime(new Date(System.currentTimeMillis()));
            paintClock(currentDateAndTime.get(Calendar.HOUR_OF_DAY), currentDateAndTime.get(Calendar.MINUTE),
                    currentDateAndTime.get(Calendar.SECOND));
        }));
        clockAnimation.setCycleCount(Timeline.INDEFINITE);
        clockAnimation.play();

        try {
            connection = Main.ConnectToDB();
        } catch (SQLException | ClassNotFoundException ex) {
        }

    }

    @FXML
    private Line secondHand;

    @FXML
    private Line minuteHand;

    @FXML
    private Line hourHand;

    @FXML
    private Circle clockKnob;

    public void paintClock(int hour, int minute, int second) {

//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                timer(timerDuration);
//            }
//        });

        double centerX = clockKnob.getCenterX();
        double centerY = clockKnob.getCenterY();

        // Draw secondHand
        double sLength = 90;
        double secondX = centerX + sLength * Math.sin(second * (2 * Math.PI / 60));
        double secondY = centerY - sLength * Math.cos(second * (2 * Math.PI / 60));
        secondHand.setEndX(secondX);
        secondHand.setEndY(secondY);

        // Draw minuteHand
        double mLength = 70;
        double minuteX = centerX + mLength * Math.sin(minute * (2 * Math.PI / 60));
        double minuteY = centerY - mLength * Math.cos(minute * (2 * Math.PI / 60));
        minuteHand.setEndX(minuteX);
        minuteHand.setEndY(minuteY);

        // Draw hourHand
        double hLength = 50;
        double time = (hour % 12 + minute / 60.0) * (2 * Math.PI / 12);
        double hourX = centerX + hLength * Math.sin(time);
        double hourY = centerY - hLength * Math.cos(time);
        hourHand.setEndX(hourX);
        hourHand.setEndY(hourY);


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

    @FXML
    private FlowPane taskList;

    @FXML
    private Pane centerWindow;

    @FXML
    private Label timerLabel;

    @FXML
    private Rectangle timerAnimation;

    private long timerDuration;

    private void timer() throws ParseException {

        System.out.println("Task Duration: "+taskDuration);
//        timerDuration = selectedTask.getDetail().getTime().getFormattedEstimatedTimeInMS();
        timerAnimation.setWidth(0);
        tAWidth = timerAnimation.widthProperty();
        System.out.println("Duration: "+ timerAnimationDuration +" seconds");
//        Timeline backgroundAnimation = new Timeline(
//                new KeyFrame(Duration.seconds(timerAnimationDuration), new KeyValue(tAWidth, tAWidth.get() + 1,
//                        Interpolator.EASE_OUT)));

        System.out.println("Rectangle increases every "+timerDuration/100+" seconds");
        timerText = timerLabel.textProperty();


        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (selectedTask == null || !selectedTask.isRunning())
                    return;

                if(istaskChanged)
                    timerDuration = 0;

                if (timerDuration < averageTaskDuration / 60.0) {
                    tAWidth.setValue( tAWidth.get() + timerAnimationDuration);
                } else
                    tAWidth.setValue(100);

                String time = getCurrentTimerTime();
                timerText.setValue(time);

                istaskChanged = false;
            }
        });

        animation.getKeyFrames().addAll(keyFrame);
        animation.play();

    }

    private void resetTimer(){
        timerDuration = 0;
        timerText.set("00:00");
        animation.stop();
        tAWidth.setValue(0);
        try{
            if(selectedTask != null)
                selectedTask.suspendTask();
        }catch(ClassNotFoundException | SQLException e){}
    }

    private String getCurrentTimerTime() {

        timerDuration += 1000;

        int minute = (int) (timerDuration / 60000);
        int second = (int) ((timerDuration - minute * 60000) / 1000);
        usedTime = timerDuration;
        return String.format("%02d:%02d", minute, second);
    }

    public static void setSelectedTask(AssignedTask task) {

        istaskChanged = true;

        selectedTask = task;

        try {
            averageTaskDuration = selectedTask.getDetail().getTime().getFormattedEstimatedTimeInMS();
            taskDuration = selectedTask.getDetail().getTime().getFormattedTaskDurationInMS();
            timerAnimationDuration = 100 / (averageTaskDuration / 60000.0 );
            System.out.println("The selected Task '"+selectedTask.getDetail().getName()+"' has an estimated time of execution as: "+(averageTaskDuration / 60000.0 )+" seconds");
            if(animation.getStatus() != Animation.Status.RUNNING)
                animation.play();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void updateTaskDuration() {

        int seconds = (int)Math.floor((usedTime / 1000) % 60);
        int minutes = (int)Math.floor((usedTime / (1000 * 60)) % 60);
        int hours = (int)Math.floor((usedTime / (1000 * 60 * 60)) % 24);
        selectedTask.getDetail().getTime().setTaskDuration(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }





}

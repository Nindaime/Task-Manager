/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author PETER
 */
public class ClockController implements Initializable {
    @FXML
    private Line hour5;
    @FXML
    private Line hour11;
    @FXML
    private Line hour8;
    @FXML
    private Line hour7;
    @FXML
    private Line hour10;
    @FXML
    private Line hour1;
    @FXML
    private Line hour4;
    @FXML
    private Line hour2;
    @FXML
    private ImageView hourTens;
    @FXML
    private ImageView hourUnits;
    @FXML
    private Pane tickerContainer;
    @FXML
    private Text ticker;
    @FXML
    private ImageView minuteTens;
    @FXML
    private ImageView minuteUnits;
    @FXML
    private Line minuteHand;
    @FXML
    private Line hourHand;
    @FXML
    private Line secondHand;
    @FXML
    private Circle clockKnob;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Calendar currentDateAndTime = new GregorianCalendar();
        Timeline clockAnimation = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent e) -> {
            currentDateAndTime.setTime(new Date(System.currentTimeMillis()));
            paintClock(currentDateAndTime.get(Calendar.HOUR_OF_DAY), currentDateAndTime.get(Calendar.MINUTE), currentDateAndTime.get(Calendar.SECOND));
        }));
        clockAnimation.setCycleCount(Timeline.INDEFINITE);
        clockAnimation.play();
    }    
   
    public void paintClock(int hour, int minute, int second){
        double centerX = clockKnob.getCenterX();
        double centerY = clockKnob.getCenterY();
        
        //Draw secondHand
        double sLength = 90;
        double secondX = centerX + sLength * Math.sin(second * (2 * Math.PI / 60));
        double secondY = centerY - sLength * Math.cos(second * (2 * Math.PI / 60));
        secondHand.setEndX(secondX);
        secondHand.setEndY(secondY);
                
        //Draw minuteHand
        double mLength = 70;
        double minuteX = centerX + mLength * Math.sin(minute * (2 * Math.PI / 60));
        double minuteY = centerY - mLength * Math.cos(minute * (2 * Math.PI / 60));
        minuteHand.setEndX(minuteX);
        minuteHand.setEndY(minuteY);
        
        //Draw hourHand
        double hLength = 50;
        double hourX = centerX + hLength * Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
        double hourY = centerY - hLength * Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
        hourHand.setEndX(hourX);
        hourHand.setEndY(hourY);
    }
        
}

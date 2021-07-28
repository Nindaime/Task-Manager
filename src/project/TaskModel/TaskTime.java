package project.TaskModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTime {


    private String deadline;
    private String startTime;
    private String estimatedTaskDuration;
    private String taskDuration;


    public TaskTime(String startTime, String deadline, String averageTaskDuration, String taskDuration) {

        this.deadline = deadline;
        this.startTime = startTime;
        this.estimatedTaskDuration = averageTaskDuration;
        this.taskDuration = taskDuration;

    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getFormattedDeadline() throws ParseException {

        // TODO
        // these formatting throws exception

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Date date = dateFormat.parse(deadline);
        String value = (new SimpleDateFormat("dd/MM HH:mm")).format(date);
        return value;
    }

    public String getFormattedStartTime() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Date date = dateFormat.parse(startTime);
        String value = (new SimpleDateFormat("dd/MM HH:mm")).format(date);
        return value;
    }


    public long getFormattedEstimatedTimeInMS() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        Date date = dateFormat.parse(estimatedTaskDuration);
        String value = (new SimpleDateFormat("HH:mm:ss")).format(date);

        String[] timeArray = value.split(":");

        int hour = Integer.parseInt(timeArray[0]);
        int min = Integer.parseInt(timeArray[1]);
        int sec = Integer.parseInt(timeArray[2]);

        return (hour * 60 * 60 * 1000) + (min * 60 * 1000) + (sec * 1000);
    }

    public long getFormattedTaskDurationInMS() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        Date date = dateFormat.parse(taskDuration);
        String value = (new SimpleDateFormat("HH:mm:ss")).format(date);

        String[] timeArray = value.split(":");

        int hour = Integer.parseInt(timeArray[0]);
        int min = Integer.parseInt(timeArray[1]);
        int sec = Integer.parseInt(timeArray[2]);

        return (hour * 60 * 60 * 1000) + (min * 60 * 1000) + (sec * 1000);
    }

    public void setTaskDuration(String taskDuration) {
        this.taskDuration = taskDuration;
    }

    public String getTaskDuration() {
        return this.taskDuration;
    }


}

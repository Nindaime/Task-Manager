package project.TaskModel;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.Database;

public class Detail {
    private String taskName;
    private String colorCode;
    private String taskDifficulty;
    private String status;

    private TaskTime time;


    public Detail(String taskName, String colorCode, String taskDifficulty, String status, TaskTime time) {
        this.taskName = taskName;
        this.colorCode = colorCode;
        this.taskDifficulty = taskDifficulty;
        this.status = status;
        this.time = time;
    }

    public String getStatus() {
        return this.status;
    }


    public String getName() {
        return this.taskName;
    }

    public String getShortName() {
        String[] words = this.taskName.split(" ");
        String result = "";
        for (String word : words) {
            result += word.charAt(0);
        }

        return result;
    }

    public String getColor() {
        return this.colorCode;
    }


    public String getTaskDifficulty() {
        return taskDifficulty;
    }

    public TaskTime getTime() {
        return this.time;
    }

}

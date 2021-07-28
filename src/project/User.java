package project;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import project.Message.Content;
import project.Message.Message;
import project.Message.People;
import project.TaskModel.*;

public class User {
    private static String username;
    private static String firstname;
    private static String lastname;
    private static String jobTitle;
    private static Blob blob;
    // private static ArrayList<AssignedTask> assignedTasks = new ArrayList<>();


    // private static int workLoad;

    public static void setUsername(String name) {
        username = name;
    }

    public static void setBlob(Blob _blob) {
        blob = _blob;

    }

    public static void setJobTitle(String title) {
        jobTitle = title;
    }

    public static String getUsername() {
        return username;
    }

    public static Message getLastReceivedMessage() throws SQLException, ClassNotFoundException {

        String queryString = "select timesent,sender,receiver,content,status from messages where receiver = ? ORDER BY timesent DESC LIMIT 1";
        String[] params = new String[1];
        params[0] = username;
        ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);

        return getMessage(resultSet);

    }

    private static Message getMessage(ResultSet resultSet) throws SQLException {
        Message message = null;
        while (resultSet.next()) {
            People people;
            Content content;
            String timesent = resultSet.getString(1);
            String sender = resultSet.getString(2);
            String receiver = resultSet.getString(3);
            String messageContent = resultSet.getString(4);
            String status = resultSet.getString(5);
            boolean isRead = true;

            if (status.equals("UNREAD")) {
                isRead = false;
            }
            people = new People(sender, receiver);
            content = new Content(messageContent, timesent, isRead);
            message = new Message(people, content);

        }
        return message;
    }

    public static Message getLastSentMessageTo(String receiver) throws SQLException, ClassNotFoundException {
        String queryString = "select  timesent,sender,receiver,content,status from messages where sender = ? and receiver= ?";
        String[] params = new String[2];
        params[0] = username;
        params[1] = receiver;

        ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);
        return getMessage(resultSet);
    }

    public static void sendMessage(String receiver, String message) throws SQLException, ClassNotFoundException {

        String queryString = "insert into messages set " + "sender = ? ," + "receiver = ? ," + "content = ? ,"
                + "status = ?";

        String[] params = new String[4];

        params[0] = User.getUsername();
        params[1] = receiver;
        params[2] = message;
        params[3] = "UNREAD";

        Database.insertRecordsIntoDB(queryString, params);
    }

    public static ArrayList<AssignedTask> getAssignedTask()
            throws SQLException, ClassNotFoundException, ParseException {
        String queryString = "select  firstname, lastname,tasks.taskName,taskAssigner, image , " +
                "staffs.jobTitle, superior, taskDeadline, " +
                "difficulty,state, startTime, " +
                "taskDuration, averageTaskDuration, username " +
                "from tasksasjob, staffs, tasks " +
                "where taskAssignee = ? and staffs.username= taskAssigner " +
                "and tasks.taskName = tasksasjob.taskName " +
                "and tasksasjob.state <> 'cancelled' " +
                "and tasksasjob.state <> 'finished' ";
        // String queryString = "select experienceLevel, jobTitle, image from staffs
        // where username = ? ";
        String[] params = new String[1];
        params[0] = username;

        ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);
        ArrayList<AssignedTask> tasks = new ArrayList<>();

        // return resultSet;
        Integer colorCount = 0;
        ColorPallete.initColorMap();

        while (resultSet.next()) {

            String firstname = resultSet.getString(1);
            String lastname = resultSet.getString(2);
            String taskName = resultSet.getString(3);
            String taskAssigner = resultSet.getString(4);
            Blob blob = resultSet.getBlob("image");
            String jobTitle = resultSet.getString(6);
            // String superior = resultSet.getString(7);
            String taskDeadline = resultSet.getString(8);
            String taskDifficulty = resultSet.getString(9);
            String state = resultSet.getString(10);
            String startTime = resultSet.getString(11);
            String taskDuration = resultSet.getString(12);
            String averageTaskDuration = resultSet.getString(13);

            Assigner assigner = new Assigner(taskAssigner, firstname, lastname, blob, jobTitle);
            TaskTime taskTime = new TaskTime(startTime, taskDeadline, averageTaskDuration, taskDuration);
            Detail detail = new Detail(taskName, ColorPallete.getColor(colorCount++), taskDifficulty, state, taskTime);

            tasks.add(new AssignedTask(assigner, detail));
        }
        return getTaskBasedOnPriority(tasks);
    }

    public static ArrayList<AssignedTask> getTaskBasedOnPriority(ArrayList<AssignedTask> assignedTasks)
            throws ParseException {

        Map<AssignedTask, Integer> priorityMap = new HashMap<>();
        for (AssignedTask task : assignedTasks) {
            int tD = Integer.parseInt(task.getDetail().getTaskDifficulty());
            int jT = getPriorityBasedOnAssignerLevel(task.getAssigner().getJobTitle());

            int dL = getDeadlinePriorityRate(task.getDetail().getTime().getDeadline());

            int pR = tD + jT + dL;

//            System.out.printf("*** \n Name: %s \n Task Difficulty: %d \n job Assigner Weight: %d \n deadLine: %d pr:%d \n",task.getDetail().getName(), tD, jT, dL, pR);
//            System.out.println("This is the deadline: "+task.getDetail().getDeadline());
            priorityMap.put(task, pR);
        }

        return sortTask(priorityMap);
    }

    private static int getDeadlinePriorityRate(String time) throws ParseException {
        int rate = 0;
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        int interval = (int) Math.floor(date.getTime() - (new Date().getTime()) / (60 * 60 * 1000));

        if (interval < 1) {
            rate = -3;
        }
        if (interval > 2 && interval < 3) {
            rate = -2;
        }
        if (interval > 3 && interval < 4) {
            rate = -1;
        }
        if (interval > 4 && interval < 5) {
            rate = 0;
        }
        if (interval > 5 && interval < 6) {
            rate = 1;
        }
        if (interval > 6 && interval < 7) {
            rate = 2;
        }
        if (interval > 7) {
            rate = 3;
        }
        return rate;
    }

    public static ArrayList<Person> getSubordinates() {

        Person currentUser = new Person(firstname, lastname, username, jobTitle, blob);

        return currentUser.getSubordinates();
    }

    public static ArrayList<Person> getStaffs() {
        Person currentUser = new Person(firstname, lastname, username, jobTitle, blob);

        return currentUser.getStaffs();
    }

    private static int getPriorityBasedOnAssignerLevel(String jobTitle) {

        int priority = 0;
        boolean isFound = false;

        if (User.jobTitle.equals(jobTitle)) {
            return priority;
        }

        Person currentUser = new Person(firstname, lastname, username, jobTitle, blob);

        ArrayList<Person> persons = currentUser.getSubordinates();

        for (Person person : persons) {
            if (person.getJobTitle().equals(jobTitle)) {
                priority = -1;
                isFound = true;
                break;
            }
        }

        if (isFound) {
            return priority;
        }

        Person boss = currentUser.getBoss();

        while (boss.getJobTitle() != null) {


            priority++;


            if (boss.getJobTitle().equals(jobTitle)) {


                break;
            }

            boss = boss.getBoss();


        }
        if (boss.getJobTitle() == null) {
            return 0;
        }
        return priority;
    }

    public static ArrayList<AssignedTask> sortTask(Map<AssignedTask, Integer> priorityMap) {

        ArrayList<AssignedTask> finalSortedTasks = new ArrayList<>();

        priorityMap.forEach((task, value) -> {
            int sizeOfList = finalSortedTasks.size();
            if (sizeOfList == 0) {
                // if no task
                finalSortedTasks.add(task);

            } else if (sizeOfList == 1) {
                int onlyTaskPriority = priorityMap.get(finalSortedTasks.get(0));
                if (value < onlyTaskPriority) {
                    finalSortedTasks.add(0, task);

                } else {
                    finalSortedTasks.add(task);
                }

            } else {

                int middle = (int) Math.floor(sizeOfList / 2);
                int index = middle;
                if (middle % 2 != 0) {
                    index = index - 1;
                }
                if (middle % 2 == 0) {
                    index = index - 1;
                }

                while (true) {
//                    System.out.println("task "+task.getDetail().getName());
//                    System.out.println("value "+value);
                    if (index == 0) {
//                        System.out.println("0 "+index);
                        finalSortedTasks.add(index, task);
                        break;
                    } else if (index >= sizeOfList) {
//                        System.out.println("max "+index);
                        finalSortedTasks.add(task);
                        break;
                    }

                    int leftBound = index > 0 ? priorityMap.get(finalSortedTasks.get(index))
                            : priorityMap.get(finalSortedTasks.get(0));
                    int rightBound = index < sizeOfList - 1 ? priorityMap.get(finalSortedTasks.get(index + 1))
                            : priorityMap.get(finalSortedTasks.get(finalSortedTasks.size() - 1));

                    if (leftBound > value) {
//                        System.out.println("left > "+index);
                        index--;
                    } else if (rightBound < value) {
//                        System.out.println("right < "+index);
                        index++;
                    } else {
//                        System.out.println("others "+index);
                        finalSortedTasks.add(index, task);
                        break;
                    }

                }
            }

        });
        return finalSortedTasks;
    }

    public void getTaskAssignee(ComboBox<Label> taskAssignee, String defaultValue)
            throws SQLException, ClassNotFoundException {
        String queryString = "select username from staffs";

        ResultSet resultSet = Database.retrieveRecordsFromDB(queryString);
        Ui.assignLabelToComboBox(resultSet, taskAssignee, 250, defaultValue);
    }

}

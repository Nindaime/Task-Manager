package project.TaskModel;

import project.Database;

import java.sql.SQLException;

public class AssignedTask {
    private Assigner assigner;
    private Assignee assignee;
    private Detail detail;
    private boolean isRunning;

    public AssignedTask(Assigner assigner, Detail detail) {
        this.assigner = assigner;
        this.detail = detail;
    }

    public AssignedTask(Assignee assignee, Detail detail) {
        this.assignee = assignee;
        this.detail = detail;
    }

    public Detail getDetail() {
        return this.detail;
    }

    public void startTask() throws ClassNotFoundException, SQLException {
        String queryString = "UPDATE tasksasjob SET state = 'started', startTime = NOW() where taskName = ?";
        String[] params = new String[1];
        params[0] = this.detail.getName();
        Database.updateRecordsDB(queryString, params);
        isRunning = true;
    }


    public void cancelTask() throws ClassNotFoundException, SQLException {
        String queryString = "UPDATE tasksasjob SET state = 'cancelled',taskDuration= ? where taskName = ?";
        String[] params = new String[2];
        params[0] = this.detail.getTime().getTaskDuration();
        params[1] = this.detail.getName();
        Database.updateRecordsDB(queryString, params);
        isRunning = false;
    }

    public void suspendTask() throws ClassNotFoundException, SQLException {
        String queryString = "UPDATE tasksasjob SET state = 'suspended', taskDuration= ? where taskName = ?";
        String[] params = new String[2];
        params[0] = this.detail.getTime().getTaskDuration();
        params[1] = this.detail.getName();
        Database.updateRecordsDB(queryString, params);
        isRunning = false;
    }

    public void finishTask() throws ClassNotFoundException, SQLException {
        String queryString = "UPDATE tasksasjob SET state = 'finished',taskDuration= ? where taskName = ?";
        String[] params = new String[2];
        params[0] = this.detail.getTime().getTaskDuration();
        params[1] = this.detail.getName();
        Database.updateRecordsDB(queryString, params);
        isRunning = false;
    }

    public Assigner getAssigner() {
        return this.assigner;
    }

    public Assignee getAssignee() {
        return this.assignee;
    }

    public Person getPerson() {

        if (this.assignee != null) {
            return this.assignee;
        }

        return this.assigner;
    }

    public void updateTimeDuration(String taskDuration) throws ClassNotFoundException, SQLException {
        String queryString = "UPDATE tasksasjob SET state = 'suspended', taskDuration = ? where taskName = ?";
        String[] params = new String[2];
        params[0] = this.detail.getName();
        params[1] = taskDuration;
        Database.updateRecordsDB(queryString, params);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

}

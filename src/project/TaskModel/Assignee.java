package project.TaskModel;

import java.sql.Blob;

public class Assignee extends Person {

    public Assignee(String firstname, String lastname, String username, Blob blob, String jobTitle) {
        super(firstname, lastname, username, jobTitle, blob);
    }

}

package project.TaskModel;

import java.sql.Blob;


public class Assigner extends Person {


    public Assigner(String username, String firstname, String lastname, Blob blob, String jobTitle) {
        super(firstname, lastname, username, jobTitle, blob);
        

    }

    

}

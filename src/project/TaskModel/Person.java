package project.TaskModel;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;

import project.Database;

public class Person {
    private String firstname;
    private String lastname;
    private String username = "";
    private String jobTitle;
    private Blob blob;

    public Blob getBlob() {
        return this.blob;
    }

    public Person() {
    }

    public Person(String firstname, String lastname, String username, String jobTitle, Blob blob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.jobTitle = jobTitle;
        this.blob = blob;
    
    }

    public String getName() {
        return this.firstname + " " + this.lastname;
    }

    public String getUsername() {
        return this.username;

    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public Person getBoss() {
        Person person = new Person();
        try {
            String queryString = "select  username,firstname,lastname,superior, jobTitle, image from staffs where username = (select superior from staffs where username= ?) LIMIT 1";
            String[] params = new String[1];
            params[0] = this.username;

            ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                String firstname = resultSet.getString(2);
                String lastname = resultSet.getString(3);
                String jobTitle = resultSet.getString(5);
                Blob blob = resultSet.getBlob("image");
                person =  new Person(firstname, lastname, username, jobTitle, blob);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return person;
    }


    public static Person getPerson(String _username) {
        Person person = new Person();
        try {
            String queryString = "select  username,firstname,lastname,superior, jobTitle, image from staffs where username = ? LIMIT 1";
            String[] params = new String[1];
            params[0] = _username;

            ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                String firstname = resultSet.getString(2);
                String lastname = resultSet.getString(3);
                String jobTitle = resultSet.getString(5);
                Blob blob = resultSet.getBlob("image");
                person =  new Person(firstname, lastname, username, jobTitle, blob);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return person;
    }

    public ArrayList<Person> getSubordinates() {
        ArrayList<Person> people = new ArrayList<>();

        try {
            String queryString = "select  username,firstname,lastname,superior, jobTitle, image from staffs where superior = ?";
            String[] params = new String[1];
            params[0] = this.username;

            ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                String firstname = resultSet.getString(2);
                String lastname = resultSet.getString(3);
                String jobTitle = resultSet.getString(5);
                Blob blob = resultSet.getBlob("image");
                people.add(new Person(firstname, lastname, username, jobTitle, blob));
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return people;
    }


    public ArrayList<Person> getStaffs() {
        ArrayList<Person> people = new ArrayList<>();

        try {
            String queryString = "select  username,firstname,lastname,superior, jobTitle, image from staffs where username <> ?";
            String[] params = new String[1];
            params[0] = this.username;
            System.out.println("current user is :"+this.username);
            ResultSet resultSet = Database.retrieveRecordsFromDB(queryString, params);
            while (resultSet.next()) {
               
                String username = resultSet.getString(1);
                String firstname = resultSet.getString(2);
                String lastname = resultSet.getString(3);
                String jobTitle = resultSet.getString(5);
                Blob blob = resultSet.getBlob("image");
                people.add(new Person(firstname, lastname, username, jobTitle, blob));
                System.out.println("this is the username"+username );
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return people;
    }
}

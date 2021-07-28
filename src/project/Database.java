package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    public static ResultSet retrieveRecordsFromDB(String queryString) throws SQLException, ClassNotFoundException {
        Connection connection = Main.ConnectToDB();

        // String queryString = "select jobtitle from jobs";
        PreparedStatement prepStatement = connection.prepareStatement(queryString);

        ResultSet resultSet = prepStatement.executeQuery();
        return resultSet;

    }

    public static ResultSet retrieveRecordsFromDB(String queryString, String[] params)
            throws SQLException, ClassNotFoundException {
        Connection connection = Main.ConnectToDB();

        PreparedStatement prepStatement = connection.prepareStatement(queryString);
        int lengthOfParams = params.length;
        for (int i = 0; i < lengthOfParams; i++) {
            prepStatement.setString(i + 1, params[i]);
        }

        ResultSet resultSet = prepStatement.executeQuery();
        return resultSet;

    }

    public static void updateRecordsDB(String queryString, String[] params)
            throws SQLException, ClassNotFoundException {
        Connection connection = Main.ConnectToDB();

        PreparedStatement prepStatement = connection.prepareStatement(queryString);
        int lengthOfParams = params.length;
        for (int i = 0; i < lengthOfParams; i++) {
            prepStatement.setString(i + 1, params[i]);
        }

        prepStatement.executeUpdate();

    }

    public static void insertRecordsIntoDB(String queryString, String[] params)
            throws SQLException, ClassNotFoundException {
        Connection connection = Main.ConnectToDB();
        PreparedStatement prepStatement = connection.prepareStatement(queryString);

        int lengthOfParams = params.length;
        for (int i = 0; i < lengthOfParams; i++) {
            prepStatement.setString(i + 1, params[i]);
        }

        prepStatement.executeUpdate();
    }

}
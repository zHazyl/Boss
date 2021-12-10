package main;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "jdbc-video";
        String databaseUser = "root";
        String databasePassword = "tnh210302";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception exception) {
            exception.printStackTrace();
            exception.getCause();
        }

        return databaseLink;
    }
}

package model.dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;
    private static final Dotenv dotenv = Dotenv.load();
    private static final String dbUrl = dotenv.get("DB_URL");
    private static final String dbUser = dotenv.get("DB_USERNAME");
    private static final String dbPassword = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        System.out.println("Connecting to database...");
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("Connected to database successfully");
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Error closing database connection", e);
        }
    }
}

import java.sql.*;

public class StudentDBManager {
    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";
    private static final String DATABASE_USER = "your_username";
    private static final String DATABASE_PASSWORD = "your_password";

    private final Connection connection;

    public StudentDBManager() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            // Handle connection error
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




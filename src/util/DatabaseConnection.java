package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/Library";
    private static final String USER = "postgres";
    private static final String PASSWORD = "AmiSul151107.";

    public static Connection getConnection() throws SQLException {
       try {
           return DriverManager.getConnection(URL, USER, PASSWORD);
       } catch (SQLException e) {
           System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
           return null;
       }
    }
}

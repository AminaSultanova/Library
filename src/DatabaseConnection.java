import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "postgres";  // Имя пользователя PostgreSQL
    private static final String PASSWORD = "AmiSul151107.";  // Замените на ваш пароль

    // Соединение с базой данных
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

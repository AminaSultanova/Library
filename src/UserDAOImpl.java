import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (name, password, user_type) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getUserType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("type")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with ID " + userId + " was deleted successfully.");
            } else {
                System.out.println("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, password = ?, user_type = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getUserType());
            stmt.setInt(4, user.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with ID " + user.getId() + " was updated successfully.");
            } else {
                System.out.println("User with ID " + user.getId() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("user_type")
                );
            } else {
                System.out.println("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}

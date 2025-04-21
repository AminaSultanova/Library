import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAOImpl implements FavoriteDAO {

    @Override
    public void addToFavorites(Favorite favorite) {
        String sql = "INSERT INTO favorites (user_id, book_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, favorite.getUserId());
            stmt.setInt(2, favorite.getBookId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Favorite> getAllFavorites() {
        List<Favorite> favorites = new ArrayList<>();
        String sql = "SELECT * FROM favorites";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Создаем объект Favorite с тремя параметрами: id, user_id, book_id
                Favorite favorite = new Favorite(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id")
                );
                favorites.add(favorite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorites;
    }
}

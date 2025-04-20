import java.util.List;

public interface FavoriteDAO {
    void addFavorite(Favorite favorite);
    void removeFavorite(int userId, int bookId);
    List<Favorite> getFavoritesByUserId(int userId);
}


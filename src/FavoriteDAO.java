import java.util.List;

public interface FavoriteDAO {
    // Добавление книги в избранное
    void addToFavorites(Favorite favorite);

    // Получение всех избранных книг
    List<Favorite> getAllFavorites();
}

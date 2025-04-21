import java.util.List;

public interface UserDAO {
    // Метод для получения всех пользователей
    List<User> getAllUsers();

    // Метод для получения пользователя по его id
    User getUserById(int userid);

    // Метод для добавления нового пользователя
    void addUser(User user);

    // Метод для обновления данных пользователя
    void updateUser(User user);

    // Метод для удаления пользователя
    void deleteUser(int Userid);
}


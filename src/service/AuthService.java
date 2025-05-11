package service;

import dao.UserDAO;
import model.User;
import java.sql.SQLException;

public class AuthService {
    private UserDAO userDao = new UserDAO();

    public User login(String username, String password) throws SQLException {
        User user = userDao.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public boolean register(String username, String password) throws SQLException {
        if (userDao.getUserByUsername(username) != null) {
            return false; // Пользователь уже существует
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setType("reader");

        return userDao.create(newUser) > 0;
    }
}
package service;

import dao.UserDAO;
import model.Librarian;
import model.Reader;
import model.User;

import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDao = new UserDAO();

    public User login(String username, String password) throws SQLException {
        User user = userDao.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean register(String username, String password, String role) throws SQLException {
        if (userDao.getUserByUsername(username) != null) {
            return false; // User уже существует
        }

        User newUser;
        if ("librarian".equalsIgnoreCase(role)) {
            newUser = new Librarian(username, password);
        } else if ("reader".equalsIgnoreCase(role)) {
            newUser = new Reader(username, password);
        } else {
            throw new IllegalArgumentException("Invalid role: must be 'reader' or 'librarian'");
        }

        return userDao.create(newUser) > 0;
    }
}

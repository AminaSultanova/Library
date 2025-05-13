package service;

import dao.BookDAO;
import model.Book;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryService extends BaseService {
    private final BookDAO bookDAO = new BookDAO();

    //Book management

    public List<Book> getAllBooks() throws SQLException {
        return bookDAO.getAll();
    }

    public Book getBookById(int id) throws SQLException {
        validatePositiveNumber(id, "Book ID");
        return bookDAO.getById(id);
    }

    public int addBook(String title, String author, int year, double price, String status) throws SQLException {
        validateString(title, "Title");
        validateString(author, "Author");
        validateNumberInRange(year, 1, 2025, "Year");
        if (price < 0) throw new IllegalArgumentException("Price must be non-negative");
        validateString(status, "Status");

        Book book = new Book(title, author, year, price, status);
        return bookDAO.create(book);
    }

    public boolean updateBook(int id, String title, String author, int year, double price, String status) throws SQLException {
        validatePositiveNumber(id, "Book ID");
        validateString(title, "Title");
        validateString(author, "Author");
        validateNumberInRange(year, 1, 2025, "Year");
        if (price < 0) throw new IllegalArgumentException("Price must be non-negative");
        validateString(status, "Status");

        Book book = new Book(title, author, year, price, status);
        book.setId(id);
        return bookDAO.update(book);
    }

    public boolean deleteBook(int id) throws SQLException {
        validatePositiveNumber(id, "Book ID");
        return bookDAO.delete(id);
    }

    // Search functionality

    public List<Book> searchBooksByTitle(String title) throws SQLException {
        validateString(title, "Title");

        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE LOWER(?)";
        List<Book> result = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(bookDAO.getById(rs.getInt("id")));
            }
        }
        return result;
    }

    public List<Book> searchBooksByAuthor(String author) throws SQLException {
        validateString(author, "Author");

        String sql = "SELECT * FROM books WHERE LOWER(author) LIKE LOWER(?)";
        List<Book> result = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + author + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(bookDAO.getById(rs.getInt("id")));
            }
        }
        return result;
    }

    public Book findBookByTitleAndAuthor(String title, String author) throws SQLException {
        validateString(title, "Title");
        validateString(author, "Author");

        String sql = "SELECT * FROM books WHERE LOWER(title) = LOWER(?) AND LOWER(author) = LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, author);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return bookDAO.getById(rs.getInt("id"));
            }
        }
        return null;
    }

    //Wallet management

    public double getBalance(int userId) throws SQLException {
        String sql = "SELECT balance FROM wallets WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
            return 0.0;
        }
    }

    public boolean deductBalance(int userId, double amount) throws SQLException {
        String sql = "UPDATE wallets SET balance = balance - ? WHERE user_id = ? AND balance >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount);
            stmt.setInt(2, userId);
            stmt.setDouble(3, amount);
            return stmt.executeUpdate() > 0;
        }
    }

//Purchased books management

    public boolean purchaseBook(int userId, int bookId) throws SQLException {
        Book book = bookDAO.getById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }

        if (!deductBalance(userId, book.getPrice())) {
            System.out.println("❌ Not enough balance.");
            return false;
        }

        String sql = "INSERT INTO purchased_books (user_id, book_id, purchase_date) VALUES (?, ?, CURRENT_DATE)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Book> getPurchasedBooks(int userId) throws SQLException {
        String sql = "SELECT b.* FROM books b " +
                "JOIN purchased_books p ON b.id = p.book_id WHERE p.user_id = ?";
        List<Book> purchased = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                purchased.add(bookDAO.getById(rs.getInt("id")));
            }
        }
        return purchased;
    }

//Favorites management

    public boolean addFavorite(int userId, int bookId) throws SQLException {
        String sql = "INSERT INTO favorites (user_id, book_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Book> getFavorites(int userId) throws SQLException {
        String sql = "SELECT b.* FROM books b " +
                "JOIN favorites f ON b.id = f.book_id WHERE f.user_id = ?";
        List<Book> favorites = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                favorites.add(bookDAO.getById(rs.getInt("id")));
            }
        }
        return favorites;
    }
}

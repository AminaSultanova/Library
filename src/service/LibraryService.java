package service;

import dao.BookDAO;
import model.Book;

import java.sql.SQLException;
import java.util.List;

public class LibraryService extends BaseService {
    private final BookDAO bookDAO = new BookDAO();

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
        book.setId(id); // Важно установить ID

        return bookDAO.update(book);
    }

    public boolean deleteBook(int id) throws SQLException {
        validatePositiveNumber(id, "Book ID");
        return bookDAO.delete(id);
    }

}
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBookDAOImpl implements BorrowedBookDAO {

    @Override
    public void borrowBook(BorrowedBook borrowedBook) {
        String sql = "INSERT INTO borrowed_books (user_id, book_id, due_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, borrowedBook.getUserId());
            stmt.setInt(2, borrowedBook.getBookId());
            stmt.setDate(3, Date.valueOf(borrowedBook.getDueDate())); // Конвертируем LocalDate в SQL Date
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BorrowedBook> getAllBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        String sql = "SELECT * FROM borrowed_books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BorrowedBook bb = new BorrowedBook(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getDate("due_date").toLocalDate() // Конвертируем SQL Date в LocalDate
                );
                borrowedBooks.add(bb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }

    @Override
    public BorrowedBook getBorrowedBookById(int id) {
        String sql = "SELECT * FROM borrowed_books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new BorrowedBook(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getDate("due_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Если не найдено
    }

    @Override
    public void returnBook(int id) {
        String sql = "DELETE FROM borrowed_books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

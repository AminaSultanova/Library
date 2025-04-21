import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchasedBookDAOImpl implements PurchasedBookDAO {
    @Override
    public void purchaseBook(PurchasedBook purchasedBook) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO purchased_books (user_id, book_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, purchasedBook.getUserId());
            stmt.setInt(2, purchasedBook.getBookId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PurchasedBook> getAllPurchasedBooks() {
        List<PurchasedBook> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM purchased_books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new PurchasedBook(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Date bookingDate = rs.getDate("booking_date");
                Date returnDate = rs.getDate("return_date");

                // Создание объекта Booking и добавление в список
                Booking booking = new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        bookingDate,
                        returnDate
                );

                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, book_id, booking_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getBookId());
            stmt.setDate(3, booking.getBookingDate());
            stmt.setDate(4, booking.getReturnDate());
            stmt.setString(5, "booked");  // Статус по умолчанию

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

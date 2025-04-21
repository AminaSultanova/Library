import java.util.List;

public interface BookingDAO {
    // Метод для получения всех бронирований
    List<Booking> getAllBookings();

    // Метод для добавления нового бронирования
    void addBooking(Booking booking);

    // Метод для обновления статуса бронирования (например, подтверждение брони)
    void updateBookingStatus(int bookingId, String status);
}

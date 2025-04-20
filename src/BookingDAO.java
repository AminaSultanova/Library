public interface BookingDAO {
    void addBooking(Booking booking);
    void cancelBooking(int id);
    List<Booking> getBookingsByUserId(int userId);
}


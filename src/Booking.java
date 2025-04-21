import java.sql.Date;

public class Booking {
    private int id;
    private int userId;
    private int bookId;
    private Date bookingDate;  // Тип данных для даты — java.sql.Date
    private Date returnDate;

    public Booking(int id, int userId, int bookId, Date bookingDate, Date returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

import java.time.LocalDate;

public class BorrowedBook {
    private int id;
    private int userId;
    private int bookId;
    private LocalDate dueDate;

    public BorrowedBook(int id, int userId, int bookId, LocalDate dueDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDate getDueDate() {
        return dueDate;
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

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

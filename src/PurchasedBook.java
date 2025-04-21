public class PurchasedBook {
    private int id;
    private int userId;
    private int bookId;

    public PurchasedBook(int id, int userId, int bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getBookId() { return bookId; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
}

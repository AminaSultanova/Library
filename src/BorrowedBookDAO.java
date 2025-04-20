public interface BorrowedBookDAO {
    void addBorrowedBook(BorrowedBook borrowedBook);
    void returnBorrowedBook(int id);
}


import java.util.List;

public interface BorrowedBookDAO {
    void borrowBook(BorrowedBook borrowedBook);
    List<BorrowedBook> getAllBorrowedBooks();
    BorrowedBook getBorrowedBookById(int id); // Метод для получения книги по id
    void returnBook(int id); // Метод для возвращения книги
}

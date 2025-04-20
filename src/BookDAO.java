import java.util.List;

public interface BookDAO {
    void addBook(Book book);
    List<Book> getAllBooks();
    void deleteBook(int id);
    void updateBook(Book book);
    Book getBookById(int id);
}



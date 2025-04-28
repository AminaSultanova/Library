import dao.BookDAO;
import model.Book;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            BookDAO bookDAO = new BookDAO();

            // Создание книги
            Book newBook = new Book(
                    "Clean Code",
                    "Robert Martin",
                    2008,
                    950.00,
                    "available"
            );

            int bookId = bookDAO.create(newBook);
            System.out.println("Created book with ID: " + bookId);

            // Чтение книги
            Book retrievedBook = bookDAO.getById(bookId);
            System.out.println("Retrieved book: " + retrievedBook);

            // Обновление книги
            retrievedBook.setPrice(1000.00);
            boolean updated = bookDAO.update(retrievedBook);
            System.out.println("Update status: " + updated);

            // Удаление книги
            boolean deleted = bookDAO.delete(bookId);
            System.out.println("Delete status: " + deleted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
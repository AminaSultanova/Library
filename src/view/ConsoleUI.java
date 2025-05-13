package view;

import dao.UserDAO;
import model.Book;
import model.User;
import service.AuthService;
import service.LibraryService;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService = new AuthService();
    private final LibraryService libraryService = new LibraryService();
    private final UserDAO userDAO = new UserDAO();

    public void start() {
        System.out.println("\uD83D\uDCDA Welcome to the Library System!");
        System.out.println("Please choose an option: ");
        System.out.println("1. Register");
        System.out.println("2. Login");

        String option = scanner.nextLine();
        switch (option) {
            case "1" -> register();
            case "2" -> login();
            default -> System.out.println("Invalid option.");
        }
    }

    private void register() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (reader/librarian): ");
        String role = scanner.nextLine();

        try {
            authService.register(name, password, role);
            System.out.println("Registration successful.");
            login();
        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
        }
    }

    private void login() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        try {
            User user = authService.login(name, password);
            if (user.getRole().equalsIgnoreCase("reader")) {
                readerMenu(user);
            } else if (user.getRole().equalsIgnoreCase("librarian")) {
                librarianMenu(user);
            } else {
                System.out.println("❌ Invalid role.");
            }
        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }

    private void readerMenu(User user) {
        while (true) {
            System.out.println("\nWelcome, dear Reader!");
            System.out.println("Please choose an option (enter 8 to exit):");
            System.out.println("1. Show all books");
            System.out.println("2. Add book to favorites");
            System.out.println("3. Show my favorites");
            System.out.println("4. Show wallet balance");
            System.out.println("5. Buy a book");
            System.out.println("6. Borrow a book");
            System.out.println("7. Return borrowed book");
            System.out.println("8. Exit");

            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> showAllBooks();
                    case "2" -> addToFavorites(user.getId());
                    case "3" -> showFavorites(user.getId());
                    case "4" -> showWallet(user.getId());
                    case "5" -> buyBook(user.getId());
                    case "6" -> borrowBook(user.getId());
                    case "7" -> returnBook(user.getId());
                    case "8" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private void librarianMenu(User user) {
        while (true) {
            System.out.println("\nWelcome, dear Librarian!");
            System.out.println("Please choose an option (enter 7 to exit):");
            System.out.println("1. Show all readers");
            System.out.println("2. Show all books");
            System.out.println("3. Show borrowed books");
            System.out.println("4. Add a book");
            System.out.println("5. Delete a book");
            System.out.println("6. Update a book");
            System.out.println("7. Exit");

            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> showAllReaders();
                    case "2" -> showAllBooks();
                    case "3" -> showBorrowedBooks();
                    case "4" -> addBook();
                    case "5" -> deleteBook();
                    case "6" -> updateBook();
                    case "7" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private void showAllReaders() throws SQLException {
        List<User> users = userDAO.getAll();
        boolean found = false;
        for (User user : users) {
            if ("reader".equalsIgnoreCase(user.getRole())) {
                System.out.println(user);
                found = true;
            }
        }
        if (!found) {
            System.out.println("📭 No readers found.");
        }
    }

    private void showBorrowedBooks() {
        String sql = """
            SELECT bb.user_id, u.username, b.id AS book_id, b.title, b.author, bb.borrow_date, bb.due_date
            FROM borrowed_books bb
            JOIN books b ON bb.book_id = b.id
            JOIN users u ON bb.user_id = u.id
            ORDER BY bb.borrow_date
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("👤 %s (User ID: %d) borrowed 📖 '%s' by %s (Book ID: %d) on %s, due by %s%n",
                        rs.getString("username"), rs.getInt("user_id"),
                        rs.getString("title"), rs.getString("author"), rs.getInt("book_id"),
                        rs.getDate("borrow_date"), rs.getDate("due_date"));
            }

            if (!found) {
                System.out.println("📭 No borrowed books found.");
            }

        } catch (SQLException e) {
            System.out.println("⚠️ Error retrieving borrowed books: " + e.getMessage());
        }
    }

    private void showAllBooks() throws SQLException {
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("📭 No books available.");
        } else {
            System.out.println("📚 All available books:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void addToFavorites(int userId) throws SQLException {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        Book book = libraryService.findBookByTitleAndAuthor(title, author);
        if (book != null) {
            if (libraryService.addFavorite(userId, book.getId())) {
                System.out.println("✅ Book added to favorites.");
            } else {
                System.out.println("❌ Failed to add to favorites.");
            }
        } else {
            System.out.println("❌ Book not found.");
        }
    }

    private void showFavorites(int userId) throws SQLException {
        List<Book> favorites = libraryService.getFavorites(userId);
        if (favorites.isEmpty()) {
            System.out.println("❤️ No favorites yet.");
        } else {
            System.out.println("❤️ Your favorite books:");
            favorites.forEach(System.out::println);
        }
    }

    private void showWallet(int userId) throws SQLException {
        double balance = libraryService.getBalance(userId);
        System.out.println("💰 Your wallet balance: $" + balance);
    }

    private void borrowBook(int userId) {
        try {
            System.out.print("Enter the ID of the book you want to borrow: ");
            int bookId = Integer.parseInt(scanner.nextLine());

            boolean success = libraryService.borrowBook(userId, bookId);
            if (success) {
                System.out.println("✅ Book successfully borrowed.");
            } else {
                System.out.println("❌ Could not borrow the book.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error while borrowing: " + e.getMessage());
        }
    }

    private void returnBook(int userId) {
        try {
            System.out.print("Enter the ID of the book to return: ");
            int bookId = Integer.parseInt(scanner.nextLine());

            boolean success = libraryService.returnBook(userId, bookId);
            if (success) {
                System.out.println("✅ Book successfully returned.");
            } else {
                System.out.println("❌ Return failed. Maybe you haven’t borrowed this book?");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error during return: " + e.getMessage());
        }
    }

    private void buyBook(int userId) throws SQLException {
        System.out.print("Enter book ID to buy: ");
        int bookId = Integer.parseInt(scanner.nextLine());
        boolean success = libraryService.purchaseBook(userId, bookId);
        if (success) {
            System.out.println("✅ Purchase successful.");
        } else {
            System.out.println("❌ Purchase failed.");
        }
    }

    private void addBook() throws SQLException {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter status: ");
        String status = scanner.nextLine();
        libraryService.addBook(title, author, year, price, status);
        System.out.println("✅ Book added.");
    }

    private void deleteBook() throws SQLException {
        System.out.print("Enter book ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        libraryService.deleteBook(id);
        System.out.println("🗑️ Book deleted.");
    }

    private void updateBook() throws SQLException {
        System.out.print("Enter book ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();
        libraryService.updateBook(id, title, author, year, price, status);
        System.out.println("✅ Book updated.");
    }
}

package view;

import model.Book;
import model.User;
import service.AuthService;
import service.LibraryService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService = new AuthService();
    private final LibraryService libraryService = new LibraryService();

    public void start() {
        System.out.println("Welcome to the Library System.");
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
            login(); // suggest login after registration
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
            System.out.println("Please choose an option (enter 7 to exit):");
            System.out.println("1. Show all books");
            System.out.println("2. Add book to favorites");
            System.out.println("3. Show my favorites");
            System.out.println("4. Show wallet balance");
            System.out.println("5. Buy a book");
            System.out.println("6. Borrow a book");
            System.out.println("7. Exit");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" ->  libraryService.getAllBooks();
                    case "2" -> addToFavorites(user.getId());
                    case "3" -> showFavorites(user.getId());
                    case "4" -> showWallet(user.getId());
                    case "5" -> buyBook(user.getId());
                    case "6" -> System.out.println("📚 Borrowing is not implemented yet.");
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

    private void librarianMenu(User user) {
        while (true) {
            System.out.println("\nWelcome, dear Librarian!");
            System.out.println("Please choose an option (enter 7 to exit):");
            System.out.println("1. Show all readers");
            System.out.println("2. Show borrowed books");
            System.out.println("3. Search for a reader");
            System.out.println("4. Search for a book");
            System.out.println("5. Add a book");
            System.out.println("6. Delete a book");
            System.out.println("7. Update a book");
            System.out.println("8. Exit");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> System.out.println("👥 Feature not implemented: show all readers.");
                    case "2" -> System.out.println("📚 Feature not implemented: borrowed books.");
                    case "3" -> System.out.println("🔍 Feature not implemented: search reader.");
                    case "4" -> searchBooks();
                    case "5" -> addBook();
                    case "6" -> deleteBook();
                    case "7" -> updateBook();
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

    private void searchBooks() throws SQLException {
        System.out.println("1. Search by title");
        System.out.println("2. Search by author");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                System.out.print("Enter title: ");
                String title = scanner.nextLine();
                List<Book> books = libraryService.searchBooksByTitle(title);
                books.forEach(System.out::println);
            }
            case "2" -> {
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                List<Book> books = libraryService.searchBooksByAuthor(author);
                books.forEach(System.out::println);
            }
            default -> System.out.println("Invalid search option.");
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

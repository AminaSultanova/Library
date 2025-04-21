import java.util.List;

public interface PurchasedBookDAO {
    void purchaseBook(PurchasedBook purchasedBook);
    List<PurchasedBook> getAllPurchasedBooks();
}

import java.util.List;

public interface WalletDAO {
    void addWallet(Wallet wallet);
    Wallet getWalletByUserId(int userId);
    void updateBalance(int userId, double newBalance);
    List<Wallet> getAllWallets();
}

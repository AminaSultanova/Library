public interface WalletDAO {
    void addWallet(Wallet wallet);
    Wallet getWalletByUserId(int userId);
    void updateWalletBalance(int userId, double newBalance);
}

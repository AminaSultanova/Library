import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletDAOImpl implements WalletDAO {

    @Override
    public void addWallet(Wallet wallet) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO wallets (user_id, balance) VALUES (?, ?)")) {
            stmt.setInt(1, wallet.getUserId());
            stmt.setDouble(2, wallet.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Wallet getWalletByUserId(int userId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM wallets WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Wallet(rs.getInt("user_id"), rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateBalance(int userId, double newBalance) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE wallets SET balance = ? WHERE user_id = ?")) {
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Wallet> getAllWallets() {
        List<Wallet> wallets = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM wallets")) {
            while (rs.next()) {
                wallets.add(new Wallet(rs.getInt("user_id"), rs.getDouble("balance")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallets;
    }
}

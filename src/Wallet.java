public class Wallet {
    private int userId;
    private double balance;

    public Wallet(int userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

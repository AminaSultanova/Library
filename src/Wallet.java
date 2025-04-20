public class Wallet {
    private int userId;
    private double balance;

    // Конструкторы
    public Wallet(int userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // Геттеры и сеттеры
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}


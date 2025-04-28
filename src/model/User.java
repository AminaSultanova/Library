package model;

public abstract class User {
    private int id;
    private String username;
    private String password;

    // Конструктор
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Абстрактный метод
    public abstract String getRole();

    // Геттеры
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
package model;

public abstract class User {
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract String getRole();

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', role='%s'}",
                id, username, getRole());
    }

}
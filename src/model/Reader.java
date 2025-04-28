package model;

public class Reader extends User {
    public Reader(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "reader";
    }
}
public class User {
    private int id;             // Идентификатор пользователя
    private String name;        // Имя пользователя
    private String password;    // Пароль пользователя
    private String userType;    // Тип пользователя (например, "reader" или "librarian")

    // Конструктор
    public User(int id, String name, String password, String userType) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private double price;
    private String status; // "available", "borrowed", "reserved"

    // Конструкторы
    public Book() {}

    public Book(String title, String author, int year, double price, String status) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.status = status;
    }

    // Геттеры
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%d. %s by %s (%d) - $%.2f [%s]",
                id, title, author, year, price, status);
    }
}
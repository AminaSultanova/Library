package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private double price;
    private String status; // "available", "borrowed"

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
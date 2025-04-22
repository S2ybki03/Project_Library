package model;

public class Book {
    private String id;
    private String title;
    private String author;
    private int availableCopies;

    public Book(String id, String title, String author, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getAvailableCopies() { return availableCopies; }

    public void borrow() { availableCopies--; }
    public void returnBook() { availableCopies++; }
    public String getId() { return id; }
}


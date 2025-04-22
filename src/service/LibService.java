package service;

import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class LibService {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Book> books = new HashMap<>();
    private List<Loan> loans = new ArrayList<>();

    public boolean registerUser(String id, String name, String email, String password) {
        if (users.containsKey(email)) return false;
        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) return false;
        users.put(email, new User(id, name, email, password));
        return true;
    }

    public User loginUser(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) return user;
        return null;
    }

    public List<Book> searchBooks(String query) {
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean borrowBook(String userId, String bookId) {
        Book book = books.get(bookId);
        if (book != null && book.getAvailableCopies() > 0) {
            book.borrow();
            loans.add(new Loan(userId, bookId));
            return true;
        }
        return false;
    }

    public boolean returnBook(String userId, String bookId) {
        Optional<Loan> loan = loans.stream()
                .filter(l -> l.getUserId().equals(userId) && l.getBookId().equals(bookId))
                .findFirst();
        if (loan.isPresent()) {
            loans.remove(loan.get());
            books.get(bookId).returnBook();
            return true;
        }
        return false;
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }
}


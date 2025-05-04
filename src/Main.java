import model.Book;
import service.LibService;

public class Main {
    public static void main(String[] args) {
        LibService lib = new LibService();

        // Dodanie przykładowych danych
        lib.registerUser("u1", "Jan", "jan@example.com", "1234");
        lib.addBook(new Book("1", "Pan Tadeusz", "Adam Mickiewicz", 2));
        lib.addBook(new Book("2", "Lalka", "Bolesław Prus", 1));

        // Przykładowe wypożyczenie
        boolean success = lib.borrowBook("u1", "1");
        System.out.println("Wypożyczenie książki: " + (success ? "sukces" : "niepowodzenie"));

        // Sprawdzenie dostępnych książek
        System.out.println("Wyniki wyszukiwania dla 'pan':");
        lib.searchBooks("pan").forEach(book -> {
            System.out.println(book.getTitle() + " - dostępne: " + book.getAvailableCopies());
        });
    }
}

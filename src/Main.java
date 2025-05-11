import model.Book;
import service.LibService;
import model.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibService lib = new LibService();
        Scanner scanner = new Scanner(System.in);

        // Przykładowe dane
        lib.registerUser("u1", "Jan", "jan@example.com", "1234");
        lib.registerUser("u2", "Anna", "anna@example.com", "pass");
        lib.addBook(new Book("1", "Pan Tadeusz", "Adam Mickiewicz", 2));
        lib.addBook(new Book("2", "Lalka", "Bolesław Prus", 1));

        System.out.println("Witaj w systemie bibliotecznym!");
        User currentUser = null;

        // Logowanie
        while (currentUser == null) {
            System.out.print("Podaj email: ");
            String email = scanner.nextLine();
            System.out.print("Podaj hasło: ");
            String password = scanner.nextLine();
            currentUser = lib.loginUser(email, password);

            if (currentUser == null) {
                System.out.println("Błędne dane logowania. Spróbuj ponownie.");
            }
        }

        System.out.println("Zalogowano jako: " + currentUser.getName());

        // Menu główne
        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Wyszukaj książkę");
            System.out.println("2. Wypożycz książkę");
            System.out.println("3. Zwróć książkę");
            System.out.println("4. Wyloguj");

            System.out.print("Wybierz opcję: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Wprowadź tytuł lub autora: ");
                    String query = scanner.nextLine();
                    var results = lib.searchBooks(query);
                    if (results.isEmpty()) {
                        System.out.println("Brak wyników.");
                    } else {
                        results.forEach(book -> {
                            System.out.println(book.getId() + ": " + book.getTitle() +
                                    " - " + book.getAuthor() + " (dostępne: " + book.getAvailableCopies() + ")");
                        });
                    }
                    break;

                case "2":
                    System.out.print("Podaj ID książki do wypożyczenia: ");
                    String borrowId = scanner.nextLine();
                    boolean borrowed = lib.borrowBook(currentUser.getId(), borrowId);
                    System.out.println(borrowed ? "Wypożyczono pomyślnie!" : "Nie udało się wypożyczyć.");
                    break;

                case "3":
                    System.out.print("Podaj ID książki do zwrotu: ");
                    String returnId = scanner.nextLine();
                    boolean returned = lib.returnBook(currentUser.getId(), returnId);
                    System.out.println(returned ? "Zwrócono pomyślnie!" : "Nie udało się zwrócić.");
                    break;

                case "4":
                    System.out.println("Wylogowano.");
                    running = false;
                    break;

                default:
                    System.out.println("Nieprawidłowa opcja.");
            }
        }

        System.out.println("Do zobaczenia!");
    }
}

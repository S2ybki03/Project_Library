package test;

import service.LibService;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibServiceTest {
    LibService lib;

    @BeforeEach
    void setUp() {
        lib = new LibService();
        lib.addBook(new Book("1", "Pan Tadeusz", "Adam Mickiewicz", 2));
        lib.registerUser("u1", "Jan", "jan@example.com", "1234");
    }

    @Test
    void testRegisterUser() {
        assertTrue(lib.registerUser("u2", "Anna", "anna@example.com", "pass"));
        assertFalse(lib.registerUser("u2", "Anna", "anna@example.com", "pass")); // duplikat
        assertFalse(lib.registerUser("u3", "", "x@example.com", "pass")); // puste pole
    }

    @Test
    void testLoginUser() {
        assertNotNull(lib.loginUser("jan@example.com", "1234"));
        assertNull(lib.loginUser("jan@example.com", "wrongpass"));
        assertNull(lib.loginUser("nonexistent@example.com", "1234"));
    }

    @Test
    void testSearchBooks() {
        assertEquals(1, lib.searchBooks("tadeusz").size());
        assertEquals(1, lib.searchBooks("adam").size());
        assertEquals(0, lib.searchBooks("hobbit").size());
    }

    @Test
    void testBorrowBook() {
        assertTrue(lib.borrowBook("u1", "1"));
        assertTrue(lib.borrowBook("u1", "1")); // 2 egzemplarze
        assertFalse(lib.borrowBook("u1", "1")); // brak dostępnych
    }

    @Test
    void testReturnBook() {
        lib.borrowBook("u1", "1");
        assertTrue(lib.returnBook("u1", "1"));
        assertFalse(lib.returnBook("u1", "1")); // już zwrócone
    }
}

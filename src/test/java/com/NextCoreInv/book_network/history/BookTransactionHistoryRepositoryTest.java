package com.NextCoreInv.book_network.history;

import com.NextCoreInv.book_network.book.Book;
import com.NextCoreInv.book_network.book.BookRepository;
import com.NextCoreInv.book_network.role.TestJpaConfig;
import com.NextCoreInv.book_network.user.User;
import com.NextCoreInv.book_network.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
@Sql(scripts = {"/test-data.sql"})
public class BookTransactionHistoryRepositoryTest {

    @Autowired
    private BookTransactionHistoryRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void isAlreadyBorrowedByUser_shouldReturnTrue_whenBookIsBorrowed() {
        User user = new User();
        user.setEmail("test-user@mail.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthorName("Test Author");
        book.setIsbn("1234567890");
        book.setOwner(savedUser);
        Book savedBook = bookRepository.save(book);

        BookTransactionHistory history = new BookTransactionHistory();
        history.setUser(savedUser);
        history.setBook(savedBook);
        history.setReturnApproved(false);
        repository.save(history);

        boolean result = repository.isAlreadyBorrowedByUser(savedBook.getId(), savedUser.getId());

        assertTrue(result);
    }
}

package com.NextCoreInv.book_network.book;

import com.NextCoreInv.book_network.common.PageResponse;
import com.NextCoreInv.book_network.file.FileStorageService;
import com.NextCoreInv.book_network.history.BookTransactionHistory;
import com.NextCoreInv.book_network.history.BookTransactionHistoryRepository;
import com.NextCoreInv.book_network.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookTransactionHistoryRepository bookTransactionHistoryRepository;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private Authentication authentication;

    @Test
    void save_shouldSaveBook() {
        BookRequest request = new BookRequest(1, "title", "author", "isbn", "synopsis", true);
        User user = User.builder().id(1).build();
        Book book = Book.builder().id(1).build();

        when(authentication.getPrincipal()).thenReturn(user);
        when(bookMapper.toBook(request)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Integer bookId = bookService.save(request, authentication);

        assertEquals(1, bookId);
    }

    @Test
    void findById_shouldReturnBook_whenBookExists() {
        Book book = Book.builder().id(1).build();
        BookResponse bookResponse = BookResponse.builder().id(1).build();
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookService.findById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void findById_shouldThrowException_whenBookDoesNotExist() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.findById(1));
    }

    @Test
    void findAllBooks_shouldReturnPageOfBooks() {
        Page<Book> page = new PageImpl<>(Collections.singletonList(Book.builder().id(1).build()));
        when(bookRepository.findAllDisplayableBooks(any(Pageable.class))).thenReturn(page);
        when(bookMapper.toBookResponse(any(Book.class))).thenReturn(BookResponse.builder().id(1).build());

        PageResponse<BookResponse> result = bookService.findAllBooks(0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findAllBooksByOwner_shouldReturnPageOfBooks() {
        User user = User.builder().id(1).build();
        Page<Book> page = new PageImpl<>(Collections.singletonList(Book.builder().id(1).build()));
        when(authentication.getPrincipal()).thenReturn(user);
        when(bookRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(bookMapper.toBookResponse(any(Book.class))).thenReturn(BookResponse.builder().id(1).build());

        PageResponse<BookResponse> result = bookService.findAllBooksByOwner(0, 10, authentication);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findAllBorrowedBooks_shouldReturnPageOfBorrowedBooks() {
        User user = User.builder().id(1).build();
        Page<BookTransactionHistory> page = new PageImpl<>(Collections.singletonList(BookTransactionHistory.builder().id(1).build()));
        when(authentication.getPrincipal()).thenReturn(user);
        when(bookTransactionHistoryRepository.findAllBorrowedBooks(any(Pageable.class), any(Integer.class))).thenReturn(page);
        when(bookMapper.toBorrowedBookResponse(any(BookTransactionHistory.class))).thenReturn(BorrowedBookResponse.builder().id(1).build());

        PageResponse<BorrowedBookResponse> result = bookService.findAllBorrowedBooks(0, 10, authentication);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findAllReturnedBooks_shouldReturnPageOfReturnedBooks() {
        User user = User.builder().id(1).build();
        Page<BookTransactionHistory> page = new PageImpl<>(Collections.singletonList(BookTransactionHistory.builder().id(1).build()));
        when(authentication.getPrincipal()).thenReturn(user);
        when(bookTransactionHistoryRepository.findAllReturnedBooks(any(Pageable.class), any(Integer.class))).thenReturn(page);
        when(bookMapper.toBorrowedBookResponse(any(BookTransactionHistory.class))).thenReturn(BorrowedBookResponse.builder().id(1).build());

        PageResponse<BorrowedBookResponse> result = bookService.findAllReturnedBooks(0, 10, authentication);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
    }
}

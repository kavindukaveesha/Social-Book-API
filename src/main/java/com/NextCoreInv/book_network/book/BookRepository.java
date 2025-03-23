package com.NextCoreInv.book_network.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    @Query("""
        SELECT book
        FROM Book book
        WHERE book.archived = false
        AND book.shareable = true
   
        """)
    Page<Book> findAllDisplayableBooks(Pageable pageable);

    // Add a count method to check how many books match your criteria
    @Query("""
        SELECT COUNT(book)
        FROM Book book
        WHERE book.archived = false
        AND book.shareable = true
        
        """)
    long countDisplayableBooks();
}

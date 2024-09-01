package com.nazat.book_network.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Long> {

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.user.id = :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, @Param("userId") Long userId);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.owner.id = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, @Param("userId") Long userId);

    @Query("""
            SELECT COUNT(history) > 0
            FROM BookTransactionHistory history
            WHERE history.user.id = :userId
            AND history.book.id = :bookId
            AND history.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(@Param("bookId") Long bookId, @Param("userId") Long userId);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.id = :bookId
            AND history.user.id = :userId
            AND history.returned = false
            AND history.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(@Param("bookId") Long bookId, @Param("userId") Long userId);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.id = :bookId
            AND history.book.owner.id = :userId
            AND history.returned = true
            AND history.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(@Param("bookId") Long bookId, @Param("userId") Long userId);
}

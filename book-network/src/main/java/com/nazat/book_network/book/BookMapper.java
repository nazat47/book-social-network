package com.nazat.book_network.book;

import com.nazat.book_network.file.FileUtils;
import com.nazat.book_network.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookRequest request) {
        return Book.builder().id(request.id())
                .title(request.title())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .synopsis(book.getSynopsis())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .rate(book.getRate())
                .owner(book.getOwner().fullname())
                .isbn(book.getIsbn())
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedBookResponse toBorrowedResponse(BookTransactionHistory bookTransactionHistory) {
        return BorrowedBookResponse.builder()
                .id(bookTransactionHistory.getBook().getId())
                .title(bookTransactionHistory.getBook().getTitle())
                .authorName(bookTransactionHistory.getBook().getAuthorName())
                .rate(bookTransactionHistory.getBook().getRate())
                .isbn(bookTransactionHistory.getBook().getIsbn())
                .returnApproved(bookTransactionHistory.isReturnApproved())
                .returned(bookTransactionHistory.isReturned())
                .build();
    }
}

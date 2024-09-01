package com.nazat.book_network.book;

import com.nazat.book_network.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Long> saveBook(
            @Valid @RequestBody BookRequest request, Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.save(request, connectedUser));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> findByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.findAllBooks(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwmer(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{bookId}")
    public ResponseEntity<Long> updateShareableStatus(@PathVariable Long bookId,
                                                      Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.updateShareableStatus(bookId, connectedUser));
    }

    @PatchMapping("/archive/{bookId}")
    public ResponseEntity<Long> updateArchiveStatus(@PathVariable Long bookId,
                                                    Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.updateArchiveStatus(bookId, connectedUser));
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Long> borrowBook(@PathVariable Long bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/{bookId}")
    public ResponseEntity<Long> returnBorrowedBook(@PathVariable Long bookId,
                                                   Authentication connectedUser) {
        return ResponseEntity.ok(bookService.returnBorrowedBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{bookId}")
    public ResponseEntity<Long> approveReturnedBorrowedBook(@PathVariable Long bookId,
                                                            Authentication connectedUser) {
        return ResponseEntity.ok(bookService.approveReturnedBorrowedBook(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{bookId}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadCoverPicture(
            @PathVariable Long bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        bookService.uploadBookCoverPicture(file, connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }

}

package com.nazat.book_network.feedback;

import com.nazat.book_network.book.Book;
import com.nazat.book_network.book.BookRepository;
import com.nazat.book_network.common.PageResponse;
import com.nazat.book_network.exceptions.OperationNotPermittedException;
import com.nazat.book_network.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;

    public Long save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId()).orElseThrow(() -> new EntityNotFoundException("No book found with id: " + request.bookId()));
        User user = (User) connectedUser.getPrincipal();
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book is archived and not shareable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not give feedback to your own book");
        }
        Feedback feedback = feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbacksByBook(Long bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = (User) connectedUser.getPrincipal();
        Page<Feedback> feedbacks = feedbackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(f -> feedbackMapper.toFeedbackResponse(f, user.getId())).toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}

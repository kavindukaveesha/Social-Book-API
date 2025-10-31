package com.NextCoreInv.book_network.feedback;

import com.NextCoreInv.book_network.book.Book;
import com.NextCoreInv.book_network.book.BookRepository;
import com.NextCoreInv.book_network.common.PageResponse;
import com.NextCoreInv.book_network.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedBackRepository feedBackRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private FeedbackMapper feedbackMapper;
    @Mock
    private Authentication authentication;

    @Test
    void save_shouldSaveFeedback() {
        FeedbackRequest request = new FeedbackRequest(5.0, "comment", 1);
        User user = User.builder().id(1).build();
        Book book = Book.builder().id(1).archived(false).shareable(true).build();
        Feedback feedback = Feedback.builder().id(1).build();

        when(authentication.getPrincipal()).thenReturn(user);
        when(bookRepository.findById(request.bookId())).thenReturn(Optional.of(book));
        when(feedbackMapper.toFeedback(request)).thenReturn(feedback);
        when(feedBackRepository.save(any(Feedback.class))).thenReturn(feedback);

        Integer feedbackId = feedbackService.save(request, authentication);

        assertEquals(1, feedbackId);
    }

    @Test
    void findAllFeedbacksByBook_shouldReturnPageOfFeedbacks() {
        User user = User.builder().id(1).build();
        Page<Feedback> page = new PageImpl<>(Collections.singletonList(Feedback.builder().id(1).build()));
        when(authentication.getPrincipal()).thenReturn(user);
        when(feedBackRepository.findAllByBookId(any(Integer.class), any(Pageable.class))).thenReturn(page);
        when(feedbackMapper.toFeedbackResponse(any(Feedback.class), any(Integer.class))).thenReturn(FeedbackResponse.builder().note(5.0).comment("comment").ownFeedback(true).build());

        PageResponse<FeedbackResponse> result = feedbackService.findAllFeedbacksByBook(1, 0, 10, authentication);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
    }
}

package com.nazat.book_network.feedback;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackResponse {
    private double note;
    private String comment;
    private boolean ownFeedback;
}

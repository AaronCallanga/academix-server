package com.academix.academix.document.feedback.service.api;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.request.entity.DocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface FeedbackService {
    // Common
    Feedback submitFeedback(DocumentRequest documentRequest, Feedback feedbackRequest);
    Feedback getFeedbackByRequestId(Long requestId);

    // Admin
    Page<Feedback> getAllFeedbacks(PageRequest pageRequest);
    Page<Feedback> getFeedbacksByRating(int rating, PageRequest pageRequest);


    /* make this private?
    // Utility / Management
    double getAverageRating();
    Map<Integer, Long> getRatingDistribution(); // e.g., {1: 3, 2: 5, 5: 10}    count nubmer of response per rating level
    boolean hasUserSubmittedFeedback(Long requestId, Authentication authentication);
     */
}

package com.academix.academix.document.feedback.service.api;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.request.entity.DocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

public interface FeedbackService {
    // Common
    Feedback submitFeedback(DocumentRequest documentRequest, Feedback feedbackRequest);
    Feedback getFeedbackByRequestId(Long requestId);

    // Admin
    Page<Feedback> getAllFeedbacks(PageRequest pageRequest);
    Page<Feedback> getFeedbacksByRating(int rating, PageRequest pageRequest);
    Double getAverageRating();
    Map<Integer, Long> getRatingDistribution(); // e.g., {1: 3, 2: 5, 5: 10}    count nubmer of response per rating level

    /* make this private?
    // Utility / Management
    boolean hasUserSubmittedFeedback(Long requestId, Authentication authentication);
     */
}

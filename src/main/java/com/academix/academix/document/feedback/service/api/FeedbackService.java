package com.academix.academix.document.feedback.service.api;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import com.academix.academix.document.feedback.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FeedbackService {
    // Common
    Feedback submitFeedback(FeedbackRequestDTO feedbackRequestDTO, Authentication authentication);
    Feedback getFeedbackByRequestId(Long requestId);
    List<FeedbackResponseDTO> getOwnFeedbacks(Authentication authentication);

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

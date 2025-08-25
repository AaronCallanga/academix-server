package com.academix.academix.document.feedback.facade.api;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface FeedbackFacade {
    // Common
    FeedbackResponseDTO submitFeedback(Long documentRequestId, FeedbackRequestDTO feedbackRequestDTO, Authentication authentication);
    FeedbackResponseDTO getFeedbackByRequestId(Long requestId);

    // Admin
    Page<FeedbackResponseDTO> getAllFeedbacks(int page, int size, String sortField, String sortDirection);
    Page<FeedbackResponseDTO> getFeedbacksByRating(int rating, int page, int size, String sortField, String sortDirection);
    Map<String, Double> getAverageRatings();
    Map<Integer, Long> getRatingDistribution();
}

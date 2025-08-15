package com.academix.academix.document.feedback.facade.api;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.request.entity.DocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface FeedbackFacade {
    // Common
    FeedbackResponseDTO submitFeedback(Long documentRequestId, FeedbackRequestDTO feedbackRequestDTO);
    FeedbackResponseDTO getFeedbackByRequestId(Long requestId);

    // Admin
    Page<FeedbackResponseDTO> getAllFeedbacks(int page, int size, String sortField, String sortDirection);
    Page<FeedbackResponseDTO> getFeedbacksByRating(int rating, int page, int size, String sortField, String sortDirection);

}

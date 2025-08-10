package com.academix.academix.document.feedback.service.api;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.entity.Feedback;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FeedbackService {
    // Common
    Feedback submitFeedback(FeedbackRequestDTO feedbackRequestDTO, Authentication authentication);
    Feedback submitFeedbackAnonymously(FeedbackRequestDTO feedbackRequestDTO);
    Feedback getFeedbackByRequestId(Long requestId);

    // Admin
    List<Feedback> getAllFeedbacks();

}

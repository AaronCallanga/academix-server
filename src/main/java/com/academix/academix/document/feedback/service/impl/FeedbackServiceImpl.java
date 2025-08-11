package com.academix.academix.document.feedback.service.impl;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.feedback.service.api.FeedbackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public class FeedbackServiceImpl implements FeedbackService {
    @Override
    public Feedback submitFeedback(FeedbackRequestDTO feedbackRequestDTO, Authentication authentication) {
        return null;
    }

    @Override
    public Feedback getFeedbackByRequestId(Long requestId) {
        return null;
    }

    @Override
    public List<FeedbackResponseDTO> getOwnFeedbacks(Authentication authentication) {
        return List.of();
    }

    @Override
    public Page<Feedback> getAllFeedbacks(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<Feedback> getFeedbacksByRating(int rating, PageRequest pageRequest) {
        return null;
    }
}

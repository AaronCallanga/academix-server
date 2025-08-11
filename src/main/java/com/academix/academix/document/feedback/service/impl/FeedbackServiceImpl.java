package com.academix.academix.document.feedback.service.impl;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.feedback.mapper.FeedbackMapper;
import com.academix.academix.document.feedback.repository.FeedbackRepository;
import com.academix.academix.document.feedback.service.api.FeedbackService;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.enums.DocumentStatus;
import com.academix.academix.exception.types.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public Feedback submitFeedback(DocumentRequest documentRequest, Feedback feedbackRequest, Authentication authentication) {

        if (!documentRequest.getStatus().equals(DocumentStatus.RELEASED) || !documentRequest.getStatus().equals(DocumentStatus.CANCELLED)) {
            throw new BadRequestException("Request is not yet completed");
        }

        // FeedbackRequest mapped by mapper in facade has rating, comment, anonymous




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

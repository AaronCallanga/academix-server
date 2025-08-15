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
import com.academix.academix.exception.types.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public Feedback submitFeedback(DocumentRequest documentRequest, Feedback feedbackRequest) {
        // If status is not RELEASED and CANCELLED, throw exception
        if (!documentRequest.getStatus().equals(DocumentStatus.RELEASED) && !documentRequest.getStatus().equals(DocumentStatus.CANCELLED)) {
            throw new BadRequestException("Request is not yet completed");
        }

        // FeedbackRequest mapped by mapper in facade has rating, comment, anonymous
        feedbackRequest.setSubmittedAt(LocalDateTime.now());
        feedbackRequest.setDocumentRequest(documentRequest);

        return feedbackRepository.save(feedbackRequest);
    }

    @Override
    public Feedback getFeedbackByRequestId(Long requestId) {
        return feedbackRepository.findByDocumentRequest_Id(requestId)
                                 .orElseThrow(() -> new ResourceNotFoundException("Feedback not found for request ID: " + requestId));
    }


    @Override
    public Page<Feedback> getAllFeedbacks(PageRequest pageRequest) {
        return feedbackRepository.findAll(pageRequest);
    }

    @Override
    public Page<Feedback> getFeedbacksByRating(int rating, PageRequest pageRequest) {
        return feedbackRepository.findByRating(rating, pageRequest);
    }
}

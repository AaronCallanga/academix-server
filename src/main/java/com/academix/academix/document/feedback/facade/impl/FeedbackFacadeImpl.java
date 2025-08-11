package com.academix.academix.document.feedback.facade.impl;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.feedback.facade.api.FeedbackFacade;
import com.academix.academix.document.feedback.mapper.FeedbackMapper;
import com.academix.academix.document.feedback.service.api.FeedbackService;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import com.academix.academix.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackFacadeImpl implements FeedbackFacade {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;
    private final UserService userService; //for logs
    private final DocumentRequestAuditService documentRequestAuditService;


    @Override
    public Feedback submitFeedback(Long documentRequestId, FeedbackRequestDTO feedbackRequestDTO) {
        return null;
    }

    @Override
    public Feedback getFeedbackByRequestId(Long requestId) {
        return null;
    }

    @Override
    public Page<Feedback> getAllFeedbacks(int page, int size, String sortField, String sortDirection) {
        return null;
    }

    @Override
    public Page<Feedback> getFeedbacksByRating(int rating, int page, int size, String sortField, String sortDirection) {
        return null;
    }
}

package com.academix.academix.document.feedback.facade.impl;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.feedback.facade.api.FeedbackFacade;
import com.academix.academix.document.feedback.mapper.FeedbackMapper;
import com.academix.academix.document.feedback.service.api.FeedbackService;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.service.api.DocumentRequestService;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import com.academix.academix.user.dto.UserInfoDTO;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackFacadeImpl implements FeedbackFacade {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;
    private final UserService userService; //for logs
    private final DocumentRequestAuditService documentRequestAuditService;
    private final DocumentRequestService documentRequestService;

    @Override
    public FeedbackResponseDTO submitFeedback(Long documentRequestId, FeedbackRequestDTO feedbackRequestDTO) {
        DocumentRequest documentRequest = documentRequestService.fetchDocumentRequestById(documentRequestId);
        Feedback feedback = feedbackMapper.toFeedback(feedbackRequestDTO);
        Feedback savedFeedback = feedbackService.submitFeedback(documentRequest, feedback);



        // logs, make it AOP

        FeedbackResponseDTO feedbackResponseDTO = feedbackMapper.toFeedbackResponseDTO(savedFeedback);
        if (!savedFeedback.isAnonymous()) {
            User user = documentRequest.getRequestedBy();
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
            feedbackResponseDTO.setUserInfoDTO(userInfoDTO);
        }

        return feedbackResponseDTO;
    }

    @Override
    public FeedbackResponseDTO getFeedbackByRequestId(Long requestId) {
        Feedback feedback = feedbackService.getFeedbackByRequestId(requestId);
        return feedbackMapper.toFeedbackResponseDTO(feedback);
    }

    @Override
    public Page<FeedbackResponseDTO> getAllFeedbacks(int page, int size, String sortField, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<Feedback> feedbacks = feedbackService.getAllFeedbacks(pageRequest);
        List<FeedbackResponseDTO> feedbackResponseDTOList = feedbackMapper.toFeedbackResponseListDTO(feedbacks.getContent());
        return new PageImpl<>(feedbackResponseDTOList, pageRequest, feedbacks.getTotalElements());
    }

    @Override
    public Page<FeedbackResponseDTO> getFeedbacksByRating(int rating, int page, int size, String sortField, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<Feedback> feedbacks = feedbackService.getFeedbacksByRating(rating, pageRequest);
        List<FeedbackResponseDTO> feedbackResponseDTOList = feedbackMapper.toFeedbackResponseListDTO(feedbacks.getContent());
        return new PageImpl<>(feedbackResponseDTOList, pageRequest, feedbacks.getTotalElements());
    }
}

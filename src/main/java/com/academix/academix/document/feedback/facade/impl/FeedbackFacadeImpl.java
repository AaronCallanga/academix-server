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
import java.util.Map;

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

        // Send email

        FeedbackResponseDTO feedbackResponseDTO = feedbackMapper.toFeedbackResponseDTO(savedFeedback);
        fillInUserInfo_IfFeedbackNotAnonymous(feedbackResponseDTO, savedFeedback);

        return feedbackResponseDTO;
    }

    @Override
    public FeedbackResponseDTO getFeedbackByRequestId(Long requestId) {
        Feedback feedback = feedbackService.getFeedbackByRequestId(requestId);
        FeedbackResponseDTO feedbackResponseDTO = feedbackMapper.toFeedbackResponseDTO(feedback);
        fillInUserInfo_IfFeedbackNotAnonymous(feedbackResponseDTO, feedback);
        return feedbackResponseDTO;
    }

    @Override
    public Page<FeedbackResponseDTO> getAllFeedbacks(int page, int size, String sortField, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<Feedback> feedbacks = feedbackService.getAllFeedbacks(pageRequest);
        List<FeedbackResponseDTO> feedbackResponseDTOList = feedbackMapper.toFeedbackResponseListDTO(feedbacks.getContent());

        // Fill in user info if not anonymous
        fillInUserInfo_IfFeedbackNotAnonymous(feedbackResponseDTOList, feedbacks);

        return new PageImpl<>(feedbackResponseDTOList, pageRequest, feedbacks.getTotalElements());
    }

    @Override
    public Page<FeedbackResponseDTO> getFeedbacksByRating(int rating, int page, int size, String sortField, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<Feedback> feedbacks = feedbackService.getFeedbacksByRating(rating, pageRequest);
        List<FeedbackResponseDTO> feedbackResponseDTOList = feedbackMapper.toFeedbackResponseListDTO(feedbacks.getContent());

        // Fill in user info if not anonymous
        fillInUserInfo_IfFeedbackNotAnonymous(feedbackResponseDTOList, feedbacks);

        return new PageImpl<>(feedbackResponseDTOList, pageRequest, feedbacks.getTotalElements());
    }

    @Override
    public Map<String, Double> getAverageRatings() {
        Double average = feedbackService.getAverageRating();
        return Map.of("Average", average);
    }

    @Override
    public Map<Integer, Long> getRatingDistribution() {
        return feedbackService.getRatingDistribution();
    }

    private UserInfoDTO buildUserInfoDTO(User user) {
        return UserInfoDTO.builder()
                          .id(user.getId())
                          .name(user.getName())
                          .email(user.getEmail())
                          .build();
    }

    private void fillInUserInfo_IfFeedbackNotAnonymous(FeedbackResponseDTO feedbackResponseDTO, Feedback feedback) {
        User user = feedback.getDocumentRequest().getRequestedBy();
        // make this as util
        if (!feedback.isAnonymous()) {
            UserInfoDTO userInfoDTO = buildUserInfoDTO(user);
            feedbackResponseDTO.setUserInfoDTO(userInfoDTO);
        }
    }

    private void fillInUserInfo_IfFeedbackNotAnonymous(List<FeedbackResponseDTO> feedbackResponseDTOList, Page<Feedback> feedbacks) {
        // Fill in user info if not anonymous
        for (int i = 0; i < feedbackResponseDTOList.size(); i++) {
            FeedbackResponseDTO feedbackDTO = feedbackResponseDTOList.get(i);
            Feedback feedbackEntity = feedbacks.getContent().get(i);

            if (!feedbackDTO.isAnonymous()) {
                // Get the user from entity
                User user = feedbackEntity.getDocumentRequest().getRequestedBy();

                if (user != null) {
                    UserInfoDTO userInfoDTO = buildUserInfoDTO(user);
                    feedbackDTO.setUserInfoDTO(userInfoDTO);
                }
            }
        }
    }
}

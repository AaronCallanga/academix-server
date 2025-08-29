package com.academix.academix.security.util;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.feedback.repository.FeedbackRepository;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.repository.DocumentRequestRepository;
import com.academix.academix.document.request.service.api.DocumentRequestService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component
public class FeedbackPermissionEvaluator {

    private final DocumentRequestService documentRequestService;
    private final UserService userService;

    // authenticated user must only be able to send feedback to their own request,
    public boolean isOwnerOfRequest(Long documentRequestId, Authentication authentication) {
        User authenticatedUser = userService.getUserFromAuthentication(authentication);
        DocumentRequest documentRequest = documentRequestService.getDocumentRequestById(documentRequestId);
        return documentRequest.getRequestedBy().equals(authenticatedUser);
    }
}

package com.academix.academix.security.util;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.service.api.DocumentRequestService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PermissionEvaluator {

    private final DocumentRequestService documentRequestService;
    private final UserService userService;

    // authenticated user must only be able to send feedback to their own request,
    public boolean isOwnerOfRequest(Long documentRequestId, Authentication authentication) {
        User authenticatedUser = userService.getUserFromAuthentication(authentication);
        DocumentRequest documentRequest = documentRequestService.getDocumentRequestById(documentRequestId);
        return documentRequest.getRequestedBy().getId().equals(authenticatedUser.getId());
    }
}

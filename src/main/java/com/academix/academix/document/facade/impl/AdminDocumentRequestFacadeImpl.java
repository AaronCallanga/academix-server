package com.academix.academix.document.facade.impl;

import com.academix.academix.document.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.dto.request.ReasonDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.document.facade.api.AdminDocumentRequestFacade;
import com.academix.academix.document.mapper.DocumentRequestMapper;
import com.academix.academix.document.service.api.DocumentRemarkService;
import com.academix.academix.document.service.api.DocumentRequestService;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDocumentRequestFacadeImpl implements AdminDocumentRequestFacade {

    private final DocumentRequestService documentRequestService;
    private final DocumentRemarkService documentRemarkService;
    private final UserService userService;
    private final DocumentRequestAuditService documentRequestAuditService;
    private final DocumentRequestMapper documentRequestMapper;

    @Override
    public Page<DocumentRequestResponseListDTO> getAllDocumentRequests(int page,
                                                                       int size,
                                                                       String sortField,
                                                                       String sortDirection) {

        return documentRequestService.getAllDocumentRequests(page, size, sortField, sortDirection);
    }

    @Override
    public Page<DocumentRequestResponseListDTO> getUserDocumentRequests(Long userId,
                                                                        int page,
                                                                        int size,
                                                                        String sortField,
                                                                        String sortDirection) {
        return documentRequestService.getUserDocumentRequests(userId, page, size, sortField, sortDirection);
    }

    @Override
    public DocumentRequestResponseDTO approveDocumentRequest(Long documentRequestId, Authentication authentication) {
        DocumentRequest savedRequest = documentRequestService.approveDocumentRequest(documentRequestId);
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                documentRequestService.determineActorType(user.getRoles()),     //Maybe make it helper/util
                DocumentAction.APPROVED,
                "Request Approved",
                user
                                                      );
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO rejectDocumentRequest(Long documentRequestId,
                                                            Authentication authentication,
                                                            ReasonDTO reasonDto) {
        DocumentRequest savedRequest = documentRequestService.rejectDocumentRequest(documentRequestId, reasonDto);
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.REJECTED,
                reasonDto.getReason(),
                user
                                                      );

        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO releaseDocumentRequest(Long documentRequestId, Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO setDocumentRequestStatusToReadyForPickup(Long documentRequestId,
                                                                               Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO setDocumentRequestStatusToInProgress(Long documentRequestId,
                                                                           Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO adminUpdateDocumentRequest(Long documentRequestId,
                                                                 DocumentRequestAdminUpdateDTO documentRequestAdminUpdateDTO,
                                                                 Authentication authentication) {
        return null;
    }
}

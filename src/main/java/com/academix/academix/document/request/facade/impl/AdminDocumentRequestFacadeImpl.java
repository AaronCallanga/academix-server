package com.academix.academix.document.request.facade.impl;

import com.academix.academix.document.request.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.request.dto.request.ReasonDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.facade.api.AdminDocumentRequestFacade;
import com.academix.academix.document.request.mapper.DocumentRequestMapper;
import com.academix.academix.document.remark.service.api.DocumentRemarkService;
import com.academix.academix.document.request.service.api.DocumentRequestService;
import com.academix.academix.email.api.DocumentEmailService;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDocumentRequestFacadeImpl implements AdminDocumentRequestFacade {

    private final DocumentRequestService documentRequestService;
    private final UserService userService;
    private final DocumentRequestAuditService documentRequestAuditService;
    private final DocumentRequestMapper documentRequestMapper;
    private final DocumentEmailService documentEmailService;

    @Override
    public Page<DocumentRequestResponseListDTO> getAllDocumentRequests(int page,
                                                                       int size,
                                                                       String sortField,
                                                                       String sortDirection) {

        // Build the PageRequest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        Page<DocumentRequest> documentRequests = documentRequestService.getAllDocumentRequests(pageRequest);
        // Convert the paged document requests to List of DTOs (total of 10 by default)
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS = documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests.getContent());

        // Re-build the page object to return the items with mapped to dtos
        return new PageImpl<>(documentRequestResponseListDTOS, pageRequest, documentRequests.getTotalElements());
    }

    @Override
    public Page<DocumentRequestResponseListDTO> getUserDocumentRequests(Long userId,
                                                                        int page,
                                                                        int size,
                                                                        String sortField,
                                                                        String sortDirection) {
        // Build the PageRequest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        Page<DocumentRequest> documentRequests = documentRequestService.getUserDocumentRequests(userId, pageRequest);

        // Map the list to List of DTOs
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS = documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests.getContent());

        // Re-build the page object to return the items with mapped to dtos
        return new PageImpl<>(documentRequestResponseListDTOS, pageRequest, documentRequests.getTotalElements());
    }

    @Override
    public DocumentRequestResponseDTO approveDocumentRequest(Long documentRequestId, Authentication authentication) {
        DocumentRequest savedRequest = documentRequestService.approveDocumentRequest(documentRequestId);
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Send update email
        documentEmailService.sendDocumentUpdate(user, savedRequest);

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

        // Send update email
        documentEmailService.sendDocumentUpdate(user, savedRequest);

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
        DocumentRequest savedRequest = documentRequestService.releaseDocumentRequest(documentRequestId);
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);
        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.RELEASED,
                "Document Released",
                user
                                                      );
        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO setDocumentRequestStatusToReadyForPickup(Long documentRequestId,
                                                                               Authentication authentication) {
        DocumentRequest savedRequest = documentRequestService.setDocumentRequestStatusToReadyForPickup(documentRequestId);
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);
        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.READY_FOR_PICKUP,
                "Ready for Pickup",
                user
                                                      );
        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO setDocumentRequestStatusToInProgress(Long documentRequestId,
                                                                           Authentication authentication) {
        DocumentRequest savedRequest = documentRequestService.setDocumentRequestStatusToInProgress(documentRequestId);
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);
        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.IN_PROGRESS,
                "Marked as In Progress",
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO adminUpdateDocumentRequest(Long documentRequestId,
                                                                 DocumentRequestAdminUpdateDTO documentRequestAdminUpdateDTO,
                                                                 Authentication authentication) {
        DocumentRequest savedRequest = documentRequestService.adminUpdateDocumentRequest(documentRequestId, documentRequestAdminUpdateDTO);
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);
        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.UPDATED,
                "Admin Forced Updated",
                user
                                                      );
        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }
}

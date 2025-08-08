package com.academix.academix.document.request.facade.impl;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.request.dto.request.ReasonDTO;
import com.academix.academix.document.request.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.facade.api.DocumentRequestFacade;
import com.academix.academix.document.request.mapper.DocumentRequestMapper;
import com.academix.academix.document.remark.service.api.DocumentRemarkService;
import com.academix.academix.document.request.service.api.DocumentRequestService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRequestFacadeImpl implements DocumentRequestFacade {

    private final DocumentRequestService documentRequestService;
    private final DocumentRemarkService documentRemarkService;
    private final UserService userService;
    private final DocumentRequestAuditService documentRequestAuditService;
    private final DocumentRequestMapper documentRequestMapper;

    @Override
    public Page<DocumentRequestResponseListDTO> getOwnDocumentRequests(Authentication authentication,
                                                                       int page,
                                                                       int size,
                                                                       String sortField,
                                                                       String sortDirection) {
        // Build the Page Request
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<DocumentRequest> documentRequests = documentRequestService.getOwnDocumentRequests(authentication, pageRequest);
        // Map the document requests to list of DTOs and return it
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS = documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests.getContent());
        // Re-build the page object to return the items with mapped to dtos
        return new PageImpl<>(documentRequestResponseListDTOS, pageRequest, documentRequests.getTotalElements());
    }

    @Override
    public DocumentRequestResponseDTO getDocumentRequestById(Long documentRequestId) {
        DocumentRequest documentRequest = documentRequestService.getDocumentRequestById(documentRequestId);
        // Map the document request to DTO then return it
        return documentRequestMapper.toDocumentRequestResponseDTO(documentRequest);
    }

    @Override
    public DocumentRequestResponseDTO createDocumentRequest(CreateDocumentRequestDTO documentRequestDTO,
                                                            Authentication authentication) {
        // Fetch the authenticated user
        User user = userService.getUserFromAuthentication(authentication);

        // Modify or fill all the remaining fields excluding remarks
        DocumentRequest newDocumentRequest = documentRequestService.buildDocumentRequest(documentRequestDTO, user);

        // Now, per each remark's content in the documentRequestDTO, create a DocumentRemark entity and add it to the request
        documentRemarkService.buildDocumentRemarkList(documentRequestDTO, newDocumentRequest, user);

        DocumentRequest savedDocumentRequest = documentRequestService.save(newDocumentRequest);

        // Log the created request
        documentRequestAuditService.logDocumentRequest(
                savedDocumentRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.CREATED,
                "Request Submitted",
                user
                                                      );

        return documentRequestMapper.toDocumentRequestResponseDTO(savedDocumentRequest);
    }

    @Override
    public DocumentRequestResponseDTO updateDocumentRequest(UpdateDocumentRequestDTO documentRequestDTO,
                                                            Long documentRequestId,
                                                            Authentication authentication) {
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        DocumentRequest savedRequest = documentRequestService.updateDocumentRequest(documentRequestDTO, documentRequestId);

        // Log the created request
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.UPDATED,
                "User Request Updated",
                user
                                                      );

        // Return the savedRequest and mapped it to response DTO
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO cancelDocumentRequest(Long documentRequestId,
                                                            Authentication authentication,
                                                            ReasonDTO reasonDto) {
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        DocumentRequest savedRequest = documentRequestService.cancelDocumentRequest(documentRequestId, reasonDto);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.CANCELLED,
                reasonDto.getReason(),
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Transactional
    @Override
    public void deleteDocumentRequest(Long documentRequestId, Authentication authentication, ReasonDTO reasonDto) {
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        DocumentRequest documentRequest = documentRequestService.fetchDocumentRequestById(documentRequestId);

        // Log the action
        documentRequestAuditService.logDocumentRequest(
                documentRequest,
                documentRequestService.determineActorType(user.getRoles()),
                DocumentAction.DELETED,
                reasonDto.getReason(),
                user
                                                      );

        //DocumentRequest documentRequest2 =
                documentRequestService.deleteDocumentRequest(documentRequestId, reasonDto);
    }
}

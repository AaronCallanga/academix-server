package com.academix.academix.document.facade.impl;

import com.academix.academix.document.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.dto.request.ReasonDTO;
import com.academix.academix.document.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.document.facade.api.DocumentRequestFacade;
import com.academix.academix.document.mapper.DocumentRequestMapper;
import com.academix.academix.document.service.api.DocumentRemarkService;
import com.academix.academix.document.service.api.DocumentRequestService;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
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
        return null;
    }

    @Override
    public DocumentRequestResponseDTO updateDocumentRequest(UpdateDocumentRequestDTO documentRequestDTO,
                                                            Long documentRequestId,
                                                            Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO cancelDocumentRequest(Long documentRequestId,
                                                            Authentication authentication,
                                                            ReasonDTO reasonDto) {
        return null;
    }

    @Override
    public void deleteDocumentRequest(Long documentRequestId, Authentication authentication, ReasonDTO reasonDto) {

    }

    @Override
    public DocumentRequest fetchDocumentRequestById(Long documentRequestId) {
        return null;
    }
}

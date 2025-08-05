package com.academix.academix.document.facade.impl;

import com.academix.academix.document.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.dto.request.ReasonDTO;
import com.academix.academix.document.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.document.facade.api.DocumentRequestFacade;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public class DocumentRequestFacadeImpl implements DocumentRequestFacade {
    @Override
    public Page<DocumentRequestResponseListDTO> getOwnDocumentRequests(Authentication authentication,
                                                                       int page,
                                                                       int size,
                                                                       String sortField,
                                                                       String sortDirection) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO getDocumentRequestById(Long documentRequestId) {
        return null;
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

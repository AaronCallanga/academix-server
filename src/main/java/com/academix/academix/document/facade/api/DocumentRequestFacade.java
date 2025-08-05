package com.academix.academix.document.facade.api;

import com.academix.academix.document.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.dto.request.ReasonDTO;
import com.academix.academix.document.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface DocumentRequestFacade {
    // ==== INDIVIDUAL / STUDENT ====
    Page<DocumentRequestResponseListDTO> getOwnDocumentRequests(Authentication authentication, int page, int size, String sortField, String sortDirection);

    // ==== COMMON ====
    DocumentRequestResponseDTO getDocumentRequestById(Long documentRequestId);
    DocumentRequestResponseDTO createDocumentRequest(CreateDocumentRequestDTO documentRequestDTO, Authentication authentication);
    DocumentRequestResponseDTO updateDocumentRequest(UpdateDocumentRequestDTO documentRequestDTO, Long documentRequestId, Authentication authentication);
    DocumentRequestResponseDTO cancelDocumentRequest(Long documentRequestId, Authentication authentication, ReasonDTO reasonDto); // set status to cancelled
    void deleteDocumentRequest(Long documentRequestId, Authentication authentication, ReasonDTO reasonDto);

    // === UTILS ====
    DocumentRequest fetchDocumentRequestById(Long documentRequestId);
}

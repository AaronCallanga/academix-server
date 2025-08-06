package com.academix.academix.document.request.facade.api;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.request.dto.request.ReasonDTO;
import com.academix.academix.document.request.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseListDTO;
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
}

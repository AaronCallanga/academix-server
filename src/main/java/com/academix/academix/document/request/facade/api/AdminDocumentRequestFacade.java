package com.academix.academix.document.request.facade.api;

import com.academix.academix.document.request.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.request.dto.request.ReasonDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseListDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface AdminDocumentRequestFacade {
    // ==== ADMIN / REGISTRAR ====
    Page<DocumentRequestResponseListDTO> getAllDocumentRequests(int page, int size, String sortField, String sortDirection);
    Page<DocumentRequestResponseListDTO> getUserDocumentRequests(Long userId, int page, int size, String sortField, String sortDirection);
    DocumentRequestResponseDTO approveDocumentRequest(Long documentRequestId, Authentication authentication);
    DocumentRequestResponseDTO rejectDocumentRequest(Long documentRequestId, Authentication authentication, ReasonDTO reasonDto);
    DocumentRequestResponseDTO releaseDocumentRequest(Long documentRequestId, Authentication authentication);
    DocumentRequestResponseDTO setDocumentRequestStatusToReadyForPickup(Long documentRequestId, Authentication authentication);
    DocumentRequestResponseDTO setDocumentRequestStatusToInProgress(Long documentRequestId, Authentication authentication);
    /** @NOTE: Use for FORCE update for status and pick up date  - UI shows current status choice, use this to change the pick-up date */
    DocumentRequestResponseDTO adminUpdateDocumentRequest(Long documentRequestId, DocumentRequestAdminUpdateDTO documentRequestAdminUpdateDTO, Authentication authentication);
}

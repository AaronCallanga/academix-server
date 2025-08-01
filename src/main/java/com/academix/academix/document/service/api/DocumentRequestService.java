package com.academix.academix.document.service.api;

import com.academix.academix.document.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.dto.request.ReasonDTO;
import com.academix.academix.document.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DocumentRequestService {

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

    // ==== FILES / FEEDBACK ====
    //void uploadAuthorization(Long documentRequestId, MultipartFile file); // Not implemented yet
    //void submitFeedback(Long documentRequestId, FeedbackDTO feedbackDTO); // Placeholder for future
    //POST  /api/requests/{id}/upload	Upload ID/authorization letter
    //POST	/api/feedback/{requestId}	Submit feedback after claim

}

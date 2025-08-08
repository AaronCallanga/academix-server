package com.academix.academix.document.request.service.api;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.request.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.request.dto.request.ReasonDTO;
import com.academix.academix.document.request.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.security.entity.Role;
import com.academix.academix.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface DocumentRequestService {

    // ==== ADMIN / REGISTRAR ====
    Page<DocumentRequest> getAllDocumentRequests(PageRequest pageRequest);
    Page<DocumentRequest> getUserDocumentRequests(Long userId, PageRequest pageRequest);
    DocumentRequest approveDocumentRequest(Long documentRequestId);
    DocumentRequest rejectDocumentRequest(Long documentRequestId, ReasonDTO reasonDto);
    DocumentRequest releaseDocumentRequest(Long documentRequestId);
    DocumentRequest setDocumentRequestStatusToReadyForPickup(Long documentRequestId);
    DocumentRequest setDocumentRequestStatusToInProgress(Long documentRequestId);
    /** @NOTE: Use for FORCE update for status and pick up date  - UI shows current status choice, use this to change the pick-up date */
    DocumentRequest adminUpdateDocumentRequest(Long documentRequestId, DocumentRequestAdminUpdateDTO documentRequestAdminUpdateDTO);

    // ==== INDIVIDUAL / STUDENT ====
    Page<DocumentRequest> getOwnDocumentRequests(Authentication authentication, PageRequest pageRequest);

    // ==== COMMON ====
    DocumentRequest getDocumentRequestById(Long documentRequestId);
    //DocumentRequest createDocumentRequest(CreateDocumentRequestDTO documentRequestDTO, User user);
    DocumentRequest updateDocumentRequest(UpdateDocumentRequestDTO documentRequestDTO, Long documentRequestId);
    DocumentRequest cancelDocumentRequest(Long documentRequestId, ReasonDTO reasonDto); // set status to cancelled
    void deleteDocumentRequest(Long documentRequestId, ReasonDTO reasonDto);

    // === UTILS ====
    DocumentRequest fetchDocumentRequestById(Long documentRequestId);
    ActorRole determineActorType(Set<Role> roles);
    // ==== FILES / FEEDBACK ====
    //void uploadAuthorization(Long documentRequestId, MultipartFile file); // Not implemented yet
    //void submitFeedback(Long documentRequestId, FeedbackDTO feedbackDTO); // Placeholder for future
    //POST  /api/requests/{id}/upload	Upload ID/authorization letter
    //POST	/api/feedback/{requestId}	Submit feedback after claim


    DocumentRequest buildDocumentRequest(CreateDocumentRequestDTO dto, User user);
    DocumentRequest save(DocumentRequest request);
}

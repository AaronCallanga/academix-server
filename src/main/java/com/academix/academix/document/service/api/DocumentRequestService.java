package com.academix.academix.document.service.api;

import com.academix.academix.document.dto.DocumentRequestDTO;
import com.academix.academix.document.dto.DocumentRequestListDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DocumentRequestService {
    // Implement pagination
    // Admin roles
    List<DocumentRequestListDTO> getAllDocumentRequests();
    List<DocumentRequestListDTO> getUserDocumentRequests(Long userId);

    // Individual/Student roles
    List<DocumentRequestListDTO> getOwnDocumentRequests(Authentication authentication);

    // General
    DocumentRequestDTO getDocumentRequestById(Long documentRequestId);
    DocumentRequestDTO createDocumentRequest(DocumentRequestDTO documentRequestDTO);
    DocumentRequestDTO updateDocumentRequest(DocumentRequestDTO documentRequestDTO);
    void removeDocumentRequest(Long documentRequestId);
    String cancelDocumentRequest(Long documentRequestId);

    //POST  /api/requests/{id}/upload	Upload ID/authorization letter
    //POST	/api/feedback/{requestId}	Submit feedback after claim


}

package com.academix.academix.document.service.api;

import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DocumentRequestService {
    // Implement pagination
    // Admin roles
    List<DocumentRequestResponseListDTO> getAllDocumentRequests();
    List<DocumentRequestResponseListDTO> getUserDocumentRequests(Long userId);

    // Individual/Student roles
    List<DocumentRequestResponseListDTO> getOwnDocumentRequests(Authentication authentication);

    // General
    DocumentRequestResponseDTO getDocumentRequestById(Long documentRequestId);
    DocumentRequestResponseDTO createDocumentRequest(DocumentRequestResponseDTO documentRequestDTO);
    DocumentRequestResponseDTO updateDocumentRequest(DocumentRequestResponseDTO documentRequestDTO);
    void removeDocumentRequest(Long documentRequestId);
    String cancelDocumentRequest(Long documentRequestId);

    //POST  /api/requests/{id}/upload	Upload ID/authorization letter
    //POST	/api/feedback/{requestId}	Submit feedback after claim

}

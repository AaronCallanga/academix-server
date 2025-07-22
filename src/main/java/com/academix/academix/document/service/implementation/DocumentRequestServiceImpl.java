package com.academix.academix.document.service.implementation;

import com.academix.academix.document.dto.request.DocumentRequestPayloadDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.service.api.DocumentRequestService;
import org.springframework.security.core.Authentication;

import java.util.List;

public class DocumentRequestServiceImpl implements DocumentRequestService {

    @Override
    public List<DocumentRequestResponseListDTO> getAllDocumentRequests() {
        return List.of();
    }

    @Override
    public List<DocumentRequestResponseListDTO> getUserDocumentRequests(Long userId) {
        return List.of();
    }

    @Override
    public List<DocumentRequestResponseListDTO> getOwnDocumentRequests(Authentication authentication) {
        return List.of();
    }

    @Override
    public DocumentRequestResponseDTO getDocumentRequestById(Long documentRequestId) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO createDocumentRequest(DocumentRequestPayloadDTO documentRequestDTO) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO updateDocumentRequest(DocumentRequestPayloadDTO documentRequestDTO) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO cancelDocumentRequest(Long documentRequestId) {
        return null;
    }

    @Override
    public void removeDocumentRequest(Long documentRequestId) {

    }
}

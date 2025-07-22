package com.academix.academix.document.service.implementation;

import com.academix.academix.document.dto.request.DocumentRequestPayloadDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.document.mapper.DocumentRequestMapper;
import com.academix.academix.document.repository.DocumentRequestRepository;
import com.academix.academix.document.service.api.DocumentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRequestServiceImpl implements DocumentRequestService {

    private final DocumentRequestRepository documentRequestRepository;
    private final DocumentRequestMapper documentRequestMapper;

    @Override
    public List<DocumentRequestResponseListDTO> getAllDocumentRequests() {
        // Fetch all the document request
        List<DocumentRequest> documentRequests = documentRequestRepository.findAll();

        // Map the list to List of DTOs and return it
        return documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests);
    }

    @Override
    public List<DocumentRequestResponseListDTO> getUserDocumentRequests(Long userId) {
        // Fetch the document request of the user by its ID
        List<DocumentRequest> documentRequests = documentRequestRepository.findByUserId(userId);

        // Map the list to List of DTOs and return it
        return documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests);
    }

    @Override
    public List<DocumentRequestResponseListDTO> getOwnDocumentRequests(Authentication authentication) {
        // Extract the email from the authenticaiton object
        String email = authentication.getName();

        // Fetch the list of document request by user's email
        List<DocumentRequest> documentRequests = documentRequestRepository.findByUserEmail(email);

        // Map the document requests to list of DTOs and return it
        return documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests);
    }

    @Override
    public DocumentRequestResponseDTO getDocumentRequestById(Long documentRequestId) {
        // Fetch the document request by its ID
        DocumentRequest documentRequest = documentRequestRepository.findById(documentRequestId)
                .orElseThrow(() -> new RuntimeException("DocumentRequest not found with id: " + documentRequestId));

        // Map the document request to DTO then return it
        return documentRequestMapper.toDocumentRequestResponseDTO(documentRequest);
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

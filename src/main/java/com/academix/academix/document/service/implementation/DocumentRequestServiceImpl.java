package com.academix.academix.document.service.implementation;

import com.academix.academix.document.dto.request.DocumentRequestPayloadDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.document.enums.DocumentStatus;
import com.academix.academix.document.mapper.DocumentRequestMapper;
import com.academix.academix.document.repository.DocumentRequestRepository;
import com.academix.academix.document.service.api.DocumentRequestService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRequestServiceImpl implements DocumentRequestService {

    private final DocumentRequestRepository documentRequestRepository;
    private final DocumentRequestMapper documentRequestMapper;
    private final UserRepository userRepository;

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
        List<DocumentRequest> documentRequests = documentRequestRepository.findByRequestedById(userId);

        // Map the list to List of DTOs and return it
        return documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests);
    }

    @Override
    public List<DocumentRequestResponseListDTO> getOwnDocumentRequests(Authentication authentication) {
        // Extract the email from the authenticaiton object
        String email = authentication.getName();

        // Fetch the list of document request by user's email
        List<DocumentRequest> documentRequests = documentRequestRepository.findByRequestedByEmail(email);

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
    public DocumentRequestResponseDTO createDocumentRequest(DocumentRequestPayloadDTO documentRequestDTO, Authentication authentication) {
        // Get the email from sub of authentication object
        String email = authentication.getName();

        // Fetch the authenticated user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // User mapper to map all the available fields from the DTO to document request entity
        DocumentRequest newDocumentRequest = documentRequestMapper.toDocumentRequestEntity(documentRequestDTO);

        // Modify or fill all the remaining fields
        //newDocumentRequest.

//        // Create a new document request object through builder pattern
//        DocumentRequest newDocumentRequest = DocumentRequest.builder()
//                .documentType(documentRequestDTO.getDocumentType())
//                .purpose(documentRequestDTO.getPurpose())
//                .status(DocumentStatus.REQUESTED.name())
//                .requestDate(LocalDateTime.now())
//                .pickUpDate(documentRequestDTO.getPickUpDate())
//                .requestedBy(user)
//                .remarks(documentRequestDTO.getRemarks())
//                .build();
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

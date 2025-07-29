package com.academix.academix.document.service.implementation;

import com.academix.academix.document.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRemark;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.document.enums.DocumentStatus;
import com.academix.academix.document.mapper.DocumentRequestMapper;
import com.academix.academix.document.repository.DocumentRequestRepository;
import com.academix.academix.document.service.api.DocumentRemarkService;
import com.academix.academix.document.service.api.DocumentRequestService;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.repository.UserRepository;
import com.academix.academix.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentRequestServiceImpl implements DocumentRequestService {

    private final DocumentRequestRepository documentRequestRepository;
    private final DocumentRequestMapper documentRequestMapper;
    private final UserRepository userRepository;
    private final DocumentRemarkService documentRemarkService;
    private final UserService userService;

    @Override
    public Page<DocumentRequestResponseListDTO> getAllDocumentRequests(int page, int size, String sortField, String sortDirection) {
        // Build the PageRequest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        // Fetch all the document request
        Page<DocumentRequest> documentRequests = documentRequestRepository.findAll(pageRequest);

        // Convert the paged document requests to List of DTOs (total of 10 by default)
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS = documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests.getContent());

        // Re-build the page object to return the items with mapped to dtos
        return new PageImpl<>(documentRequestResponseListDTOS, pageRequest, documentRequests.getTotalElements());
    }

    @Override
    public Page<DocumentRequestResponseListDTO> getUserDocumentRequests(Long userId, int page, int size, String sortField, String sortDirection) {
        // Build the PageRequest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        // Fetch the document request of the user by its ID
        Page<DocumentRequest> documentRequests = documentRequestRepository.findByRequestedById(userId, pageRequest);


        // Map the list to List of DTOs
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS = documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests.getContent());

        // Re-build the page object to return the items with mapped to dtos
        return new PageImpl<>(documentRequestResponseListDTOS, pageRequest, documentRequests.getTotalElements());
    }

    @Override
    public Page<DocumentRequestResponseListDTO> getOwnDocumentRequests(Authentication authentication, int page, int size, String sortField, String sortDirection) {
        // Extract the email from the authenticaiton object
        String email = authentication.getName();

        // Build the Page Request
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        // Fetch the list of document request by user's email
        Page<DocumentRequest> documentRequests = documentRequestRepository.findByRequestedByEmail(email, pageRequest);

        // Map the document requests to list of DTOs and return it
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS = documentRequestMapper.toDocumentRequestResponseListDTO(documentRequests.getContent());

        // Re-build the page object to return the items with mapped to dtos
        return new PageImpl<>(documentRequestResponseListDTOS, pageRequest, documentRequests.getTotalElements());
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
    public DocumentRequestResponseDTO createDocumentRequest(CreateDocumentRequestDTO documentRequestDTO, Authentication authentication) {

        // Fetch the authenticated user
        User user = userService.getUserFromAuthentication(authentication);

        // User mapper to map all the available fields from the DTO to document request entity
        // This map the DocumentType, Purpose, and PickupDate
        DocumentRequest newDocumentRequest = documentRequestMapper.toDocumentRequestEntity(documentRequestDTO);

        // Modify or fill all the remaining fields excluding remarks
        newDocumentRequest.setStatus(DocumentStatus.REQUESTED);
        newDocumentRequest.setRequestDate(LocalDateTime.now());
        newDocumentRequest.setRequestedBy(user);

        // Now, per each remark's content in the documentRequestDTO, create a DocumentRemark entity and add it to the request
        for (DocumentRemarkRequestDTO documentRemarkRequestDTO : documentRequestDTO.getRemarks()) {
            DocumentRemark documentRemark = documentRemarkService.buildDocumentRemark(documentRemarkRequestDTO.getContent(), user);
            newDocumentRequest.addRemark(documentRemark);  // handle the remark.setRequest(), so setting it to null initially is fine
        }

        // Save the newDocumentRequest without setting the remarks entity
        DocumentRequest savedDocumentRequest = documentRequestRepository.save(newDocumentRequest);

        return documentRequestMapper.toDocumentRequestResponseDTO(savedDocumentRequest);
    }

    @Override
    public DocumentRequestResponseDTO updateDocumentRequest(UpdateDocumentRequestDTO documentRequestDTO, Long documentRequestId) {
        /**
         * @NOTE: After updating, maybe log it in database? just many to one with the document request
         *         - And admin/registrar can see it that the user changed/updated the request in log section
         *         - Or saved the log as remarks, or maybe remarks can have LOG TYPE (other than user/registrar/admin)
         * */

        // Fetch the document request by ID
        DocumentRequest documentRequest = documentRequestRepository.findById(documentRequestId)
                .orElseThrow(() -> new RuntimeException("DocumentRequest not found with id: " + documentRequestId));

        // Update the fields of DocumentRequest entity with the available fields in the DTO using mapper
        documentRequestMapper.updateDocumentRequestEntityFromDTO(documentRequestDTO, documentRequest);

        // Save the changes to the database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Return the savedRequest and mapped it to response DTO
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO approveDocumentRequest(Long documentRequestId, Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO rejectDocumentRequest(Long documentRequestId, Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO releaseDocumentRequest(Long documentRequestId, Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO setDocumentRequestStatusToReadyForPickup(Long documentRequestId,
                                                                               Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO setDocumentRequestStatusToInProgress(Long documentRequestId,
                                                                           Authentication authentication) {
        return null;
    }

    @Override
    public DocumentRequestResponseDTO cancelDocumentRequest(Long documentRequestId) {
        /**
         * @NOTE: After cancelling, maybe log it in database? just many to one with the document request
         *         - And admin/registrar can see it that the user changed/updated the request in log section
         *         - Or saved the log as remarks, or maybe remarks can have LOG TYPE (other than user/registrar/admin)
         * */
        // Fetch the Document Request Entity
        DocumentRequest documentRequest = documentRequestRepository.findById(documentRequestId)
                .orElseThrow(() -> new RuntimeException("DocumentRequest not found with id: " + documentRequestId));

        // Set the status to CANCELLED
        documentRequest.setStatus(DocumentStatus.CANCELLED);

        // Save to database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public void deleteDocumentRequest(Long documentRequestId) {
        // Delete the document request entity by ID
        documentRequestRepository.deleteById(documentRequestId);
    }

}

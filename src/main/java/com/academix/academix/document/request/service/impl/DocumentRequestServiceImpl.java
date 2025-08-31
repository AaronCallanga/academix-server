package com.academix.academix.document.request.service.impl;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.request.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.request.dto.request.ReasonDTO;
import com.academix.academix.document.request.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.enums.ActionPermission;
import com.academix.academix.document.request.enums.DocumentStatus;
import com.academix.academix.document.request.mapper.DocumentRequestMapper;
import com.academix.academix.document.request.repository.DocumentRequestRepository;
import com.academix.academix.document.request.service.api.DocumentRequestService;
import com.academix.academix.exception.types.ConflictException;
import com.academix.academix.exception.types.ResourceNotFoundException;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.security.entity.Role;
import com.academix.academix.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DocumentRequestServiceImpl implements DocumentRequestService {
    private final DocumentRequestRepository documentRequestRepository;
    private final DocumentRequestMapper documentRequestMapper;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<DocumentRequest> getAllDocumentRequests(PageRequest pageRequest) {
        // Fetch all the document request
        return documentRequestRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<DocumentRequest> getUserDocumentRequests(Long userId, PageRequest pageRequest) {
        // Fetch the document request of the user by its ID
        return documentRequestRepository.findByRequestedById(userId, pageRequest);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<DocumentRequest> getOwnDocumentRequests(Authentication authentication, PageRequest pageRequest) {
        // Extract the email from the authenticaiton object
        String email = authentication.getName();

        // Fetch the list of document request by user's email
        return documentRequestRepository.findByRequestedByEmail(email, pageRequest);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public DocumentRequest getDocumentRequestById(Long documentRequestId) {
        // Fetch the document request by its ID
        return fetchDocumentRequestById(documentRequestId);
    }

    @Override
    public DocumentRequest buildDocumentRequest(CreateDocumentRequestDTO dto, User user) {
        DocumentRequest request = documentRequestMapper.toDocumentRequestEntity(dto);
        request.setStatus(DocumentStatus.REQUESTED);
        request.setRequestDate(LocalDateTime.now());
        request.setRequestedBy(user);
        return request;
    }

    @Transactional
    @Override
    public DocumentRequest save(DocumentRequest request) {
        return documentRequestRepository.save(request);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public DocumentRequest updateDocumentRequest(UpdateDocumentRequestDTO documentRequestDTO, Long documentRequestId) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Update the fields of DocumentRequest entity with the available fields in the DTO using mapper
        documentRequestMapper.updateDocumentRequestEntityFromDTO(documentRequestDTO, documentRequest);

        // Save the changes to the database
        return documentRequestRepository.save(documentRequest);
    }

    @Override
    public DocumentRequest approveDocumentRequest(Long documentRequestId) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.APPROVE);

        // Update the status to APPROVED
        documentRequest.setStatus(DocumentStatus.APPROVED);

        // Save to database
        return documentRequestRepository.save(documentRequest);
    }

    @Override
    public DocumentRequest rejectDocumentRequest(Long documentRequestId, ReasonDTO reasonDto) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.REJECT);

        // Update the status to REJECTED
        documentRequest.setStatus(DocumentStatus.REJECTED);

        // Save to database
       return documentRequestRepository.save(documentRequest);
    }

    @Override
    public DocumentRequest releaseDocumentRequest(Long documentRequestId) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.RELEASE);

        // Update the status to RELEASED
        documentRequest.setStatus(DocumentStatus.RELEASED);

        // Save to database
        return documentRequestRepository.save(documentRequest);
    }
    @Override
    public DocumentRequest cancelDocumentRequest(Long documentRequestId, ReasonDTO reasonDto) {
        // Fetch the Document Request Entity
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.CANCEL);

        // Set the status to CANCELLED
        documentRequest.setStatus(DocumentStatus.CANCELLED);

        // Save to database
        return documentRequestRepository.save(documentRequest);
    }

    @Override
    public DocumentRequest setDocumentRequestStatusToReadyForPickup(Long documentRequestId) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.SET_READY_FOR_PICKUP);

        // Update the status to READY_FOR_PICKUP
        documentRequest.setStatus(DocumentStatus.READY_FOR_PICKUP);

        // Save to database
        return documentRequestRepository.save(documentRequest);
    }

    @Override
    public DocumentRequest setDocumentRequestStatusToInProgress(Long documentRequestId) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.SET_IN_PROGRESS);

        // Update the status to READY_FOR_PICKUP
        documentRequest.setStatus(DocumentStatus.IN_PROGRESS);

        // Save to database
        return documentRequestRepository.save(documentRequest);

    }

    @Override
    public DocumentRequest adminUpdateDocumentRequest(Long documentRequestId,
                                                                 DocumentRequestAdminUpdateDTO documentRequestAdminUpdateDTO) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Update the fields
        documentRequest.setStatus(documentRequestAdminUpdateDTO.getStatus());
        documentRequest.setPickUpDate(documentRequestAdminUpdateDTO.getPickUpDate());

        // Save to database
        return documentRequestRepository.save(documentRequest);
    }

    @Override
    public void deleteDocumentRequest(Long documentRequestId, ReasonDTO reasonDto) {
        // Retrieve the document request before deleting it (so you can still log it)
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Delete the document request entity by ID
        documentRequestRepository.deleteById(documentRequestId);
    }

    @Override
    public DocumentRequest fetchDocumentRequestById(Long documentRequestId) {
        return documentRequestRepository.findById(documentRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentRequest not found with id: " + documentRequestId));
    }

    @Override
    public ActorRole determineActorType(Set<Role> roles) {
        List<ActorRole> priorityOrder = List.of(
                ActorRole.ADMIN,
                ActorRole.REGISTRAR,
                ActorRole.STUDENT,
                ActorRole.USER
                                               );

        for (ActorRole actorRole : priorityOrder) {
            for (Role role : roles) {
                if (role.getName().equalsIgnoreCase("ROLE_" + actorRole.name())) {
                    return actorRole;
                }
            }
        }

        return ActorRole.USER; // You can add UNKNOWN in your enum if not already there
    }

    private void validateAction(DocumentRequest request, ActionPermission action) {
        if (!action.isAllowed(request.getStatus())) {
            throw new ConflictException("Cannot " + action.name().toLowerCase().replace("_", " ")
                    + " when status is " + request.getStatus());
        }
    }

}

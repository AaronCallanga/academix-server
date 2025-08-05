package com.academix.academix.document.service.implementation;

import com.academix.academix.document.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.dto.request.ReasonDTO;
import com.academix.academix.document.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.entity.DocumentRemark;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.document.enums.ActionPermission;
import com.academix.academix.document.enums.DocumentStatus;
import com.academix.academix.document.mapper.DocumentRequestMapper;
import com.academix.academix.document.repository.DocumentRequestRepository;
import com.academix.academix.document.service.api.DocumentRemarkService;
import com.academix.academix.document.service.api.DocumentRequestService;
import com.academix.academix.exception.types.ConflictException;
import com.academix.academix.exception.types.ResourceNotFoundException;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import com.academix.academix.security.entity.Role;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentRequestServiceImpl implements DocumentRequestService {
// throw exceptions for wrong status state
    // check for other exceptions to throw
    private final DocumentRequestRepository documentRequestRepository;
    private final DocumentRequestMapper documentRequestMapper;
    private final UserRepository userRepository;
    private final DocumentRemarkService documentRemarkService;
    private final UserService userService;
    private final DocumentRequestAuditService documentRequestAuditService;

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
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

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

        // Log the created request
        documentRequestAuditService.logDocumentRequest(
                savedDocumentRequest,
                determineActorType(user.getRoles()),
                DocumentAction.CREATED,
                "Request Submitted",
                user
                                                      );

        return documentRequestMapper.toDocumentRequestResponseDTO(savedDocumentRequest);
    }

    @Override
    public DocumentRequestResponseDTO updateDocumentRequest(UpdateDocumentRequestDTO documentRequestDTO, Long documentRequestId, Authentication authentication) {
        /**
         * @NOTE: After updating, maybe log it in database? just many to one with the document request
         *         - And admin/registrar can see it that the user changed/updated the request in log section
         *         - Or saved the log as remarks, or maybe remarks can have LOG TYPE (other than user/registrar/admin)
         * */

        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Update the fields of DocumentRequest entity with the available fields in the DTO using mapper
        documentRequestMapper.updateDocumentRequestEntityFromDTO(documentRequestDTO, documentRequest);

        // Save the changes to the database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Log the created request
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                determineActorType(user.getRoles()),
                DocumentAction.UPDATED,
                "User Request Updated",
                user
                                                      );

        // Return the savedRequest and mapped it to response DTO
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO approveDocumentRequest(Long documentRequestId, Authentication authentication) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.APPROVE);

        // Update the status to APPROVED
        documentRequest.setStatus(DocumentStatus.APPROVED);

        // Save to database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                determineActorType(user.getRoles()),
                DocumentAction.APPROVED,
                "Request Approved",
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO rejectDocumentRequest(Long documentRequestId, Authentication authentication, ReasonDTO reasonDto) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.REJECT);

        // Update the status to REJECTED
        documentRequest.setStatus(DocumentStatus.REJECTED);

        // Save to database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                determineActorType(user.getRoles()),
                DocumentAction.REJECTED,
                reasonDto.getReason(),
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO releaseDocumentRequest(Long documentRequestId, Authentication authentication) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.RELEASE);

        // Update the status to RELEASED
        documentRequest.setStatus(DocumentStatus.RELEASED);

        // Save to database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                determineActorType(user.getRoles()),
                DocumentAction.RELEASED,
                "Document Released",
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }
    @Override
    public DocumentRequestResponseDTO cancelDocumentRequest(Long documentRequestId, Authentication authentication, ReasonDTO reasonDto) {
        /**
         * @NOTE: After cancelling, maybe log it in database? just many to one with the document request
         *         - And admin/registrar can see it that the user changed/updated the request in log section
         *         - Or saved the log as remarks, or maybe remarks can have LOG TYPE (other than user/registrar/admin)
         * */
        // Fetch the Document Request Entity
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.CANCEL);

        // Set the status to CANCELLED
        documentRequest.setStatus(DocumentStatus.CANCELLED);

        // Save to database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                determineActorType(user.getRoles()),
                DocumentAction.CANCELLED,
                reasonDto.getReason(),
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO setDocumentRequestStatusToReadyForPickup(Long documentRequestId,
                                                                               Authentication authentication) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.SET_READY_FOR_PICKUP);

        // Update the status to READY_FOR_PICKUP
        documentRequest.setStatus(DocumentStatus.READY_FOR_PICKUP);

        // Save to database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                determineActorType(user.getRoles()),
                DocumentAction.READY_FOR_PICKUP,
                "Ready for Pickup",
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO setDocumentRequestStatusToInProgress(Long documentRequestId,
                                                                           Authentication authentication) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Validate if changing the status is allowed based on the current status
        validateAction(documentRequest, ActionPermission.SET_IN_PROGRESS);

        // Update the status to READY_FOR_PICKUP
        documentRequest.setStatus(DocumentStatus.IN_PROGRESS);

        // Save to database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                determineActorType(user.getRoles()),
                DocumentAction.IN_PROGRESS,
                "Marked as In Progress",
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public DocumentRequestResponseDTO adminUpdateDocumentRequest(Long documentRequestId,
                                                                 DocumentRequestAdminUpdateDTO documentRequestAdminUpdateDTO,
                                                                 Authentication authentication) {
        // Fetch the document request by ID
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Update the fields
        documentRequest.setStatus(documentRequestAdminUpdateDTO.getStatus());
        documentRequest.setPickUpDate(documentRequestAdminUpdateDTO.getPickUpDate());

        // Save to database
        DocumentRequest savedRequest = documentRequestRepository.save(documentRequest);

        // Log the update
        documentRequestAuditService.logDocumentRequest(
                savedRequest,
                determineActorType(user.getRoles()),
                DocumentAction.UPDATED,
                "Admin Forced Updated",
                user
                                                      );

        // Mapped savedReqyest to DTO then return it as a response
        return documentRequestMapper.toDocumentRequestResponseDTO(savedRequest);
    }

    @Override
    public void deleteDocumentRequest(Long documentRequestId, Authentication authentication, ReasonDTO reasonDto) {
        // Get the User from the Authentication Object
        User user = userService.getUserFromAuthentication(authentication);

        // Retrieve the document request before deleting it (so you can still log it)
        DocumentRequest documentRequest = fetchDocumentRequestById(documentRequestId);

        // Delete the document request entity by ID
        documentRequestRepository.deleteById(documentRequestId);

        // Log the action
        documentRequestAuditService.logDocumentRequest(
                documentRequest,
                determineActorType(user.getRoles()),
                DocumentAction.DELETED,
                reasonDto.getReason(),
                user
                                                      );
    }

    @Override
    public DocumentRequest fetchDocumentRequestById(Long documentRequestId) {
        return documentRequestRepository.findById(documentRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentRequest not found with id: " + documentRequestId));
    }

    private ActorRole determineActorType(Set<Role> roles) {
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

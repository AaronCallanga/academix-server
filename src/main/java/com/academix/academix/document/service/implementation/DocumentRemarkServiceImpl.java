package com.academix.academix.document.service.implementation;

import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.entity.DocumentRemark;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.document.mapper.DocumentRemarkMapper;
import com.academix.academix.document.repository.DocumentRemarkRepository;
import com.academix.academix.document.repository.DocumentRequestRepository;
import com.academix.academix.document.service.api.DocumentRemarkService;
import com.academix.academix.security.entity.Role;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DocumentRemarkServiceImpl implements DocumentRemarkService {

    private final DocumentRemarkRepository documentRemarkRepository;
    private final DocumentRemarkMapper documentRemarkMapper;
    private final DocumentRequestRepository documentRequestRepository;
    private final UserRepository userRepository;

    @Override
    public Page<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId, int page, int size, String sortField, String sortDirection) {
        // Define the page requeest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        // Fetch the paged data by ID
        Page<DocumentRemark> documentRemarkList =
                documentRemarkRepository.findByDocumentRequestIdOrderByTimeStampAsc(documentRequestId, pageRequest);

        // Map the paged content (list of document remarks) to DTOs
        List<DocumentRemarkResponseDTO> documentRemarkResponseDTOList =
                documentRemarkMapper.toDocumentRemarkResponseDTOList(documentRemarkList.getContent());

        // Return the paged data
        return new PageImpl<>(documentRemarkResponseDTOList, pageRequest, documentRemarkList.getTotalElements());
    }

    @Override
    public DocumentRemarkResponseDTO updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO, Long documentRemarkId, Long documentRequestId) {

        DocumentRequest documentRequest = documentRequestRepository.findById(documentRequestId)
                                                                   .orElseThrow(() -> new RuntimeException("Document request not found with id: " + documentRequestId));

        DocumentRemark remark = documentRemarkRepository.findById(documentRemarkId)
                .orElseThrow(() -> new RuntimeException("Document remark does not exist with the id : " + documentRemarkId));

        if (!remark.getDocumentRequest().getId().equals(documentRequest.getId())) {
            throw new IllegalArgumentException("Remark does not belong to the given document request");
        }

        String updatedContent = documentRemarkRequestDTO.getContent();
        remark.setContent(updatedContent);
        DocumentRemark updatedRemark = documentRemarkRepository.save(remark);

        return documentRemarkMapper.toDocumentRemarkResponseDTO(updatedRemark);
    }

    @Override
    public DocumentRemarkResponseDTO addRemark(Long documentRequestId, DocumentRemarkRequestDTO remarkRequestDTO, Authentication authentication) {
        // Fetch the document request
        DocumentRequest documentRequest = documentRequestRepository.findById(documentRequestId)
                .orElseThrow(() -> new RuntimeException("Document request does not exist with the id :" + documentRequestId));

        // Extract the content from the DTO
        String content = remarkRequestDTO.getContent();

        // Extract the author from the Authentication object
        String email = authentication.getName();
        // Fetch the user from the database based on the email
        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new RuntimeException("User not found with the email : " + email));

        // Create the remark entity
        DocumentRemark newRemark = buildDocumentRemark(content, user, documentRequest);
        documentRequest.addRemark(newRemark);  // Optional, this only affects the memory

        // Persist the new remark to the database
        DocumentRemark savedRemark = documentRemarkRepository.save(newRemark);

        return documentRemarkMapper.toDocumentRemarkResponseDTO(savedRemark);
    }

    @Override
    public void deleteRemark(Long documentRequestId, Long documentRemarkId) {
        // Fetch the document request
        DocumentRequest documentRequest = documentRequestRepository.findById(documentRequestId)
                                                                   .orElseThrow(() -> new RuntimeException("Document request not found with id: " + documentRequestId));

        // Fetch the remark
        DocumentRemark documentRemark = documentRemarkRepository.findById(documentRemarkId)
                                                                .orElseThrow(() -> new RuntimeException("Document remark not found with id: " + documentRemarkId));

        // Validate that the remark belongs to the request
        if (!documentRemark.getDocumentRequest().getId().equals(documentRequest.getId())) {
            throw new IllegalArgumentException("Remark does not belong to the given document request");
        }

        // Remove the remark from the request (to update the bidirectional relationship)
        documentRequest.removeRemark(documentRemark); // Optional: if you want to maintain consistency
        documentRemark.setDocumentRequest(null); // Optional

        // Delete the remark
        documentRemarkRepository.delete(documentRemark);
    }

    @Override
    public DocumentRemark buildDocumentRemark(String content, User user, DocumentRequest documentRequest) {
        String role = determineHighestPriorityRole(user.getRoles());

        // Build/Create the new remark entity
        DocumentRemark documentRemark = DocumentRemark.builder()
                             .content(content)
                             .author(user)
                             .role(role)
                             .documentRequest(documentRequest)
                             .timeStamp(LocalDateTime.now())
                             .build();

        return documentRemarkRepository.save(documentRemark);

    }

    @Override
    public DocumentRemark buildDocumentRemark(String content, User user) {
        String role = determineHighestPriorityRole(user.getRoles());

        // Build/Create the new remark entity
        return DocumentRemark.builder()
                             .content(content)
                             .author(user)
                             .role(role)
                             .timeStamp(LocalDateTime.now())
                             .build();
    }

    private String determineHighestPriorityRole(Set<Role> roles) {
        List<String> priorities = List.of("ROLE_ADMIN", "ROLE_REGISTRAR", "ROLE_STUDENT");

        // Loop through all the item in the priorities
        for (String priority : priorities) {
            // Nested loop for each item of roles
            for (Role role : roles) {
                if (priority.equals(role.getName())) {
                    return priority.replace("ROLE_", ""); // Converts ROLE_ADMIN -> ADMIN
                }
            }
        }
        return "UNKNOWN";
    }

}

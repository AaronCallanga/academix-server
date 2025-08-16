package com.academix.academix.document.remark.service.implementation;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.remark.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.remark.entity.DocumentRemark;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.remark.repository.DocumentRemarkRepository;
import com.academix.academix.document.remark.service.api.DocumentRemarkService;
import com.academix.academix.exception.types.ResourceNotFoundException;
import com.academix.academix.security.entity.Role;
import com.academix.academix.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DocumentRemarkServiceImpl implements DocumentRemarkService {

    private final DocumentRemarkRepository documentRemarkRepository;

    @Override
    public Page<DocumentRemark> getAllDocumentRemarksByRequestId(Long documentRequestId, PageRequest pageRequest) {
        // Fetch the paged data by ID
        return documentRemarkRepository.findByDocumentRequestId(documentRequestId, pageRequest);
    }

    @Override
    public DocumentRemark updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO, Long documentRemarkId, DocumentRequest documentRequest) {
        DocumentRemark remark = documentRemarkRepository.findById(documentRemarkId)
                .orElseThrow(() -> new ResourceNotFoundException("Document remark does not exist with the id : " + documentRemarkId));

        if (!remark.getDocumentRequest().getId().equals(documentRequest.getId())) {
            throw new IllegalArgumentException("Remark does not belong to the given document request");
        }

        String updatedContent = documentRemarkRequestDTO.getContent();
        remark.setContent(updatedContent);
        return documentRemarkRepository.save(remark);
    }

    @Override
    public DocumentRemark addRemark(DocumentRequest documentRequest, DocumentRemarkRequestDTO remarkRequestDTO, User user) {
        // Extract the content from the DTO
        String content = remarkRequestDTO.getContent();

        // Create the remark entity
        DocumentRemark newRemark = buildDocumentRemark(content, user, documentRequest);
        documentRequest.addRemark(newRemark);  // Optional, this only affects the memory

        // Persist the new remark to the database
        return documentRemarkRepository.save(newRemark);
    }

    @Override
    public void deleteRemark(DocumentRequest documentRequest, Long documentRemarkId) {
        // Fetch the remark
        DocumentRemark documentRemark = documentRemarkRepository.findById(documentRemarkId)
                                                                .orElseThrow(() -> new ResourceNotFoundException("Document remark not found with id: " + documentRemarkId));

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

    @Override
    public void buildDocumentRemarkList(CreateDocumentRequestDTO documentRequestDTO, DocumentRequest newDocumentRequest, User user) {
        for (DocumentRemarkRequestDTO documentRemarkRequestDTO : documentRequestDTO.getRemarks()) {
            DocumentRemark documentRemark = buildDocumentRemark(documentRemarkRequestDTO.getContent(), user);
            newDocumentRequest.addRemark(documentRemark);  // handle the remark.setRequest(), so setting it to null initially is fine
        }
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
        return "USER";
    }

}

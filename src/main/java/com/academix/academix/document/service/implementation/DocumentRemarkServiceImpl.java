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
    public List<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId) {
        List<DocumentRemark> documentRemarkList =
                documentRemarkRepository.findByDocumentRequestIdOrderByTimeStampAsc(documentRequestId);

        List<DocumentRemarkResponseDTO> documentRemarkResponseDTOList =
                documentRemarkMapper.toDocumentRemarkResponseDTOList(documentRemarkList);

        return documentRemarkResponseDTOList;
    }

    @Override
    public DocumentRemarkResponseDTO updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO, Long documentRemarkId) {
        DocumentRemark remark = documentRemarkRepository.findById(documentRemarkId)
                .orElseThrow(() -> new RuntimeException("Document remark does not exist with the id :" + documentRemarkId));

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
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        // Extract User roles based on the highest role it has
        String role = determineHighestPriorityRole(user.getRoles());

        // Build/ Create the new remark entity
        DocumentRemark newRemark = DocumentRemark.builder()
                .content(content)
                .documentRequest(documentRequest)
                .author(user)
                .role(role)
                .timeStamp(LocalDateTime.now())
                .build();

        DocumentRemark savedRemark = documentRemarkRepository.save(newRemark);
        return documentRemarkMapper.toDocumentRemarkResponseDTO(savedRemark);
    }

    @Override
    public void removeRemark(Long documentRequestId, Long remarkRequestId) {

    }

    private String determineHighestPriorityRole(Set<Role> roles) {
        if (roles.contains("ROLE_ADMIN")) return "ADMIN";
        if (roles.contains("ROLE_REGISTRAR")) return "REGISTRAR";
        if (roles.contains("ROLE_STUDENT")) return "STUDENT";
        return "UNKNOWN";
    }
}

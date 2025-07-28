package com.academix.academix.document.service.api;

import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.entity.DocumentRemark;
import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DocumentRemarkService {
    // GENERAL
    Page<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId, int size, int page, String sortField, String sortDirection);

    DocumentRemarkResponseDTO updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO, Long documentRemarkId, Long documentRequestId);

    // ==== REMARKS ====
    DocumentRemarkResponseDTO addRemark(Long documentRequestId, DocumentRemarkRequestDTO remarkRequestDTO, Authentication authentication);
    void deleteRemark(Long documentRequestId, Long documentRemarkId);

    DocumentRemark buildDocumentRemark(String content, User user, DocumentRequest documentRequest);
    DocumentRemark buildDocumentRemark(String content, User user);
}

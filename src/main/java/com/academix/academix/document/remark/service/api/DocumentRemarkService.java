package com.academix.academix.document.remark.service.api;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.remark.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.remark.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.remark.entity.DocumentRemark;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface DocumentRemarkService {
    // GENERAL
    Page<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId, int size, int page, String sortField, String sortDirection);

    DocumentRemarkResponseDTO updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO, Long documentRemarkId, Long documentRequestId);

    // ==== REMARKS ====
    DocumentRemarkResponseDTO addRemark(Long documentRequestId, DocumentRemarkRequestDTO remarkRequestDTO, Authentication authentication);
    void deleteRemark(Long documentRequestId, Long documentRemarkId);

    DocumentRemark buildDocumentRemark(String content, User user, DocumentRequest documentRequest);
    DocumentRemark buildDocumentRemark(String content, User user);

    void buildDocumentRemarkList(CreateDocumentRequestDTO documentRequestDTO, DocumentRequest newDocumentRequest, User user);
}

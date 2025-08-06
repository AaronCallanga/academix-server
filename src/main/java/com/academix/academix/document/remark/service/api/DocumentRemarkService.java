package com.academix.academix.document.remark.service.api;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.remark.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.remark.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.remark.entity.DocumentRemark;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

public interface DocumentRemarkService {
    // GENERAL
    Page<DocumentRemark> getAllDocumentRemarksByRequestId(Long documentRequestId, PageRequest pageRequest);

    DocumentRemark updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO, Long documentRemarkId, DocumentRequest documentRequest);

    // ==== REMARKS ====
    DocumentRemark addRemark(DocumentRequest documentRequest, DocumentRemarkRequestDTO remarkRequestDTO, User user);
    void deleteRemark(DocumentRequest documentRequest, Long documentRemarkId);

    DocumentRemark buildDocumentRemark(String content, User user, DocumentRequest documentRequest);
    DocumentRemark buildDocumentRemark(String content, User user);

    void buildDocumentRemarkList(CreateDocumentRequestDTO documentRequestDTO, DocumentRequest newDocumentRequest, User user);
}

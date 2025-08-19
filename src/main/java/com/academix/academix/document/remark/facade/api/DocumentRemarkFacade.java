package com.academix.academix.document.remark.facade.api;

import com.academix.academix.document.remark.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.remark.dto.response.DocumentRemarkResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface DocumentRemarkFacade {
    // GENERAL
    Page<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId, int page, int size, String sortField, String sortDirection);
    DocumentRemarkResponseDTO updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO, Long documentRemarkId, Long documentRequestId);

    // ==== REMARKS ====
    DocumentRemarkResponseDTO addRemark(Long documentRequestId, DocumentRemarkRequestDTO remarkRequestDTO, Authentication authentication);
    void deleteRemark(Long documentRequestId, Long documentRemarkId);
}

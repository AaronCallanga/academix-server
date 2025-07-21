package com.academix.academix.document.service.api;

import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DocumentRemarkService {
    //  List<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentReqId)
    // General
    List<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId);

    DocumentRemarkResponseDTO updateRemark(DocumentRemarkRequestDTO documentRemarkRequestDTO, Long documentRemarkId);

    // ==== REMARKS ====
    DocumentRemarkResponseDTO addRemark(Long documentRequestId, DocumentRemarkRequestDTO remarkRequestDTO, Authentication authentication);
    void deleteRemark(Long documentRequestId, Long documentRemarkId);
}

package com.academix.academix.document.service.api;

import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;

import java.util.List;

public interface DocumentRemarkService {
    //  List<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentReqId)
    // General
    List<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId);

    DocumentRemarkResponseDTO updateRemark(DocumentRemarkResponseDTO documentRemarkResponseDTO);

    // ==== REMARKS ====
    DocumentRemarkResponseDTO addRemark(Long documentRequestId, DocumentRemarkRequestDTO remarkRequestDTO);
    void removeRemark(Long documentRequestId, Long remarkRequestId);
}

package com.academix.academix.document.service.api;

import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;

import java.util.List;

public interface DocumentRemarkService {
    //  List<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentReqId)
    // General
    List<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(String requestId);
    DocumentRemarkResponseDTO addRemark(DocumentRemarkResponseDTO documentRemarkResponseDTO);
    DocumentRemarkResponseDTO updateRemark(DocumentRemarkResponseDTO documentRemarkResponseDTO);
    void deleteRemarkbyId(Long documentRemarkId);
}

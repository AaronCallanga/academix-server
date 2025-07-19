package com.academix.academix.document.service.api;

import com.academix.academix.document.dto.DocumentRemarkDTO;

import java.util.List;

public interface DocumentRemarkService {
    //  List<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentReqId)
    // General
    List<DocumentRemarkDTO> getAllDocumentRemarksByRequestId(String requestId);
    DocumentRemarkDTO addRemark(DocumentRemarkDTO documentRemarkDTO);
    DocumentRemarkDTO updateRemark(DocumentRemarkDTO documentRemarkDTO);
    void deleteRemarkbyId(Long documentRemarkId);
}

package com.academix.academix.document.service.implementation;

import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.service.api.DocumentRemarkService;

import java.util.List;

public class DocumentRemarkServiceImpl extends DocumentRemarkService {
    @Override
    public List<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(String requestId) {
        return List.of();
    }

    @Override
    public DocumentRemarkResponseDTO updateRemark(DocumentRemarkResponseDTO documentRemarkResponseDTO) {
        return null;
    }

    @Override
    public DocumentRemarkResponseDTO addRemark(Long documentRequestId, DocumentRemarkRequestDTO remarkRequestDTO) {
        return null;
    }

    @Override
    public void removeRemark(Long documentRequestId, Long remarkRequestId) {

    }
}

package com.academix.academix.document.service.implementation;

import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.entity.DocumentRemark;
import com.academix.academix.document.mapper.DocumentRemarkMapper;
import com.academix.academix.document.repository.DocumentRemarkRepository;
import com.academix.academix.document.service.api.DocumentRemarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRemarkServiceImpl implements DocumentRemarkService {

    private final DocumentRemarkRepository documentRemarkRepository;
    private final DocumentRemarkMapper documentRemarkMapper;

    @Override
    public List<DocumentRemarkResponseDTO> getAllDocumentRemarksByRequestId(Long documentRequestId) {
        List<DocumentRemark> documentRemarkList =
                documentRemarkRepository.findByDocumentRequestIdOrderByTimeStampAsc(documentRequestId);

        List<DocumentRemarkResponseDTO> documentRemarkResponseDTOList =
                documentRemarkMapper.toDocumentRemarkResponseDTOList(documentRemarkList);

        return documentRemarkResponseDTOList;
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

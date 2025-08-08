package com.academix.academix.log.facade.impl;

import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.dto.response.DocumentRequestAuditListResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import com.academix.academix.log.facade.api.DocumentRequestAuditFacade;
import com.academix.academix.log.mapper.DocumentRequestAuditMapper;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRequestAuditFacadeImpl implements DocumentRequestAuditFacade {

    private final DocumentRequestAuditService documentRequestAuditService;
    private final DocumentRequestAuditMapper documentRequestAuditMapper;

    @Override
    public Page<DocumentRequestAuditListResponseDTO> getAllDocumentRequestsByRequestId(Long documentRequestId,
                                                                                       int page,
                                                                                       int size,
                                                                                       String sortDirection,
                                                                                       String sortField) {
        // Build the PageRequest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<DocumentRequestAudit> documentRequestAuditList = documentRequestAuditService.getAllDocumentRequestsByRequestId(documentRequestId, pageRequest);
        List<DocumentRequestAuditListResponseDTO> documentRequestAuditDetailResponseDTOList = documentRequestAuditMapper.toDocumentRequestAuditResponseDTOList(documentRequestAuditList.getContent());
        return new PageImpl<>(documentRequestAuditDetailResponseDTOList, pageRequest, documentRequestAuditList.getTotalElements());
    }

    @Override
    public DocumentRequestAuditDetailResponseDTO getDocumentRequestAuditDetails(Long documentRequestId, Long auditId) {
        return null;
    }
}

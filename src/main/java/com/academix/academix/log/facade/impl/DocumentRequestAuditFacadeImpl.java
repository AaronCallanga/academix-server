package com.academix.academix.log.facade.impl;

import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.dto.response.DocumentRequestAuditListResponseDTO;
import com.academix.academix.log.facade.api.DocumentRequestAuditFacade;
import org.springframework.data.domain.Page;

public class DocumentRequestAuditFacadeImpl implements DocumentRequestAuditFacade {
    @Override
    public Page<DocumentRequestAuditListResponseDTO> getAllDocumentRequestsByRequestId(Long documentRequestId,
                                                                                       int page,
                                                                                       int size,
                                                                                       String sortDirection,
                                                                                       String sortField) {
        return null;
    }

    @Override
    public DocumentRequestAuditDetailResponseDTO getDocumentRequestAuditDetails(Long documentRequestId, Long auditId) {
        return null;
    }
}

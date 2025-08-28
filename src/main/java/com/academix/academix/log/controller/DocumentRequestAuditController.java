package com.academix.academix.log.controller;

import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.dto.response.DocumentRequestAuditListResponseDTO;
import com.academix.academix.log.facade.api.DocumentRequestAuditFacade;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR')")
public class DocumentRequestAuditController {
   private final DocumentRequestAuditFacade documentRequestAuditFacade;

    @GetMapping("/request/{requestId}")
    public Page<DocumentRequestAuditListResponseDTO> getAllAuditLogsByRequestId(@PathVariable Long requestId,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "DESC") String sortDirection,
                                                                                @RequestParam(defaultValue = "performedAt") String sortField) {
        return documentRequestAuditFacade.getAllDocumentRequestsByRequestId(requestId, page, size, sortDirection, sortField);
    }

    @GetMapping("/{auditId}/request/{requestId}/")
    public DocumentRequestAuditDetailResponseDTO getDocumentRequestAuditDetailsById(@PathVariable Long auditId, @PathVariable Long requestId) {
        return documentRequestAuditFacade.getDocumentRequestAuditDetails(auditId, requestId);
    }

}
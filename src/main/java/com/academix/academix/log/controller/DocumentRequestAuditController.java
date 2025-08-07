package com.academix.academix.log.controller;

import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class DocumentRequestAuditController {
   private final DocumentRequestAuditService documentRequestAuditService;

    @GetMapping("/{requestId}")
    public Page<DocumentRequestAuditDetailResponseDTO> getAllAuditLogsByRequestId(@PathVariable Long requestId,
                                                                                  @RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size,
                                                                                  @RequestParam(defaultValue = "DESC") String sortDirection,
                                                                                  @RequestParam(defaultValue = "performedAt") String sortField) {
        return documentRequestAuditService.getAllDocumentRequestsByRequestId(requestId, page, size, sortDirection, sortField);
    }

}
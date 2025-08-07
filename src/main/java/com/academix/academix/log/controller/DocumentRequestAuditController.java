package com.academix.academix.log.controller;

import com.academix.academix.log.dto.response.DocumentRequestAuditResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import com.academix.academix.log.repository.DocumentRequestAuditRepository;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class DocumentRequestAuditController {
   private final DocumentRequestAuditService documentRequestAuditService;

    @GetMapping("/{requestId}")
    public Page<DocumentRequestAuditResponseDTO> getAllAuditLogsByRequestId(@PathVariable Long requestId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            @RequestParam(defaultValue = "ASC") String sortDirection,
                                                                            @RequestParam(defaultValue = "requestDate") String sortField) {
        return documentRequestAuditService.getAllDocumentRequestsByRequestId(requestId, page, size, sortDirection, sortField);
    }

}
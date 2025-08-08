package com.academix.academix.log.controller;

import com.academix.academix.log.entity.DocumentRequestAudit;
import com.academix.academix.log.repository.DocumentRequestAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class DocumentRequestAuditController {
    private final DocumentRequestAuditRepository repo;

    @GetMapping
    public List<DocumentRequestAudit> getAll() {
        return repo.findAll();
    }

}
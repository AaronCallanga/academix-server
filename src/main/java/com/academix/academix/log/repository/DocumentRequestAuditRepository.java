package com.academix.academix.log.repository;

import com.academix.academix.log.entity.DocumentRequestAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRequestAuditRepository extends JpaRepository<DocumentRequestAudit, Long> {
    List<DocumentRequestAudit> findByDocumentId(Long documentId);
}

package com.academix.academix.log.repository;

import com.academix.academix.log.entity.DocumentRequestAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRequestAuditRepository extends JpaRepository<DocumentRequestAudit, Long> {
    List<DocumentRequestAudit> findByDocumentRequestId(Long documentRequestId);
}

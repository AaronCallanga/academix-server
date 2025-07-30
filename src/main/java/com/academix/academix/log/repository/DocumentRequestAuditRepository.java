package com.academix.academix.log.repository;

import com.academix.academix.log.entity.DocumentRequestAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRequestAuditRepository extends JpaRepository<DocumentRequestAudit, Long> {
}

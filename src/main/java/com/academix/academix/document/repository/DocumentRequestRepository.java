package com.academix.academix.document.repository;

import com.academix.academix.document.entity.DocumentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
}

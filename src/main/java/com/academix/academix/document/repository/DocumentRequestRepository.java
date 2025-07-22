package com.academix.academix.document.repository;

import com.academix.academix.document.entity.DocumentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    List<DocumentRequest> findByUserId(Long userId);
    List<DocumentRequest> findByUserEmail(String email);
}

package com.academix.academix.document.request.repository;

import com.academix.academix.document.request.entity.DocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    Page<DocumentRequest> findByRequestedById(Long userId, Pageable pageable);
    Page<DocumentRequest> findByRequestedByEmail(String email, Pageable pageable);
}

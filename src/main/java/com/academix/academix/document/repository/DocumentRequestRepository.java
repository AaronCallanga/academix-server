package com.academix.academix.document.repository;

import com.academix.academix.document.entity.DocumentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    List<DocumentRequest> findByRequestedById(Long userId);
    List<DocumentRequest> findByRequestedByEmail(String email);
}

package com.academix.academix.document.repository;

import com.academix.academix.document.entity.DocumentRemark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentRemarkRepository extends JpaRepository<DocumentRemark, Long> {
    //Page<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentRequestId, Pageable pageable);
    Page<DocumentRemark> findByDocumentRequestId(Long documentId, Pageable pageable);
}

package com.academix.academix.document.remark.repository;

import com.academix.academix.document.remark.entity.DocumentRemark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentRemarkRepository extends JpaRepository<DocumentRemark, Long> {
    //Page<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentRequestId, Pageable pageable);  can't do it because we want it to be sorted by pageaRequest
    Page<DocumentRemark> findByDocumentRequestId(Long documentId, Pageable pageable);
}

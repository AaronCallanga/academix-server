package com.academix.academix.document.repository;

import com.academix.academix.document.entity.DocumentRemark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRemarkRepository extends CrudRepository<DocumentRemark, Long> {
    //Page<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentRequestId, Pageable pageable);
    Page<DocumentRemark> findByDocumentRequestId(Long documentId, Pageable pageable);
}

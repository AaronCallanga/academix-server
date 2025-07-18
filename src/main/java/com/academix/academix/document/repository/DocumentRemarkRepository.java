package com.academix.academix.document.repository;

import com.academix.academix.document.entity.DocumentRemark;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRemarkRepository extends CrudRepository<DocumentRemark, Long> {
    List<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentRequestId);
}

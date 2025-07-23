package com.academix.academix.document.repository;

import com.academix.academix.document.entity.DocumentRemark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRemarkRepository extends CrudRepository<DocumentRemark, Long> {
    List<DocumentRemark> findByDocumentRequestIdOrderByTimeStampAsc(Long documentRequestId);
}

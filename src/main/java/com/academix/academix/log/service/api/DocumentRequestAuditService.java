package com.academix.academix.log.service.api;


import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.log.dto.response.DocumentRequestAuditDetailResponseDTO;
import com.academix.academix.log.dto.response.DocumentRequestAuditListResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DocumentRequestAuditService {

    void logDocumentRequest(DocumentRequest documentRequest, ActorRole actorRole, DocumentAction documentAction, String remark, User userOptional);
    Page<DocumentRequestAudit> getAllDocumentRequestsByRequestId(Long documentRequestId, PageRequest pageRequest); // Should be paged by timeStamp latest as default
    DocumentRequestAudit getDocumentRequestAuditDetails(DocumentRequest documentRequest, Long auditId);
}

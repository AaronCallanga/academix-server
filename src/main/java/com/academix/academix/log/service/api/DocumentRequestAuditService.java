package com.academix.academix.log.service.api;


import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.log.dto.response.DocumentRequestAuditResponseDTO;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.user.entity.User;

import java.util.List;

public interface DocumentRequestAuditService {

    void logDocumentRequest(DocumentRequest documentRequest, ActorRole actorRole, DocumentAction documentAction, String remark, User userOptional);
    List<DocumentRequestAuditResponseDTO> getAllDocumentRequestsByRequestId(Long documentRequestId); // Should be paged by timeStamp latest as default
}

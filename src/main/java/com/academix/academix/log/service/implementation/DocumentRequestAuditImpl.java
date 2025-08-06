package com.academix.academix.log.service.implementation;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.log.dto.response.DocumentRequestAuditResponseDTO;
import com.academix.academix.log.entity.DocumentRequestAudit;
import com.academix.academix.log.enums.ActorRole;
import com.academix.academix.log.enums.DocumentAction;
import com.academix.academix.log.mapper.DocumentRequestAuditMapper;
import com.academix.academix.log.repository.DocumentRequestAuditRepository;
import com.academix.academix.log.service.api.DocumentRequestAuditService;
import com.academix.academix.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRequestAuditImpl implements DocumentRequestAuditService {

    private final DocumentRequestAuditRepository documentRequestAuditRepository;
    private final DocumentRequestAuditMapper documentRequestAuditMapper;

    @Override
    public void logDocumentRequest(DocumentRequest documentRequest,
                                                              ActorRole actorRole,
                                                              DocumentAction documentAction,
                                                              String remark,
                                                              User userOptional) {
        DocumentRequestAudit audit = DocumentRequestAudit.builder()
                                                         .documentRequest(documentRequest)
                                                         .performedBy(userOptional) // null if SYSTEM
                                                         .performedAt(LocalDateTime.now())
                                                         .action(documentAction)
                                                         .actorRole(actorRole)
                                                         .remark(remark)
                                                         .build();

        DocumentRequestAudit savedAudit = documentRequestAuditRepository.save(audit);
        //return documentRequestAuditMapper.toDocumentRequestAuditResponseDTO(savedAudit);
    }

    @Override
    public List<DocumentRequestAuditResponseDTO> getAllDocumentRequestsByRequestId(Long documentRequestId) {
        List<DocumentRequestAudit> documentRequestAuditList = documentRequestAuditRepository.findByDocumentRequestId(documentRequestId);
        return documentRequestAuditMapper.toDocumentRequestAuditResponseDTOList(documentRequestAuditList);
    }

}

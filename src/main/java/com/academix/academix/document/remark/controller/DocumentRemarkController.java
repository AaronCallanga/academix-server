package com.academix.academix.document.remark.controller;

import com.academix.academix.document.remark.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.remark.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.remark.facade.api.DocumentRemarkFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/remarks")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'REGISTRAR', 'STUDENT')")
public class DocumentRemarkController {

    private final DocumentRemarkFacade documentRemarkFacade;

    @GetMapping("/{requestId}")
    public ResponseEntity<Page<DocumentRemarkResponseDTO>> getAllDocumentRemarksByRequestId(
            @PathVariable Long requestId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "timeStamp") String sortField
                                                                                           ) {
        Page<DocumentRemarkResponseDTO> documentRemarks = documentRemarkFacade.getAllDocumentRemarksByRequestId(requestId, page, size, sortField, sortDirection);
        return new ResponseEntity<>(documentRemarks, HttpStatus.OK);
    }

    @PatchMapping("/{remarksId}/documents/{requestId}")
    public ResponseEntity<DocumentRemarkResponseDTO> updateDocumentRemark(@Valid @RequestBody DocumentRemarkRequestDTO documentRemarkRequestDTO, @PathVariable Long remarksId, @PathVariable Long requestId) {
        DocumentRemarkResponseDTO documentRemarkResponseDTO = documentRemarkFacade.updateRemark(documentRemarkRequestDTO, remarksId, requestId);
        return new ResponseEntity<>(documentRemarkResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/documents/{requestId}")
    public ResponseEntity<DocumentRemarkResponseDTO> addDocumentRemark(@Valid @RequestBody DocumentRemarkRequestDTO documentRemarkRequestDTO, @PathVariable Long requestId, Authentication authentication) {
        DocumentRemarkResponseDTO documentRemarkResponseDTO = documentRemarkFacade.addRemark(requestId, documentRemarkRequestDTO, authentication);
        return new ResponseEntity<>(documentRemarkResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{remarksId}/documents/{requestId}")
    public ResponseEntity<Void> deleteDocumentRemark(@PathVariable Long remarksId, @PathVariable Long requestId) {
        documentRemarkFacade.deleteRemark(requestId, remarksId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

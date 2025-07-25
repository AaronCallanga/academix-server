package com.academix.academix.document.controller;

import com.academix.academix.document.dto.request.DocumentRemarkRequestDTO;
import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.service.api.DocumentRemarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/remarks")
@RequiredArgsConstructor
public class DocumentRemarkController {

    private final DocumentRemarkService documentRemarkService;

    @GetMapping("/{requestId}")
    public ResponseEntity<List<DocumentRemarkResponseDTO>> getAllDocumentRemarksByRequestId(@PathVariable Long requestId) {
        List<DocumentRemarkResponseDTO> documentRemarks = documentRemarkService.getAllDocumentRemarksByRequestId(requestId);
        return new ResponseEntity<>(documentRemarks, HttpStatus.OK);
    }

    @PatchMapping("/{remarksId}")
    public ResponseEntity<DocumentRemarkResponseDTO> updateDocumentRemark(@RequestBody DocumentRemarkRequestDTO documentRemarkRequestDTO, @PathVariable Long remarksId) {
        DocumentRemarkResponseDTO documentRemarkResponseDTO = documentRemarkService.updateRemark(documentRemarkRequestDTO, remarksId);
        return new ResponseEntity<>(documentRemarkResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/documents/{requestId}")
    public ResponseEntity<DocumentRemarkResponseDTO> addDocumentRemark(@RequestBody DocumentRemarkRequestDTO documentRemarkRequestDTO, @PathVariable Long requestId, Authentication authentication) {
        DocumentRemarkResponseDTO documentRemarkResponseDTO = documentRemarkService.addRemark(requestId, documentRemarkRequestDTO, authentication);
        return new ResponseEntity<>(documentRemarkResponseDTO, HttpStatus.OK);
    }
}

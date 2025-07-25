package com.academix.academix.document.controller;

import com.academix.academix.document.dto.response.DocumentRemarkResponseDTO;
import com.academix.academix.document.service.api.DocumentRemarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        // Fetch the the list by ID
        List<DocumentRemarkResponseDTO> documentRemarks = documentRemarkService.getAllDocumentRemarksByRequestId(requestId);
        return new ResponseEntity<>(documentRemarks, HttpStatus.OK);
    }
}

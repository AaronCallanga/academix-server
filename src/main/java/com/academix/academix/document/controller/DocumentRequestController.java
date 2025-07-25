package com.academix.academix.document.controller;

import com.academix.academix.document.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.service.api.DocumentRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentRequestController {

    private final DocumentRequestService documentRequestService;

    @GetMapping
    public ResponseEntity<List<DocumentRequestResponseListDTO>> getAllDocumentRequests() {
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS =
                documentRequestService.getAllDocumentRequests();

        return new ResponseEntity<>(documentRequestResponseListDTOS, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<DocumentRequestResponseListDTO>> getAllUserDocumentRequests(@PathVariable Long userId) {
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS =
                documentRequestService.getUserDocumentRequests(userId);

        return new ResponseEntity<>(documentRequestResponseListDTOS, HttpStatus.OK);
    }

    @GetMapping("/own")
    public ResponseEntity<List<DocumentRequestResponseListDTO>> getAllOwnDocumentRequests(Authentication authentication) {
        List<DocumentRequestResponseListDTO> documentRequestResponseListDTOS =
                documentRequestService.getOwnDocumentRequests(authentication);

        return new ResponseEntity<>(documentRequestResponseListDTOS, HttpStatus.OK);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<DocumentRequestResponseDTO> getDocumentRequestById(@PathVariable Long requestId) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.getDocumentRequestById(requestId);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DocumentRequestResponseDTO> sendDocumentRequest(@Valid @RequestBody CreateDocumentRequestDTO createDocumentRequestDTO, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.createDocumentRequest(createDocumentRequestDTO, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{requestId}")
    public ResponseEntity<DocumentRequestResponseDTO> updateDocumentRequest(@Valid @RequestBody UpdateDocumentRequestDTO updateDocumentRequestDTO, @PathVariable Long requestId) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.updateDocumentRequest(updateDocumentRequestDTO, requestId);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteDocumentRequest(@PathVariable Long requestId) {
        documentRequestService.deleteDocumentRequest(requestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<DocumentRequestResponseDTO> cancelDocumentRequest(@PathVariable Long requestId) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.cancelDocumentRequest(requestId);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }


}

/*
 When to use <?>:
When you're writing generic controllers or flexible endpoints.
When the response might return different types depending on the situation.

Example:
@GetMapping("/{id}")
public ResponseEntity<?> getById(@PathVariable Long id) {
    Optional<User> user = userService.findById(id);
    if (user.isPresent()) {
        return ResponseEntity.ok(user.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("User not found");
    }
}
 */

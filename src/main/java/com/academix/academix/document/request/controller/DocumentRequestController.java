package com.academix.academix.document.request.controller;

import com.academix.academix.document.request.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.request.dto.request.ReasonDTO;
import com.academix.academix.document.request.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.request.facade.api.DocumentRequestFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
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
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentRequestController {

    private final DocumentRequestFacade documentRequestFacade;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/own")
    public ResponseEntity<Page<DocumentRequestResponseListDTO>> getAllOwnDocumentRequests(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "requestDate") String sortField
                                                                                         ) {
        Page<DocumentRequestResponseListDTO> documentRequestResponseListDTOS =
                documentRequestFacade.getOwnDocumentRequests(authentication, page, size, sortField, sortDirection);

        return new ResponseEntity<>(documentRequestResponseListDTOS, HttpStatus.OK);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<DocumentRequestResponseDTO> getDocumentRequestById(@PathVariable Long requestId) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestFacade.getDocumentRequestById(requestId);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DocumentRequestResponseDTO> sendDocumentRequest(@Valid @RequestBody CreateDocumentRequestDTO createDocumentRequestDTO, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestFacade.createDocumentRequest(createDocumentRequestDTO, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{requestId}")
    public ResponseEntity<DocumentRequestResponseDTO> updateDocumentRequest(@Valid @RequestBody UpdateDocumentRequestDTO updateDocumentRequestDTO, @PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestFacade.updateDocumentRequest(updateDocumentRequestDTO, requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteDocumentRequest(@PathVariable Long requestId, Authentication authentication, @Valid @RequestBody ReasonDTO reasonDto) {
        documentRequestFacade.deleteDocumentRequest(requestId, authentication, reasonDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<DocumentRequestResponseDTO> cancelDocumentRequest(@PathVariable Long requestId, Authentication authentication, @Valid @RequestBody ReasonDTO reasonDto) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestFacade.cancelDocumentRequest(requestId, authentication, reasonDto);
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

package com.academix.academix.document.controller;

import com.academix.academix.document.dto.request.CreateDocumentRequestDTO;
import com.academix.academix.document.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.dto.request.ReasonDTO;
import com.academix.academix.document.dto.request.UpdateDocumentRequestDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.service.api.DocumentRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentRequestController {

    private final DocumentRequestService documentRequestService;

    @GetMapping
    public ResponseEntity<Page<DocumentRequestResponseListDTO>> getAllDocumentRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "requestDate") String sortField
                                                                                      ) {
        Page<DocumentRequestResponseListDTO> documentRequestResponseListDTOS =
                documentRequestService.getAllDocumentRequests(page, size, sortField, sortDirection);

        return new ResponseEntity<>(documentRequestResponseListDTOS, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<DocumentRequestResponseListDTO>> getAllUserDocumentRequests(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "requestDate") String sortField
                                                                                          ) {
        Page<DocumentRequestResponseListDTO> documentRequestResponseListDTOS =
                documentRequestService.getUserDocumentRequests(userId, page, size, sortField, sortDirection);

        return new ResponseEntity<>(documentRequestResponseListDTOS, HttpStatus.OK);
    }

    @GetMapping("/own")
    public ResponseEntity<Page<DocumentRequestResponseListDTO>> getAllOwnDocumentRequests(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "requestDate") String sortField
                                                                                         ) {
        Page<DocumentRequestResponseListDTO> documentRequestResponseListDTOS =
                documentRequestService.getOwnDocumentRequests(authentication, page, size, sortField, sortDirection);

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
    public ResponseEntity<DocumentRequestResponseDTO> updateDocumentRequest(@Valid @RequestBody UpdateDocumentRequestDTO updateDocumentRequestDTO, @PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.updateDocumentRequest(updateDocumentRequestDTO, requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteDocumentRequest(@PathVariable Long requestId, Authentication authentication, @RequestBody ReasonDTO reasonDto) {
        documentRequestService.deleteDocumentRequest(requestId, authentication, reasonDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<DocumentRequestResponseDTO> cancelDocumentRequest(@PathVariable Long requestId, Authentication authentication, ReasonDTO reasonDto) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.cancelDocumentRequest(requestId, authentication, reasonDto);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/approve")
    public ResponseEntity<DocumentRequestResponseDTO> approveDocumentRequest(@PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.approveDocumentRequest(requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/reject")
    public ResponseEntity<DocumentRequestResponseDTO> rejectDocumentRequest(@PathVariable Long requestId, Authentication authentication, ReasonDTO reasonDto) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.rejectDocumentRequest(requestId, authentication, reasonDto);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/release")
    public ResponseEntity<DocumentRequestResponseDTO> releaseDocumentRequest(@PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.releaseDocumentRequest(requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/ready-pickup")
    public ResponseEntity<DocumentRequestResponseDTO> setToReadyForPickupDocumentRequest(@PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.setDocumentRequestStatusToReadyForPickup(requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/in-progress")
    public ResponseEntity<DocumentRequestResponseDTO> setToInProgressDocumentRequest(@PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.setDocumentRequestStatusToInProgress(requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/admin")
    public ResponseEntity<DocumentRequestResponseDTO> adminUpdateDocumentRequest(@PathVariable Long requestId, @Valid @RequestBody DocumentRequestAdminUpdateDTO documentRequestAdminUpdateDTO, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = documentRequestService.adminUpdateDocumentRequest(requestId, documentRequestAdminUpdateDTO, authentication);
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

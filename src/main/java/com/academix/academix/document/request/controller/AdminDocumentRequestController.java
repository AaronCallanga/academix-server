package com.academix.academix.document.request.controller;

import com.academix.academix.document.request.dto.request.DocumentRequestAdminUpdateDTO;
import com.academix.academix.document.request.dto.request.ReasonDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseDTO;
import com.academix.academix.document.request.dto.response.DocumentRequestResponseListDTO;
import com.academix.academix.document.request.facade.api.AdminDocumentRequestFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/documents")
@RequiredArgsConstructor
public class AdminDocumentRequestController {
    private final AdminDocumentRequestFacade adminDocumentRequestFacade;

    @GetMapping
    public ResponseEntity<Page<DocumentRequestResponseListDTO>> getAllDocumentRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "requestDate") String sortField
                                                                                      ) {
        Page<DocumentRequestResponseListDTO> documentRequestResponseListDTOS =
                adminDocumentRequestFacade.getAllDocumentRequests(page, size, sortField, sortDirection);

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
                adminDocumentRequestFacade.getUserDocumentRequests(userId, page, size, sortField, sortDirection);

        return new ResponseEntity<>(documentRequestResponseListDTOS, HttpStatus.OK);
    }



    @PatchMapping("/{requestId}/approve")
    public ResponseEntity<DocumentRequestResponseDTO> approveDocumentRequest(@PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = adminDocumentRequestFacade.approveDocumentRequest(requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/reject")
    public ResponseEntity<DocumentRequestResponseDTO> rejectDocumentRequest(@PathVariable Long requestId, Authentication authentication, @Valid @RequestBody ReasonDTO reasonDto) {
        DocumentRequestResponseDTO documentRequestResponseDTO = adminDocumentRequestFacade.rejectDocumentRequest(requestId, authentication, reasonDto);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/release")
    public ResponseEntity<DocumentRequestResponseDTO> releaseDocumentRequest(@PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = adminDocumentRequestFacade.releaseDocumentRequest(requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/ready-pickup")
    public ResponseEntity<DocumentRequestResponseDTO> setToReadyForPickupDocumentRequest(@PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = adminDocumentRequestFacade.setDocumentRequestStatusToReadyForPickup(requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/in-progress")
    public ResponseEntity<DocumentRequestResponseDTO> setToInProgressDocumentRequest(@PathVariable Long requestId, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = adminDocumentRequestFacade.setDocumentRequestStatusToInProgress(requestId, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/admin")
    public ResponseEntity<DocumentRequestResponseDTO> adminUpdateDocumentRequest(@PathVariable Long requestId, @Valid @RequestBody DocumentRequestAdminUpdateDTO documentRequestAdminUpdateDTO, Authentication authentication) {
        DocumentRequestResponseDTO documentRequestResponseDTO = adminDocumentRequestFacade.adminUpdateDocumentRequest(requestId, documentRequestAdminUpdateDTO, authentication);
        return new ResponseEntity<>(documentRequestResponseDTO, HttpStatus.OK);
    }

}

package com.academix.academix.document.feedback.controller;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import com.academix.academix.document.feedback.facade.api.FeedbackFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackFacade feedbackFacade;

    @PostMapping("/documents/{documentRequestId}")
    public ResponseEntity<FeedbackResponseDTO> submitFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO,
                                                              @PathVariable Long documentRequestId,
                                                              Authentication authentication) {
        FeedbackResponseDTO feedbackResponseDTO = feedbackFacade.submitFeedback(documentRequestId, feedbackRequestDTO, authentication);
        return new ResponseEntity<>(feedbackResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/documents/{documentRequestId}")
    public ResponseEntity<FeedbackResponseDTO> getFeedback(@PathVariable Long documentRequestId) {
        FeedbackResponseDTO feedbackResponseDTO = feedbackFacade.getFeedbackByRequestId(documentRequestId);
        return new ResponseEntity<>(feedbackResponseDTO, HttpStatus.OK);
    }

}

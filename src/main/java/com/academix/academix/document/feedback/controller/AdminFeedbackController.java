package com.academix.academix.document.feedback.controller;

import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import com.academix.academix.document.feedback.facade.api.FeedbackFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/feedbacks")
@RequiredArgsConstructor
public class AdminFeedbackController {

    private final FeedbackFacade feedbackFacade;

    @GetMapping
    public ResponseEntity<Page<FeedbackResponseDTO>> getAllFeedbacks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "submittedAt") String sortField
                                                                    ) {
        Page<FeedbackResponseDTO> feedbacksPageDTO = feedbackFacade.getAllFeedbacks(page, size, sortField, sortDirection);
        return new ResponseEntity<>(feedbacksPageDTO, HttpStatus.OK);
    }

    @GetMapping("/rating")
    public ResponseEntity<Page<FeedbackResponseDTO>> getAllFeedbacksByRating(
            @RequestParam(defaultValue = "5") int rating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "submittedAt") String sortField
                                                                            ) {
        Page<FeedbackResponseDTO> feedbacksPageDTO = feedbackFacade.getFeedbacksByRating(rating, page, size, sortField, sortDirection);
        return new ResponseEntity<>(feedbacksPageDTO, HttpStatus.OK);
    }

    @GetMapping("/rating/average")
    public ResponseEntity<Map<String, Double>> getAlRatingAverage() {
        return new ResponseEntity<>(feedbackFacade.getAverageRatings(), HttpStatus.OK);
    }

    @GetMapping("/rating/distribution")
    public ResponseEntity<Map<Integer, Long>> getAlRatingDistribution() {
        return new ResponseEntity<>(feedbackFacade.getRatingDistribution(), HttpStatus.OK);
    }
}

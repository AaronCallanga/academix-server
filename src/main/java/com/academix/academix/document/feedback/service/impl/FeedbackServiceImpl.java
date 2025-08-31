package com.academix.academix.document.feedback.service.impl;

import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.feedback.repository.FeedbackRepository;
import com.academix.academix.document.feedback.service.api.FeedbackService;
import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.document.request.enums.DocumentStatus;
import com.academix.academix.exception.types.BadRequestException;
import com.academix.academix.exception.types.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Transactional
    @Override
    public Feedback submitFeedback(DocumentRequest documentRequest, Feedback feedbackRequest) {
        // If status is not RELEASED and CANCELLED, throw exception
        if (!documentRequest.getStatus().equals(DocumentStatus.RELEASED) && !documentRequest.getStatus().equals(DocumentStatus.CANCELLED)) {
            throw new BadRequestException("Request is not yet completed");
        }

        // Check if user has already submmited a feedback
        if (feedbackRepository.existsByDocumentRequest_Id(documentRequest.getId())) {
            throw new BadRequestException("Feedback already exists for this document request");
        }

        // FeedbackRequest mapped by mapper in facade has rating, comment, anonymous
        feedbackRequest.setSubmittedAt(LocalDateTime.now());
        feedbackRequest.setDocumentRequest(documentRequest);

        return feedbackRepository.save(feedbackRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public Feedback getFeedbackByRequestId(Long requestId) {
        return feedbackRepository.findByDocumentRequest_Id(requestId)
                                 .orElseThrow(() -> new ResourceNotFoundException("Feedback not found for request ID: " + requestId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Feedback> getAllFeedbacks(PageRequest pageRequest) {
        return feedbackRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Feedback> getFeedbacksByRating(int rating, PageRequest pageRequest) {
        return feedbackRepository.findByRating(rating, pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public Double getAverageRating() {
        Double averageRating = feedbackRepository.findAverageRating();
        return averageRating != null ? averageRating : 0.0;
    }

    @Override
    public Map<Integer, Long> getRatingDistribution() {
        List<Integer> ratings = feedbackRepository.findAllRatings();

        // Count how many per rating 1–5
        Map<Integer, Long> distribution = ratings.stream()
                                                 .collect(Collectors.groupingBy(r -> r, Collectors.counting()));

        // Ensure 1–5 always appear, even if count = 0
        IntStream.rangeClosed(1, 5).forEach(r ->
                        distribution.putIfAbsent(r, 0L)
                                           );
        return distribution;
    }
}

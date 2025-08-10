package com.academix.academix.document.feedback.dto.response;

import com.academix.academix.document.request.entity.DocumentRequest;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class FeedbackResponseDTO {
    private Long id;

    private Long documentRequestId;

    private int rating;

    private String comment;

    private boolean anonymous;

    private LocalDateTime submittedAt;
}

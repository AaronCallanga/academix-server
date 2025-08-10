package com.academix.academix.document.feedback.entity;

import com.academix.academix.document.request.entity.DocumentRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Feedback {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_request_id", nullable = false)
    private DocumentRequest documentRequest;

    @Column(nullable = false) //add validation 1-5 only
    private int rating;

    @Column(length = 500)
    private String comment;

    @Column(nullable = false)
    private boolean anonymous;

    @Column(nullable = false)
    private LocalDateTime submittedAt;
}

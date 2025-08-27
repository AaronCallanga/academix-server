package com.academix.academix.document.feedback.entity;

import com.academix.academix.document.request.entity.DocumentRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_request_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
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

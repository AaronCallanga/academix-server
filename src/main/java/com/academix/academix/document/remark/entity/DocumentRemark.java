package com.academix.academix.document.remark.entity;

import com.academix.academix.document.request.entity.DocumentRequest;
import com.academix.academix.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRemark {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private String role;        // ADMIN, REGISTRAR, STUDENT

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_request_id", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)  // delete remarks when request is deleted, could also handle manually by deleteByRequestId via repo
    private DocumentRequest documentRequest;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;        // User who gives the remarks

    /*
    @PrePersist
    public void prePersist() {
    this.timeStamp = LocalDateTime.now();
}
     */
}

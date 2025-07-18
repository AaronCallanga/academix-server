package com.academix.academix.document.dto;

import com.academix.academix.document.entity.DocumentRequest;
import com.academix.academix.user.entity.User;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentRemarkDTO {
    private Long id;
    private String content;
    private String role;        // ADMIN, REGISTRAR, STUDENT
    private LocalDateTime timeStamp;
    private Long documentRequestId;
    private String authorName;        // User who gives the remarks
}


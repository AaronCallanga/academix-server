package com.academix.academix.document.dto;

import com.academix.academix.user.dto.UserInfoDTO;
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
public class DocumentRemarkResponseDTO {        // Response
    private Long id;
    private String content;
    private String role;        // ADMIN, REGISTRAR, STUDENT
    private LocalDateTime timeStamp;
    private Long documentRequestId;
    private UserInfoDTO author;
}


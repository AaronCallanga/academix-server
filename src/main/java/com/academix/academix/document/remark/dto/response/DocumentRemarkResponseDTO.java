package com.academix.academix.document.remark.dto.response;

import com.academix.academix.user.dto.UserInfoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;
    private UserInfoDTO author;
}


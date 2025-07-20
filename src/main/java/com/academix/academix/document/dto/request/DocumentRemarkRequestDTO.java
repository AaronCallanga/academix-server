package com.academix.academix.document.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentRemarkRequestDTO {     // Both for update/ delete
    private String content;
//    private String role;        // ADMIN, REGISTRAR, STUDENT          get from jwt
}

//     private LocalDateTime timeStamp;
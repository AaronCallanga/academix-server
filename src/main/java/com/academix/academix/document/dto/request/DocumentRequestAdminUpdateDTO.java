package com.academix.academix.document.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentRequestAdminUpdateDTO {

    private String status;  // PENDING, APPROVED, REJECTED

    private LocalDateTime pickUpDate;

//    private List<DocumentRemarkRequestDTO> remarks;
}

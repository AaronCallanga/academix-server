package com.academix.academix.document.dto.request;

import com.academix.academix.document.enums.DocumentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Document status is required")
    private DocumentStatus status;  // PENDING, APPROVED, REJECTED

    @NotNull(message = "Pickup date is required")
    @Future(message = "Pickup date must be in the future")
    private LocalDateTime pickUpDate;

//    private List<DocumentRemarkRequestDTO> remarks;
}

package com.academix.academix.document.request.dto.request;

import com.academix.academix.document.request.enums.DocumentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
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
public class DocumentRequestAdminUpdateDTO {

    @NotNull(message = "Document status is required")
    private DocumentStatus status;  // PENDING, APPROVED, REJECTED

    @NotNull(message = "Pickup date is required")
    @Future(message = "Pickup date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime pickUpDate;

//    private List<DocumentRemarkRequestDTO> remarks;
}

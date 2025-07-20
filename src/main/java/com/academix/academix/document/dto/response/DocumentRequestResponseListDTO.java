package com.academix.academix.document.dto.response;

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
public class DocumentRequestResponseListDTO {  // For list of document request
    private Long id;

    private String documentType; // F-137, Good Moral, etc.

    private String status; // PENDING, APPROVED, REJECTED    can modify by admin/registrar

    private LocalDateTime requestDate;

    private LocalDateTime pickUpDate;       // approved date by admin/registrar

    private UserInfoDTO userInfo;

}

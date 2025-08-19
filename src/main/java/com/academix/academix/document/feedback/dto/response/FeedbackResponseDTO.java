package com.academix.academix.document.feedback.dto.response;

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
public class FeedbackResponseDTO {
    private Long id;

    private Long documentRequestId;

    private int rating;

    private String comment;

    private boolean anonymous;

    private LocalDateTime submittedAt;

    private UserInfoDTO userInfoDTO;        // only shows if anonymous == false
}

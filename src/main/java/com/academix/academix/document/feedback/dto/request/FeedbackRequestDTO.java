package com.academix.academix.document.feedback.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDTO {
    private int rating;
    private String comment;
    private boolean anonymous;

}

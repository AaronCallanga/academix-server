package com.academix.academix.document.feedback.mapper;

import com.academix.academix.document.feedback.dto.request.FeedbackRequestDTO;
import com.academix.academix.document.feedback.dto.response.FeedbackResponseDTO;
import com.academix.academix.document.feedback.entity.Feedback;
import com.academix.academix.document.request.mapper.DocumentRequestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = DocumentRequestMapper.class)
public interface FeedbackMapper {

    FeedbackResponseDTO toFeedbackResponseDTO(Feedback feedback);
    List<FeedbackResponseDTO> toFeedbackResponseListDTO(List<Feedback> feedbacks);

    Feedback toFeedback(FeedbackRequestDTO feedbackRequestDTO);     //   rating;, comment;, anonymous;
    Feedback updateFeedback(FeedbackRequestDTO feedbackRequestDTO, @MappingTarget Feedback feedback);


}

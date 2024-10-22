package com.KoiHealthService.Koi.demo.mapper;


import com.KoiHealthService.Koi.demo.dto.request.feedback.FeedbackCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.feedback.FeedbackUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.FeedbackResponse;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    Feedback toFeedback(FeedbackCreationRequest request);
    FeedbackResponse toFeedbackResponse(Feedback feedback);
    void updateFeedback(@MappingTarget Feedback feedback, FeedbackUpdateRequest request);
}

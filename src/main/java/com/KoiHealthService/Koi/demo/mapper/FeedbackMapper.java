package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.FeedbackRequest;
import com.KoiHealthService.Koi.demo.dto.response.FeedbackResponse;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackResponse toFeedbackResponse(Feedback feedback);

    Feedback toFeedback(FeedbackRequest feedbackRequest);
}

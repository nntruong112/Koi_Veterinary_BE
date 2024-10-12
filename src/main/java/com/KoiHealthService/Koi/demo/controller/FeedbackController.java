package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.FeedbackRequest;
import com.KoiHealthService.Koi.demo.dto.request.UpdateFeedbackRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.FeedbackResponse;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import com.KoiHealthService.Koi.demo.service.FeedbackService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackController {
    @NonNull
    FeedbackService feedbackService;

    @PostMapping()
    public ApiResponse<Feedback> createFeedback(@RequestBody FeedbackRequest feedbackRequest){
        return ApiResponse.<Feedback>builder()
                .result(feedbackService.createFeedback(feedbackRequest))
                .build();
    }
    @GetMapping("/{feedbackId}")
    public FeedbackResponse getById(@PathVariable ("feedbackId") String feedbackId){
        return feedbackService.getFeedbackById(feedbackId);
    }

    @PutMapping()
    public void updateFeedback(@PathVariable String feedbackId, @RequestBody UpdateFeedbackRequest feedbackRequest){
        feedbackService.updateFeedback(feedbackId,feedbackRequest);
    }

    @DeleteMapping("/{feedbackId}")
    public void deleteFeedbackById(@PathVariable String feedbackId){
        feedbackService.deleteFeedback(feedbackId);
    }
}

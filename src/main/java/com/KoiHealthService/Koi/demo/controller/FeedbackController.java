package com.KoiHealthService.Koi.demo.controller;


import com.KoiHealthService.Koi.demo.dto.request.feedback.FeedbackCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.feedback.FeedbackUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import com.KoiHealthService.Koi.demo.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    //đừng để NonNull ở đây, tại sao cũng hong biết nữa, một là để nonnull, 2 là để final
    @NonNull
    private final FeedbackService feedbackService;
    //bắt buộc có final ở đây ngen, kiểu nếu ko có final thì nếu những thành phần liên quan bị null nó hong chạy được

    @PostMapping("/create")
    ApiResponse<Feedback> createFeedback(@RequestBody @Valid FeedbackCreationRequest request) {
        ApiResponse<Feedback> apiResponse = new ApiResponse<>();
        apiResponse.setResult(feedbackService.createFeedback(request));
        return apiResponse;
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable String feedbackId) {
        return feedbackService.getFeedbackById(feedbackId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable String feedbackId,
            @RequestBody FeedbackUpdateRequest request) {
        Feedback updatedFeedback = feedbackService.updateFeedback(feedbackId, request);
        return ResponseEntity.ok(updatedFeedback);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable String feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.noContent().build();
    }



}

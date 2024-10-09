package com.KoiHealthService.Koi.demo.controller;


import com.KoiHealthService.Koi.demo.dto.request.FeedbackCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import com.KoiHealthService.Koi.demo.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

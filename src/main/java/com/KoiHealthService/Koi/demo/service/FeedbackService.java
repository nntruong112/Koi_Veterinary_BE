package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.FeedbackRequest;
import com.KoiHealthService.Koi.demo.dto.response.FeedbackResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.FeedbackMapper;
import com.KoiHealthService.Koi.demo.mapper.UserMapper;
import com.KoiHealthService.Koi.demo.repository.FeedbackRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {
    @NonNull
    FeedbackRepository feedbackRepository;

     @NonNull
     UserMapper userMapper;

    @NonNull
    FeedbackMapper feedbackMapper;

    @NonNull
    UserRepository userRepository;

    public Feedback createFeedback(FeedbackRequest  feedbackRequest){
        User user = userRepository.findById(feedbackRequest.getId())
                .orElseThrow(() -> new AnotherException(ErrorCode.USER_NOT_EXISTED));


       Feedback feedback = Feedback.builder()
               .comment(feedbackRequest.getComment())
               .rating(feedbackRequest.getRating())
               .customer(user)
               .build();

        return feedbackRepository.save(feedback);
    }


    public List<Feedback> getAllFeedBack(){
        return feedbackRepository.findAll();
    }

    public Feedback getById(String feedbackId){
        return feedbackRepository.getById(feedbackId);
    }
}

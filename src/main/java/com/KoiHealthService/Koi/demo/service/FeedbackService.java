package com.KoiHealthService.Koi.demo.service;


import com.KoiHealthService.Koi.demo.dto.request.feedback.FeedbackCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.feedback.FeedbackUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.FeedbackResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.FeedbackMapper;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import com.KoiHealthService.Koi.demo.repository.FeedbackRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeedbackService {


    private final FeedbackRepository feedbackRepository;

    private final FeedbackMapper feedbackMapper;


    private final UserRepository userRepository;


    private final AppointmentRepository appointmentRepository;


    private Feedback feedback;

    private Appointment appointment;
    
    private User customer;


    public Feedback createFeedback(FeedbackCreationRequest request) {

        appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));

        customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));

        feedback = Feedback.builder()
                .feedbackId(request.getFeedbackId())
                .comment(request.getComment())
                .rating(request.getRating())
                .appointment(appointment)
                .customer(customer)
                .build();

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(String feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    public Feedback updateFeedback(String feedbackId, FeedbackUpdateRequest request) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_FEEDBACK_FOUND));

        FeedbackResponse feedbackResponse = feedbackMapper.toFeedbackResponse(feedback);
        

        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(String feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }


}

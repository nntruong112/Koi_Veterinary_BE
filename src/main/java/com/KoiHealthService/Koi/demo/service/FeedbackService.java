package com.KoiHealthService.Koi.demo.service;


import com.KoiHealthService.Koi.demo.dto.request.FeedbackCreationRequest;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import com.KoiHealthService.Koi.demo.repository.FeedbackRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedbackService {


    private final FeedbackRepository feedbackRepository;


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
                
}

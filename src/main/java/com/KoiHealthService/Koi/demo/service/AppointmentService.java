package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.AppointmentRequest;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.AppointmentMapper;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@NonNull
public class AppointmentService {

    @NonNull
    private final AppointmentMapper appointmentMapper;
    private final FishRepository fishRepository;
    @NonNull
    private final AppointmentRepository appointmentRepository; //giao tiếp với repos
    private final UserRepository userRepository;

    private Appointment appointment;
    private Fish fish;
    private User customer;
    private User veterinarian;

    public Appointment createAppointment(AppointmentRequest request){
        customer = userRepository.findById(request.getCustomerId())                           
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));
        
        veterinarian = userRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));

        fish = fishRepository.findById(request.getFishId())
                .orElseThrow(()-> new AnotherException(ErrorCode.NO_FISH_FOUND));


        appointment = Appointment.builder()
                .appointmentId(request.getAppointmentId())
                .appointmentDate(request.getAppointmentDate())
                .appointmentType(request.getAppointmentType())
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .startTime(request.getStartTime())
                .status(request.getStatus())
                .customer(customer)
                .fish(fish)
                .veterinarian(veterinarian)
                .build();

        return appointmentRepository.save(appointment);
        
    }

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    
}

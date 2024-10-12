package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@NonNull
public class AppointmentService {

    @NonNull
    private final AppointmentMapper appointmentMapper;

    @NonNull
    private final FishRepository fishRepository;
    @NonNull
    private final AppointmentRepository appointmentRepository; //giao tiếp với repos

    @NonNull
    private final UserRepository userRepository;

    private Appointment appointment;
    private Fish fish;
    private User customer;
    private User veterinarian;


    //create appointment====================================================================================
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

    //Get all appointment=======================================================================================
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    //Update appointment======================================================================================

    public AppointmentResponse updateAppointment(String appointmentId ,AppointmentUpdateRequest request) {
        //fetch appointment by id
        appointment = appointmentRepository.findById(appointmentId).orElseThrow(()-> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));
        //
        appointmentMapper.toUpdateAppointment(appointment,request);
        //save
        return  appointmentMapper.toAppointmentResponse(appointmentRepository.save(appointment));

    }

    //Get appointment by customerId
    public List<AppointmentResponse> getAppointmentsByUserId(String customerId) {
        customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("invalid user id"));
        List<Appointment> appointments = appointmentRepository.findAppointmentsByCustomerId(customerId);
        return appointments.stream()
                .map(appointmentMapper::toAppointmentResponse) // Map each appointment to AppointmentResponse
                .collect(Collectors.toList()); // Collect results into a List
    }

    
}

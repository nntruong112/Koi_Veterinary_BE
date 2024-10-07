package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.AppointmentRequest;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.mapper.AppointmentMapper;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@NonNull
public class AppointmentService {

    @NonNull
    private final AppointmentMapper appointmentMapper;
    @NonNull
    private AppointmentRepository appointmentRepository; //giao tiếp với repos

    private Appointment appointment;
    private Fish fish;
    private User customer;
    private User veterinarian;

    @NonNull
    public Appointment createAppointment(AppointmentRequest request){

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
}

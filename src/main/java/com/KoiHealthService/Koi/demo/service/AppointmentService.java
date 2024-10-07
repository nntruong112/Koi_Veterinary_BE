package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.AppointmentRequest;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Fish;
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
    @NonNull
    public Appointment createAppointment(AppointmentRequest request){
    
        @NonNull
        Appointment appointment = appointmentMapper.toAppointment(request);

        return appointmentRepository.save(appointment);
        
    }
}
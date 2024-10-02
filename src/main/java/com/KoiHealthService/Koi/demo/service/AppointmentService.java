package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.AppointmentRequest;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository; //giao tiếp với repos

    public Appointment createAppointment(AppointmentRequest request){
        Appointment appointment = new Appointment();

        
        appointment.setAppointmentType(request.getAppointmentType());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setFish(request.getFishId());
        appointment.setCustomer(request.getCustomerId());
        appointment.setVeterinarian(request.getVeterinarianId());
        appointment.setLocation(request.getLocation());

        return appointmentRepository.save(appointment);
        
    }
}

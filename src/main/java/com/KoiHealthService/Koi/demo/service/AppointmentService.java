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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentMapper appointmentMapper;
    private final FishRepository fishRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    

    public Appointment createAppointment(AppointmentRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));
        User veterinarian = userRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));
        Fish fish = fishRepository.findById(request.getFishId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_FOUND));

        Appointment appointment = Appointment.builder()
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

    public AppointmentResponse updateAppointment(String appointmentId, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));

        appointmentMapper.toUpdateAppointment(appointment, request);
        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(appointment);
    }

    public void deleteAppointment(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    public List<AppointmentResponse> getAppointmentsByCustomerId(String customerId) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByCustomerId(customerId);
        return appointments.stream()
                .map(appointmentMapper::toAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponse> getAppointmentsByVetId(String vetId) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByVetId(vetId);
        return appointments.stream()
                .map(appointmentMapper::toAppointmentResponse)
                .collect(Collectors.toList());
    }
}

package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.AppointmentType;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.AppointmentMapper;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import com.KoiHealthService.Koi.demo.repository.AppointmentTypeRepository;
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
    private final AppointmentTypeRepository appointmentTypeRepository;
    

    //create new Appointment ========================================================================================
    public Appointment createAppointment(AppointmentRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));
        User veterinarian = userRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));
        Fish fish = fishRepository.findById(request.getFishId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_FOUND));

        // Manually fetch AppointmentType from repository
        AppointmentType appointmentType = appointmentTypeRepository.findById(request.getAppointmentTypeId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_TYPE_FOUND));

        Appointment appointment = Appointment.builder()
                .appointmentId(request.getAppointmentId())
                .appointmentDate(request.getAppointmentDate())
                .appointmentType(appointmentType)
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

    //get all Appointment ========================================================================================
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }


    //update Appointment ========================================================================================
    public AppointmentResponse updateAppointment(String appointmentId, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));

        appointmentMapper.toUpdateAppointment(appointment, request);
        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(appointment);
    }

    //delete Appointment ========================================================================================
    public void deleteAppointment(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

//    //get Appointment by customer Id ========================================================================================
//    public List<AppointmentResponse> getAppointmentsByCustomerId(String customerId) {
//        // Fetch appointments by customerId
//        List<Appointment> appointments = appointmentRepository.findAppointmentsByCustomerId(customerId);
//
//
//        // Print out appointment data for debugging
//        appointments.forEach(a -> {
//            System.out.println("Appointment ID: " + a.getAppointmentId());
//            System.out.println("Customer ID: " + (a.getCustomer() != null ? a.getCustomer().getUserId() : "null"));
//            System.out.println("Veterinarian ID: " + (a.getVeterinarian() != null ? a.getVeterinarian().getUserId() : "null"));
//            System.out.println("Fish ID: " + (a.getFish() != null ? a.getFish().getFishId() : "null"));
//            System.out.println("Appointment Type ID: " + (a.getAppointmentType() != null ? a.getAppointmentType().getAppointmentTypeId() : "null"));
//        });
//        // Map to response DTOs
//        return appointments.stream()
//                .map(appointmentMapper::toAppointmentResponse)
//                .collect(Collectors.toList());
//    }

    public List<AppointmentResponse> getAppointmentsByCustomerId(String customerId) {
        // Fetch appointments by customerId
        List<Appointment> appointments = appointmentRepository.findAppointmentsByCustomerId(customerId);

        // Map to response DTOs using the builder pattern directly
        return appointments.stream()
                .map(appointment -> AppointmentResponse.builder()
                        .appointmentId(appointment.getAppointmentId())
                        .appointmentDate(appointment.getAppointmentDate())
                        .status(appointment.getStatus())
                        .location(appointment.getLocation())
                        .startTime(appointment.getStartTime())
                        .endTime(appointment.getEndTime())
                        // Map related entities
                        .customerId(appointment.getCustomer() != null ? appointment.getCustomer().getUserId() : null)
                        .veterinarianId(appointment.getVeterinarian() != null ? appointment.getVeterinarian().getUserId() : null)
                        .fishId(appointment.getFish() != null ? appointment.getFish().getFishId() : null)
                        .appointmentTypeId(appointment.getAppointmentType() != null ? appointment.getAppointmentType().getAppointmentTypeId() : null)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<AppointmentResponse> getAppointmentsByVeterinarianId(String veterinarianId) {
        // Fetch appointments by veterinarianId
        List<Appointment> appointments = appointmentRepository.findAppointmentsByVeterinarianId(veterinarianId);

        // Map to response DTOs using the builder pattern directly
        return appointments.stream()
                .map(appointment -> AppointmentResponse.builder()
                        .appointmentId(appointment.getAppointmentId())
                        .appointmentDate(appointment.getAppointmentDate())
                        .status(appointment.getStatus())
                        .location(appointment.getLocation())
                        .startTime(appointment.getStartTime())
                        .endTime(appointment.getEndTime())
                        // Map related entities
                        .customerId(appointment.getCustomer() != null ? appointment.getCustomer().getUserId() : null)
                        .veterinarianId(appointment.getVeterinarian() != null ? appointment.getVeterinarian().getUserId() : null)
                        .fishId(appointment.getFish() != null ? appointment.getFish().getFishId() : null)
                        .appointmentTypeId(appointment.getAppointmentType() != null ? appointment.getAppointmentType().getAppointmentTypeId() : null)
                        .build()
                )
                .collect(Collectors.toList());
    }
}

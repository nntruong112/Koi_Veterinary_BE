package com.KoiHealthService.Koi.demo.service;


import com.KoiHealthService.Koi.demo.dto.request.AppointmentTypeCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentTypeResponse;
import com.KoiHealthService.Koi.demo.entity.AppointmentType;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.AppointmentTypeMapper;
import com.KoiHealthService.Koi.demo.repository.AppointmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentTypeService {
    private final AppointmentTypeRepository appointmentTypeRepository;

    // Create Appointment Type
    public AppointmentTypeResponse createAppointmentType(AppointmentTypeCreationRequest request) {
        // Build the AppointmentType entity from the request
        AppointmentType appointmentType = AppointmentType.builder()
                .appointmentService(request.getAppointmentService())
                .price(request.getPrice())
                .build();

        // Save the entity to the repository
        AppointmentType savedAppointmentType = appointmentTypeRepository.save(appointmentType);

        // Build and return the response
        return AppointmentTypeResponse.builder()
                .appointmentTypeId(savedAppointmentType.getAppointmentTypeId())
                .appointmentService(savedAppointmentType.getAppointmentService())
                .price(Double.valueOf(savedAppointmentType.getPrice()))
                .build();
    }

    // Get all Appointment Types
    public List<AppointmentTypeResponse> getAllAppointmentTypes() {
        List<AppointmentType> appointmentTypes = appointmentTypeRepository.findAll();
        // Map entities to response DTOs using builder
        return appointmentTypes.stream()
                .map(appointmentType -> AppointmentTypeResponse.builder()
                        .appointmentTypeId(appointmentType.getAppointmentTypeId())
                        .appointmentService(appointmentType.getAppointmentService())
                        .price(Double.valueOf(appointmentType.getPrice()))
                        .build())
                .collect(Collectors.toList());
    }

    // Get Appointment Type by ID
    public AppointmentTypeResponse getAppointmentTypeById(String id) {
        // Find the appointment type by ID
        AppointmentType appointmentType = appointmentTypeRepository.findById(id)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_TYPE_FOUND));

        // Build and return the response
        return AppointmentTypeResponse.builder()
                .appointmentTypeId(appointmentType.getAppointmentTypeId())
                .appointmentService(appointmentType.getAppointmentService())
                .price(Double.valueOf(appointmentType.getPrice()))
                .build();
    }

//    // Update Appointment Type
//    public AppointmentTypeResponse updateAppointmentType(String id, AppointmentTypeCreationRequest request) {
//        // Find the existing appointment type by ID
//        AppointmentType updatedAppointmentType = appointmentTypeRepository.findById(id)
//                .map(existing -> {
//                    existing.setAppointmentService(request.getAppointmentService());
//                    existing.setPrice(request.getPrice());
//                    return appointmentTypeRepository.save(existing); // Save the updated entity
//                })
//                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_TYPE_FOUND));
//
//        // Build and return the response
//        return AppointmentTypeResponse.builder()
//                .appointmentTypeId(updatedAppointmentType.getAppointmentTypeId())
//                .appointmentService(updatedAppointmentType.getAppointmentService())
//                .price(updatedAppointmentType.getPrice())
//                .build();
//    }

    // Delete Appointment Type
    public void deleteAppointmentType(String id) {
        // Delete the appointment type by ID
        appointmentTypeRepository.deleteById(id);
    }
}

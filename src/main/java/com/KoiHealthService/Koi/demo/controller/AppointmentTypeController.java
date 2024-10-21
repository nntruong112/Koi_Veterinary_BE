package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.appointmentType.AppointmentTypeCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentTypeResponse;
import com.KoiHealthService.Koi.demo.entity.AppointmentType;
import com.KoiHealthService.Koi.demo.service.AppointmentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment_types")
@RequiredArgsConstructor
public class AppointmentTypeController {

    private final AppointmentTypeService appointmentTypeService;

    @GetMapping
    public ResponseEntity<List<AppointmentTypeResponse>> getAllAppointmentTypes() {
        List<AppointmentTypeResponse> appointmentTypes = appointmentTypeService.getAllAppointmentTypes();
        return ResponseEntity.ok(appointmentTypes);  // Returns 200 OK
    }

    @GetMapping("/{appointmentTypeId}")
    public ResponseEntity<AppointmentTypeResponse> getAppointmentTypeById(@PathVariable("appointmentTypeId") String appointmentTypeId) {
        AppointmentTypeResponse response = appointmentTypeService.getAppointmentTypeById(appointmentTypeId);
        return ResponseEntity.ok(response);  // Returns 200 OK
    }

    @PostMapping("/create")
    public ResponseEntity<AppointmentTypeResponse> createAppointmentType(@RequestBody @Valid AppointmentTypeCreationRequest request) {
        AppointmentTypeResponse response = appointmentTypeService.createAppointmentType(request);
        return ResponseEntity.status(201).body(response);  // Returns 201 Created
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointmentType(@PathVariable String id) {
        try {
            appointmentTypeService.deleteAppointmentType(id);
            return ResponseEntity.noContent().build();  // Returns 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // Returns 404 Not Found if not found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentType> updateAppointmentType(
            @PathVariable(value = "id") String appointmentTypeId,
            @RequestBody AppointmentType appointmentTypeDetails) {
        AppointmentType updatedAppointmentType = appointmentTypeService.updateAppointmentType(appointmentTypeId, appointmentTypeDetails);
        return ResponseEntity.ok(updatedAppointmentType);
    }
}

////package com.KoiHealthService.Koi.demo.controller;

package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentTypeResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Appointment>> createAppointment(@RequestBody @Valid AppointmentRequest request) {
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.createAppointment(request));
        return ResponseEntity.ok(apiResponse);
    }


    //Get all appointment
    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments() {
        return ResponseEntity.ok(appointmentService.getAppointments());
    }

    @GetMapping("{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") String appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointment);
    }

    //update appointment by Id
    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable("appointmentId") String appointmentId,
            @RequestBody AppointmentUpdateRequest request) {
        AppointmentResponse response = appointmentService.updateAppointment(appointmentId, request);
        return ResponseEntity.ok(response);
    }

    //delete
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("appointmentId") String appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    //get appointment by customerId
    @GetMapping("/belonged_to_customerId/{customerId}")
    public ResponseEntity<List<Appointment>> getAppointmentByCustomerId(@PathVariable("customerId") String customerId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByCustomerId(customerId));
    }

    //get appointment by vetId
    @GetMapping("/belonged_to_vetId/{vetId}")
    public ResponseEntity<List<Appointment>> getAppointmentByVetId(@PathVariable("vetId") String vetId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByVeterinarianId(vetId));
    }

    @GetMapping("/count")
    public Long countAppointments() {
        return appointmentService.countAppointments();
    }

    @GetMapping("/total-income")
    public Long getTotalIncome() {
        return appointmentService.calculateTotalIncome();
    }

    @GetMapping("/fish-in-appointments/{appointmentId}")
    public ResponseEntity<Fish> findFishByAppointmentId(@PathVariable String appointmentId) {
        Fish fish = appointmentService.findFishByAppointmentId(appointmentId);
        return ResponseEntity.ok(fish);
    }

    @GetMapping("/count/{appointmentTypeId}")
    public ResponseEntity<Long> countAppointmentsByType(@PathVariable String appointmentTypeId) {
        Long count = appointmentService.getAppointmentCountByType(appointmentTypeId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON) // Set the content type to JSON
                .body(count);
    }
}
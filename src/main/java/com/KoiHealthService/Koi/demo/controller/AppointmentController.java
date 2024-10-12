//////package com.KoiHealthService.Koi.demo.controller;
//
//package com.KoiHealthService.Koi.demo.controller;
//
//import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentRequest;
//import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentUpdateRequest;
//import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
//import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
//import com.KoiHealthService.Koi.demo.entity.Appointment;
//import com.KoiHealthService.Koi.demo.service.AppointmentService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/appointments")
//@RequiredArgsConstructor
//public class AppointmentController {
//
//    private final AppointmentService appointmentService;
//
//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse<Appointment>> createAppointment(@RequestBody @Valid AppointmentRequest request) {
//        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(appointmentService.createAppointment(request));
//        return ResponseEntity.ok(apiResponse);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Appointment>> getAppointments() {
//        return ResponseEntity.ok(appointmentService.getAppointments());
//    }
//
//    @PutMapping("/{appointmentId}")
//    public ResponseEntity<AppointmentResponse> updateAppointment(
//            @PathVariable("appointmentId") String appointmentId,
//            @RequestBody AppointmentUpdateRequest request) {
//        AppointmentResponse response = appointmentService.updateAppointment(appointmentId, request);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{appointmentId}")
//    public ResponseEntity<Void> deleteAppointment(@PathVariable("appointmentId") String appointmentId) {
//        appointmentService.deleteAppointment(appointmentId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/belonged_to_customerId/{customerId}")
//    public ResponseEntity<List<AppointmentResponse>> getAppointmentByCustomerId(@PathVariable("customerId") String customerId) {
//        return ResponseEntity.ok(appointmentService.getAppointmentsByCustomerId(customerId));
//    }
//
//    @GetMapping("/belonged_to_vetId/{vetId}")
//    public ResponseEntity<List<AppointmentResponse>> getAppointmentByVetId(@PathVariable("vetId") String vetId) {
//        return ResponseEntity.ok(appointmentService.getAppointmentsByVetId(vetId));
//    }
//}
package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Appointment>> createAppointment(@RequestBody @Valid AppointmentRequest request) {
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.createAppointment(request));
        apiResponse.setMessage("Appointment created successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments() {
        List<Appointment> appointments = appointmentService.getAppointments();
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(
            @PathVariable("appointmentId") String appointmentId,
            @RequestBody AppointmentUpdateRequest request) {
        AppointmentResponse response = appointmentService.updateAppointment(appointmentId, request);
        ApiResponse<AppointmentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(response);
        apiResponse.setMessage("Appointment updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable("appointmentId") String appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Appointment deleted successfully");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/belonged_to_customerId/{customerId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentByCustomerId(
            @PathVariable("customerId") String customerId) {
        List<AppointmentResponse> responseList = appointmentService.getAppointmentsByCustomerId(customerId);
        ApiResponse<List<AppointmentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(responseList);
        apiResponse.setMessage("Appointments fetched successfully for customer ID: " + customerId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/belonged_to_vetId/{vetId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentByVetId(
            @PathVariable("vetId") String vetId) {
        List<AppointmentResponse> responseList = appointmentService.getAppointmentsByVetId(vetId);
        ApiResponse<List<AppointmentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(responseList);
        apiResponse.setMessage("Appointments fetched successfully for veterinarian ID: " + vetId);
        return ResponseEntity.ok(apiResponse);
    }
}

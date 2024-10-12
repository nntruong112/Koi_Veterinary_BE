package com.KoiHealthService.Koi.demo.controller;


import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    @NonNull
    private AppointmentService appointmentService;

    @PostMapping("/create") //endpoint đặt có s vì nó là invention trong việc đặt tên API
    ApiResponse<Appointment> createAppointment (@RequestBody @Valid AppointmentRequest request){
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.createAppointment(request));
        return apiResponse;
    }

    @GetMapping()
    ResponseEntity<List<Appointment>> getAppointments(){
        return ResponseEntity.ok(appointmentService.getAppointments());
    }

    //update Appointment===============================================================================================
    @PutMapping("/{appointmentId}")
    AppointmentResponse updateAppointment(@PathVariable("appointmentId")  String appointmentId, @RequestBody AppointmentUpdateRequest request){
        return appointmentService.updateAppointment(appointmentId,request);

        //return null;
    }

    //get appointment by customerId
    @GetMapping("/belonged_to_customerId/{customerId}")
    public List<AppointmentResponse>  getAppointmentByCustomerId(@PathVariable("customerId") String customerId){
        return appointmentService.getAppointmentsByUserId(customerId);
    }
    


    
}

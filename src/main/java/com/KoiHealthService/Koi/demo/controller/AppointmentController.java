package com.KoiHealthService.Koi.demo.controller;


import com.KoiHealthService.Koi.demo.dto.request.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping() //endpoint users voi method POST, users đặt có s vì nó là invention trong việc đặt tên API
    ApiResponse<Appointment> createAppointment (@RequestBody @Valid AppointmentRequest request){
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.createAppointment(request));
        return apiResponse;
    }

//    @GetMapping()
//    List<Appointment> getUsers(){
//        return appointmentService.get();
//    }


    
}

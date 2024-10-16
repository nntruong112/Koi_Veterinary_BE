package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.VeterinarianScheduleRequest;
import com.KoiHealthService.Koi.demo.dto.request.fish.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.dto.response.VeterinarianScheduleResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.VeterinarianSchedule;
import com.KoiHealthService.Koi.demo.service.VeterinarianScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/veterinarian_schedules")
public class VeterinarianScheduleController {
    private final VeterinarianScheduleService veterinarianScheduleService;

    //create
    @PostMapping("/create")
    //endpoint users voi method POST, users đặt có s vì nó là invention trong việc đặt tên API
    ApiResponse<VeterinarianSchedule> createVeterinarianSchedule(@RequestBody @Valid VeterinarianScheduleRequest request) {
        ApiResponse<VeterinarianSchedule> apiResponse = new ApiResponse<>();
        apiResponse.setResult(veterinarianScheduleService.createVeterinarianSchedule(request));
        return apiResponse;
    }

    @GetMapping
    public ResponseEntity<List<VeterinarianSchedule>> getSchedules() {
        return ResponseEntity.ok(veterinarianScheduleService.getAllVeterinarianSchedules());
    }

    // Get fish by id==========================================================================================
    @GetMapping("/{veterinarian_schedulesId}")
    VeterinarianSchedule getScheduleById(@PathVariable("veterinarian_schedulesId") String veterinarian_schedulesId){
        return veterinarianScheduleService.getScheduleById(veterinarian_schedulesId);
    }

    @GetMapping("/belonged_to_vetId/{vetId}")
    public ResponseEntity<List<VeterinarianScheduleResponse>> getScheduleByVetId(@PathVariable("vetId") String vetId) {
        return ResponseEntity.ok(veterinarianScheduleService.getScheduleByVeterinarianId(vetId));
    }

    
}

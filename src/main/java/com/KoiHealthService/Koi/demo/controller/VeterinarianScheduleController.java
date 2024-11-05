package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.user.LinkVeterinarianToScheduleRequest;
import com.KoiHealthService.Koi.demo.dto.request.VeterinarianScheduleRequest;
import com.KoiHealthService.Koi.demo.dto.response.VeterinarianScheduleResponse;
import com.KoiHealthService.Koi.demo.entity.VeterinarianProfile;
import com.KoiHealthService.Koi.demo.entity.VeterinarianSchedule;
import com.KoiHealthService.Koi.demo.service.VeterinarianScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/veterinarian_schedules")
public class VeterinarianScheduleController {
    private final VeterinarianScheduleService veterinarianScheduleService;

    //create
    // Create a new schedule for a veterinarian profile
    @PostMapping("/create")
    public ResponseEntity<VeterinarianScheduleResponse> createSchedule(
            @RequestBody VeterinarianScheduleRequest request) {
        // Gọi service để tạo lịch
        VeterinarianScheduleResponse schedule = veterinarianScheduleService.createSchedule(request);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping
    public ResponseEntity<List<VeterinarianSchedule>> getSchedules() {
        return ResponseEntity.ok(veterinarianScheduleService.getAllVeterinarianSchedules());
    }

    // Get schedule by scheduleId==========================================================================================
    @GetMapping("/{veterinarian_schedulesId}")
    VeterinarianSchedule getScheduleById(@PathVariable("veterinarian_schedulesId") String veterinarian_schedulesId){
        return veterinarianScheduleService.getScheduleById(veterinarian_schedulesId);
    }

//    @GetMapping("/belonged_to_vetId/{vetId}")
//    public ResponseEntity<List<VeterinarianScheduleResponse>> getScheduleByVetId(@PathVariable("vetId") String vetId) {
//        return ResponseEntity.ok(veterinarianScheduleService.getScheduleByVeterinarianId(vetId));
//    }

    @GetMapping("/schedules/{veterinarianId}")
    public ResponseEntity<List<VeterinarianSchedule>> getScheduleByVeterinarianId(@PathVariable String veterinarianId) {
        List<VeterinarianSchedule> schedules = veterinarianScheduleService.getScheduleByVeterinarianId(veterinarianId);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/link")
    public ResponseEntity<VeterinarianProfile> linkVeterinarianProfile(@RequestBody LinkVeterinarianToScheduleRequest request) {
        return ResponseEntity.ok(veterinarianScheduleService.linkVeterinarianProfile(request));
    }




    
}

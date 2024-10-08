package com.KoiHealthService.Koi.demo.controller;


import com.KoiHealthService.Koi.demo.dto.request.HealthRecordCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import com.KoiHealthService.Koi.demo.service.FishService;
import com.KoiHealthService.Koi.demo.service.HealthRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthRecords")
@RequiredArgsConstructor
public class HealthRecordController {

    private HealthRecordService healthRecordService;


    @PostMapping("/create")
    ApiResponse<HealthRecord> createHealthRecord(@RequestBody @Valid HealthRecordCreationRequest request) {
        ApiResponse<HealthRecord> apiResponse = new ApiResponse<>();
        apiResponse.setResult(healthRecordService.createHealthRecord(request));
        return apiResponse;
    }

    @GetMapping
    ResponseEntity<List<HealthRecord>> getHealthRecord(){
        return ResponseEntity.ok(healthRecordService.getAllHealthRecords());
    }

    @DeleteMapping("/{healthRecordId}")
    String deleteHealthRecord(@PathVariable String healthRecordId) {
        healthRecordService.deleteHealthRecord(healthRecordId);
        return "HealthRecord has been deleted";
    }
}

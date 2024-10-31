package com.KoiHealthService.Koi.demo.controller;


import com.KoiHealthService.Koi.demo.dto.request.HealthRecordCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.HealthRecordResponse;
import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import com.KoiHealthService.Koi.demo.service.FishService;
import com.KoiHealthService.Koi.demo.service.HealthRecordService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health_records")
@RequiredArgsConstructor
public class HealthRecordController {

    @NonNull
    private final HealthRecordService healthRecordService;
                 //bắt buộc có final ở đây ngen, kiểu nếu ko có final thì nếu những thành phần liên quan bị null nó hong chạy được

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

    //Get healthRecord by fishId
    @GetMapping("/belonged_to_fishId/{fishId}")
//    ResponseEntity<List<HealthRecordResponse>> getHealthRecordByFishId(@PathVariable ("fishId") String fishId){
//        return ResponseEntity.ok(healthRecordService.getHealthRecordsByFishId(fishId));
//    }
    List<HealthRecordResponse> getHealthRecordByFishId(@PathVariable ("fishId") String fishId){
        return healthRecordService.getHealthRecordsByFishId(fishId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthRecord> getHealthRecordById(@PathVariable("id") String healthRecordId) {
        HealthRecord healthRecord = healthRecordService.getHealthRecordById(healthRecordId);
        return ResponseEntity.ok(healthRecord);
    }
}

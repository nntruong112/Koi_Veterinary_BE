package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.fishSpecialty.FishSpecialtyCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.fishSpecialty.FishSpecialtyUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.FishSpecialtyResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.service.FishSpecialtyService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fish_specialties")
@RequiredArgsConstructor
public class FishSpecialtyController {

    
    //@NonNull
    private final FishSpecialtyService fishSpecialtyService;           //must be final
    

    @PostMapping("/create")
        //endpoint voi method POST,  đặt có s vì nó là invention trong việc đặt tên API
    ApiResponse<FishSpecialty> createFishSpecialty(@RequestBody @Valid FishSpecialtyCreationRequest request) {
        ApiResponse<FishSpecialty> apiResponse = new ApiResponse<>();
        apiResponse.setResult(fishSpecialtyService.createFishSpecialty(request));
        return apiResponse;
    }

    @PutMapping("/{fishSpecialtyId}")
    FishSpecialtyResponse updateFishSpecialty(@PathVariable ("fishSpecialtyId")String fishSpecialtyId, @RequestBody @Valid FishSpecialtyUpdateRequest request) {
        return fishSpecialtyService.updateFishSpecialty(fishSpecialtyId, request)  ;
    }

    @DeleteMapping("/{fishSpecialtyId}")
    String deleteFishSpecialty(@PathVariable String fishSpecialtyId) {
        fishSpecialtyService.deleteFishSpecialty(fishSpecialtyId);
        return "Fish specialty has been deleted";
    }

    @GetMapping
    public ResponseEntity<List<FishSpecialty>> getAppointments() {
        return ResponseEntity.ok(fishSpecialtyService.getFishSpecialtyList());
    }

    @GetMapping("/{fishSpecialtyId}")
    public FishSpecialty getFishSpecialtyById(@PathVariable String fishSpecialtyId) {
        return fishSpecialtyService.getFishSpecialtyById(fishSpecialtyId);
    }
}

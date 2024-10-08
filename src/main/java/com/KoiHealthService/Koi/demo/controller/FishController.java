package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.FishUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.UpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.service.FishService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequiredArgsConstructor
@RestController
@RequestMapping("/fishes")
public class FishController {

    @NonNull
    private FishService fishService;

    @PostMapping("/create")
        //endpoint users voi method POST, users đặt có s vì nó là invention trong việc đặt tên API
    ApiResponse<Fish> createFish(@RequestBody @Valid FishCreationRequest request) {
        ApiResponse<Fish> apiResponse = new ApiResponse<>();
        apiResponse.setResult(fishService.createFish(request));
        return apiResponse;
    }

    @PutMapping("/{fishId}")
    Fish updateFish(@PathVariable ("fishId") String fishId, @RequestBody FishUpdateRequest updateRequest) {
        return fishService.updateFish(fishId, updateRequest);
    }


    @GetMapping()
    ResponseEntity<List<Fish>> getFish(){
        return ResponseEntity.ok(fishService.getFishes());
    }

    @DeleteMapping("/{fishId}")
    String deleteFish(@PathVariable String fishId) {
        fishService.deleteFish(fishId);
        return "Fish has been deleted";
    }
}

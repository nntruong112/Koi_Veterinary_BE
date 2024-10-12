package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.fishSpecialty.FishSpecialtyCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.fishSpecialty.FishSpecialtyUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.FishSpecialtyResponse;
import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.service.FishSpecialtyService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fish_specialties")
@RequiredArgsConstructor
public class FishSpecialtyController {

    //đừng để NonNull ở đây, tại sao cũng hong biết nữa, một là để nonnull, 2 là để final
    @NonNull
    private final FishSpecialtyService fishSpecialtyService;
    

    @PostMapping("/create")
        //endpoint users voi method POST, users đặt có s vì nó là invention trong việc đặt tên API
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

}

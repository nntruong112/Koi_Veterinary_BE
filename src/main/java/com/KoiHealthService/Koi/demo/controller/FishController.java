package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.fish.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.fish.FishUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.service.FishService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequiredArgsConstructor
@RestController
@RequestMapping("/fishes")
public class FishController {


    //đừng để NonNull ở đây, tại sao cũng hong biết nữa, một là để nonnull, 2 là để final
    private final FishService fishService;


    //create fish==========================================================================================
    @PostMapping("/create")
        //endpoint users voi method POST, users đặt có s vì nó là invention trong việc đặt tên API
    ApiResponse<Fish> createFish(@RequestBody @Valid FishCreationRequest request) {
        ApiResponse<Fish> apiResponse = new ApiResponse<>();
        apiResponse.setResult(fishService.createFish(request));
        return apiResponse;
    }



    //update fish ==========================================================================================
    @PutMapping("/{fishId}")
    ApiResponse<FishResponse> updateFish(@PathVariable ("fishId") String fishId, @RequestBody FishUpdateRequest updateRequest) {
        ApiResponse<FishResponse> apiResponse = new ApiResponse<>();
        //call service layer
        FishResponse fishResponse = fishService.updateFish(fishId,updateRequest);

        //set result in api response
        apiResponse.setResult(fishResponse);

        apiResponse.setMessage("Fish has been updated");

        return apiResponse;
    }

    // Get fish by id==========================================================================================
    @GetMapping("/{fishId}")
    FishResponse getFishById(@PathVariable("fishId") String fishId){
        return fishService.getFishById(fishId);
    }
    //Get fish by userId==========================================================================================

    @GetMapping("/own_by_users_id/{customerId}")
    public List<Fish> getFishByCustomerId(@PathVariable("customerId") String customerId) {
        return fishService.getFishByCustomerId(customerId);
    }


    
    //get all fish==========================================================================================
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

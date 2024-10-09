package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.service.FishSpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class FishSpecialtyController {

    //đừng để NonNull ở đây, tại sao cũng hong biết nữa, một là để nonnull, 2 là để final
    private final FishSpecialtyService fishSpecialtyService;
    

//    @PostMapping("/create")
//        //endpoint users voi method POST, users đặt có s vì nó là invention trong việc đặt tên API
//    ApiResponse<Service> createService(@RequestBody @Valid ServiceCreationRequest request) {
//        ApiResponse<Service> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(serviceService.createService(request));
//        return apiResponse;
//    }

}

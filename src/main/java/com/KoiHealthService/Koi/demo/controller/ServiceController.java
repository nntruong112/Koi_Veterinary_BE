package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.service.ServiceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    @NonNull
    ServiceService serviceService;

//    @PostMapping("/create")
//        //endpoint users voi method POST, users đặt có s vì nó là invention trong việc đặt tên API
//    ApiResponse<Service> createService(@RequestBody @Valid ServiceCreationRequest request) {
//        ApiResponse<Service> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(serviceService.createService(request));
//        return apiResponse;
//    }

}

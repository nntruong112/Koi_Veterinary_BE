package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.ServiceCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.Service;
import com.KoiHealthService.Koi.demo.service.FishService;
import com.KoiHealthService.Koi.demo.service.ServiceService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

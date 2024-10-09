package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.FishSpecialtyCreationRequest;
import com.KoiHealthService.Koi.demo.repository.ServiceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FishSpecialtyService {
    @NonNull
    private final ServiceRepository serviceRepository;

    private Service service;

    public Service createService(FishSpecialtyCreationRequest request) {




        return null;
    }

    


    
}

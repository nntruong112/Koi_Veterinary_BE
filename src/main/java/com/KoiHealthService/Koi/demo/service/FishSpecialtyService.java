package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.FishSpecialtyCreationRequest;
import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.repository.FishSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FishSpecialtyService {

    private final FishSpecialtyRepository fishSpecialtyRepository;

    private FishSpecialty fishSpecialty;

    private User veterinarian;


    //create fish specialty============================================================================================
    public FishSpecialty createFishSpecialty ( FishSpecialtyCreationRequest request) {

        fishSpecialty = FishSpecialty.builder()
                .fishSpecialtyId(request.getFishSpecialtyId())
                .category(request.getCategory())
                .fishSpecialtyName(request.getFishSpecialtyName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();


        FishSpecialty fish = fishSpecialtyRepository.save(fishSpecialty);
        return fish;    
    }

    //delete fish ====================================================================================
    public void deleteFishSpecialty(String fishSpecialtyId) {
        fishSpecialtyRepository.deleteById(fishSpecialtyId);
    }



    


    
}

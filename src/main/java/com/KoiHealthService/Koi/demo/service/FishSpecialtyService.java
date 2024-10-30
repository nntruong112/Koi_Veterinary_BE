package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.fishSpecialty.FishSpecialtyCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.fishSpecialty.FishSpecialtyUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishSpecialtyResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.FishSpecialtyMapper;
import com.KoiHealthService.Koi.demo.repository.FishSpecialtyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Native;
import java.util.List;


@RequiredArgsConstructor
@Service
public class FishSpecialtyService {

    private final FishSpecialtyRepository fishSpecialtyRepository;

    
    private FishSpecialtyMapper fishSpecialtyMapper;

    private FishSpecialty fishSpecialty;

    private User veterinarian;


    //create fish specialty============================================================================================
    public FishSpecialty createFishSpecialty ( FishSpecialtyCreationRequest request) {

        fishSpecialty = FishSpecialty.builder()
                .fishSpecialtyId(request.getFishSpecialtyId())
                .category(request.getCategory())
                .fishSpecialtyName(request.getFishSpecialtyName())
                .description(request.getDescription())
                
                .build();


        FishSpecialty fish = fishSpecialtyRepository.save(fishSpecialty);
        return fish;    
    }

    public List<FishSpecialty> getFishSpecialtyList() {
        return fishSpecialtyRepository.findAll();
    }

    //delete fish specialty ====================================================================================
    public void deleteFishSpecialty(String fishSpecialtyId) {
        fishSpecialtyRepository.deleteById(fishSpecialtyId);
    }

    //update fish specialty
    public FishSpecialtyResponse updateFishSpecialty(String fishSpecialtyId, FishSpecialtyUpdateRequest request) {
        FishSpecialty fishSpecialty = fishSpecialtyRepository.findById(fishSpecialtyId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_SPECIALTY_FOUND));

        // Update fields manually using the request data
        fishSpecialty = FishSpecialty.builder()
                .fishSpecialtyId(fishSpecialty.getFishSpecialtyId())  // Keep the existing ID
                .fishSpecialtyName(request.getFishSpecialtyName() != null ? request.getFishSpecialtyName() : fishSpecialty.getFishSpecialtyName())
                .description(request.getDescription() != null ? request.getDescription() : fishSpecialty.getDescription())
                .category(request.getCategory() != null ? request.getCategory() : fishSpecialty.getCategory())
                .build();

        // Save the updated entity and build the response using builder
        fishSpecialty = fishSpecialtyRepository.save(fishSpecialty);

        return FishSpecialtyResponse.builder()
                .fishSpecialtyId(fishSpecialty.getFishSpecialtyId())
                .fishSpecialtyName(fishSpecialty.getFishSpecialtyName())
                .description(fishSpecialty.getDescription())
                .category(fishSpecialty.getCategory())
                .build();
    }

    public FishSpecialty getFishSpecialtyById(String fishSpecialtyId) {
        return fishSpecialtyRepository.findById(fishSpecialtyId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_SPECIALTY_FOUND));
    }

    
    
}

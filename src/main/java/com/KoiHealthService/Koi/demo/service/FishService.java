package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.FishUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.UpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.FishMapper;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@NonNull
@Service
@RequiredArgsConstructor
public class FishService {

    @NonNull
    private final FishRepository fishRepository;

    @NonNull
    private final FishMapper fishMapper;

    @NonNull
    private final UserRepository userRepository;

    
    private User customer;

    private Fish fish;

    public Fish createFish(FishCreationRequest request) {

         customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));

        fish = Fish.builder()
                .species(request.getSpecies())
                .age(request.getAge())
                .customer(customer)
                .build();

        return fishRepository.save(fish);
    }

    public List<Fish> getFishes() {
        return fishRepository.findAll();
    }

    public FishResponse getFish(String id) {
        return fishMapper.toFishResponse(fishRepository.findById(id).orElseThrow(() -> new RuntimeException("Fish not found")));
    }

    public void deleteFish(String fishId) {
        fishRepository.deleteById(fishId);
    }

    //Update fish
    public FishResponse updateFish(String id, FishUpdateRequest request){
        Fish fish = fishRepository.findById(id).orElseThrow(() -> new RuntimeException("Fish is not found") );

        fish = Fish.builder()
                .age(request.getAge())
                .species(request.getSpecies())
                .build();

        return null;
    }
}

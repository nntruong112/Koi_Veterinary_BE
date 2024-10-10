package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.fish.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.fish.FishUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.FishMapper;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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


    //create fish ============================================================================
    public Fish createFish(FishCreationRequest request) {

         customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));

        fish = Fish.builder()
                .species(request.getSpecies())
                .age(request.getAge())
                .image(request.getImage())
                .weight(request.getWeight())
                .size(request.getSize())
                .color(request.getColor())
                .gender(request.getGender())
                .customer(customer)//trong entity khai báo cho customer là dạng User
                .build();

        return fishRepository.save(fish);
    }

    //get all fishes==================================================================================
    public List<Fish> getFishes() {
        return fishRepository.findAll();
    }

    //get fish by id ===============================================================================
    public FishResponse getFishById(String id) {
        fish = fishRepository.findById(id).orElseThrow(() -> new RuntimeException("Fish is not found") );

        User customer = fish.getCustomer();

        return FishResponse.builder()
                .fishId(fish.getFishId())
                .age(fish.getAge())
                .species(fish.getSpecies())
                .image(fish.getImage())
                .weight(fish.getWeight())
                .size(fish.getSize())
                .customerId(customer.getUserId())         //trong FishResponse khai báo là String
                .build();

    }
    //Get fish by userId
    public List<Fish> getFishByUserId(String userId) {

        
        return null;
    }



    //delete fish ====================================================================================

    public void deleteFish(String fishId) {
        fishRepository.deleteById(fishId);
    }



    //Update fish    ======================================================================================
    public Fish updateFish(String id, FishUpdateRequest request){
        //fetch fish by id
        fish = fishRepository.findById(id).orElseThrow(() -> new RuntimeException("Fish is not found") );

        //update fish nè
        fish.setAge(request.getAge());
        fish.setSpecies(request.getSpecies());
        fish.setImage(request.getImage());
        fish.setWeight(request.getWeight());
        fish.setSize(request.getSize());
       
        return fishRepository.save(fish);
    }
}

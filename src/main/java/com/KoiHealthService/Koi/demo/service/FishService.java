package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.mapper.FishMapper;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FishService {

    private FishRepository fishRepository;

    
    private FishMapper fishMapper;

    public Fish createFish(FishCreationRequest request){
        Fish fish = fishMapper.toFish(request);

        return fishRepository.save(fish);
    }

    public List<Fish> getFishes() {
        return fishRepository.findAll();
    }

    public FishResponse getFish(String id) {
        return fishMapper.toFishResponse(fishRepository.findById(id).orElseThrow(() -> new RuntimeException("Fish not found"))); //nếu tìm thấy user sẽ trả về, else báo lỗi

    }
}

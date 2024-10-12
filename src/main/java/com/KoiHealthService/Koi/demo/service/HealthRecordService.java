package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.HealthRecordCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.HealthRecordResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.HealthRecordMapper;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import com.KoiHealthService.Koi.demo.repository.HealthRecordRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@NonNull
@RequiredArgsConstructor
@Service
public class HealthRecordService {

    @Autowired
    private final FishRepository fishRepository;
    @NonNull
    private  HealthRecordRepository healthRecordRepository;

    @Autowired
    private final HealthRecordMapper healthRecordMapper;


    private  HealthRecord healthRecord;

    private Fish fish;

    public HealthRecord createHealthRecord(HealthRecordCreationRequest request) {

        fish = fishRepository.findById(request.getFishId())
                .orElseThrow(() -> new RuntimeException("Fish is not found")); //gán giá trị cho cá, nếu hong có thì exception

        healthRecord = HealthRecord.builder()
                .healthRecordId(request.getHealthRecordId())
                .createdDate(request.getCreatedDate())
                .diagnosis(request.getDiagnosis())
                .fish(fish)
                .treatment(request.getTreatment())
                .build();

        return healthRecordRepository.save(healthRecord);
    }

    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordRepository.findAll();
    }

    public void deleteHealthRecord(String healthRecordId) {
        healthRecordRepository.deleteById(healthRecordId);
    }

    //get healthRecord by fishId
    public List<HealthRecordResponse> getHealthRecordsByFishId(String fishId) {
        Fish fish = fishRepository.findById(fishId).orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_FOUND));
        List<HealthRecord> foundHealthRecord= healthRecordRepository.findByFishId(fishId);

          return foundHealthRecord.stream()
                .map(healthRecordMapper::toHealthResponse) // Map each healthRecord to HealthRecordResponse
                .collect(Collectors.toList()); // Collect results into a List
    }

//    // Get fish by customerId
//    public List<HealthRecordResponse> getHealthRecordByFishId(String fishId) {
//        return healthRecordRepository.findByFishId(fishId);
//    }

}

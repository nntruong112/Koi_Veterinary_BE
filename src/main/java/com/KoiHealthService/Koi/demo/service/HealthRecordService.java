package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.HealthRecordCreationRequest;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import com.KoiHealthService.Koi.demo.repository.HealthRecordRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@NonNull
@RequiredArgsConstructor
@Service
public class HealthRecordService {

    @NonNull
    private final FishRepository fishRepository;
    @NonNull
    private  HealthRecordRepository healthRecordRepository;


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

}

package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.HealthRecordCreationRequest;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import com.KoiHealthService.Koi.demo.repository.HealthRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class HealthRecordService {

    private  HealthRecordRepository healthRecordRepository;
    private  HealthRecord healthRecord;

    private Fish fish;

    public HealthRecord createHealthRecord(HealthRecordCreationRequest request) {

        healthRecord = HealthRecord.builder()
                .healthRecordId(request.getHealthRecordId())
                .appointmentDate(request.getAppointmentDate())
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

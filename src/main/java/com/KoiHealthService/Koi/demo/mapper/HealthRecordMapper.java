package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.HealthRecordCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.HealthRecordResponse;
import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface HealthRecordMapper{
    HealthRecord toHealthRecord(HealthRecordCreationRequest request);
    HealthRecordResponse toHealthResponse(HealthRecord healthRecord);
}

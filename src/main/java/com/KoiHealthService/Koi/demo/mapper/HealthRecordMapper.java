package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.HealthRecordResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.HealthRecord;
import com.KoiHealthService.Koi.demo.entity.User;
import lombok.NonNull;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface HealthRecordMapper{
    HealthRecord toUser(UserRequest userRequest);
    HealthRecordResponse toHealthResponse(HealthRecord healthRecord);
}

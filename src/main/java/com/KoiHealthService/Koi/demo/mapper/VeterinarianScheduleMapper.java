package com.KoiHealthService.Koi.demo.mapper;


import com.KoiHealthService.Koi.demo.dto.request.VeterinarianScheduleRequest;
import com.KoiHealthService.Koi.demo.dto.response.VeterinarianScheduleResponse;
import com.KoiHealthService.Koi.demo.entity.VeterinarianSchedule;
import lombok.NonNull;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeterinarianScheduleMapper {
    @NonNull
    VeterinarianSchedule toVeterinarianSchedule(VeterinarianScheduleRequest veterinarianScheduleRequest);
    @NonNull
    VeterinarianScheduleResponse toVeterinarianResponse(VeterinarianSchedule veterinarianSchedule);
}

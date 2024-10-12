package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.fishSpecialty.FishSpecialtyCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.fishSpecialty.FishSpecialtyUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.user.UpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishSpecialtyResponse;
import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FishSpecialtyMapper {
    FishSpecialty toFishSpecialty(FishSpecialtyCreationRequest request);
    FishSpecialtyResponse toFishSpecialtyResponse(FishSpecialty fishSpecialty);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateFishSpecialty(@MappingTarget FishSpecialty fishSpecialty, FishSpecialtyUpdateRequest request);
}

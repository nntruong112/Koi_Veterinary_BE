package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.FishUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import lombok.NonNull;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface FishMapper {

    Fish toFish(FishCreationRequest request);
    FishResponse toFishResponse(Fish fish);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFish(@MappingTarget Fish fish, FishUpdateRequest request);
}

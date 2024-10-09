package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.fish.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.fish.FishUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface FishMapper {

    Fish toFish(FishCreationRequest request);
    FishResponse toFishResponse(Fish fish);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFish(@MappingTarget Fish fish, FishUpdateRequest request);
}

package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.NewsCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.NewsUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.NewsResponse;
import com.KoiHealthService.Koi.demo.entity.News;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(target = "author", ignore = true) // Ignore setting author directly here
    News toNews(NewsCreationRequest newsRequest);

    @Mapping(target = "authorId", source = "author.userId")
    NewsResponse toNewsResponse(News news);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNewsFromRequest(NewsUpdateRequest newsRequest, @MappingTarget News news);

}

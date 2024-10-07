package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.User;
import lombok.NonNull;
import org.mapstruct.Mapper;


@NonNull
@Mapper(componentModel = "spring")
public interface ServiceMapper {
    @NonNull
    User toUser(UserRequest userRequest);
    @NonNull
    UserResponse toUserResponse(User user);
}

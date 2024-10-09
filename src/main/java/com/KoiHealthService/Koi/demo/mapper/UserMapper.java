package com.KoiHealthService.Koi.demo.mapper;


import com.KoiHealthService.Koi.demo.dto.request.user.UpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.user.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.User;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring")
public interface UserMapper {
    @NonNull
    User toUser(UserRequest userRequest);
    @NonNull
    UserResponse toUserResponse(User user);
    void toUpdateUser(@MappingTarget User user, UpdateRequest updateRequest);
    UserResponse toVerifyCodeResponse(String verificationCode);
}

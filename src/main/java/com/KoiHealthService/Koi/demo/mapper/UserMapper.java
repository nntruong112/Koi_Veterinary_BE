package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.LoginRequest;
import com.KoiHealthService.Koi.demo.dto.request.UpdateUserRequest;
import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.LoginResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);
    LoginResponse toLogin(User user);
    void toUpdateUser(@MappingTarget User user, UpdateUserRequest updateUserRequest);
}

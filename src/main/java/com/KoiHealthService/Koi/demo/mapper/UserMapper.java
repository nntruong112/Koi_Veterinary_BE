package com.KoiHealthService.Koi.demo.mapper;

import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper (componentModel = "string")
public interface UserMapper {
    User toUser(UserRequest userRequest);

}

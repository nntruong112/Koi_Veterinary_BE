package com.KoiHealthService.Koi.demo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String firstname;
    String lastname;
    String name;
    String username;
    String email;
    Set<String> roles;
}

package com.KoiHealthService.Koi.demo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userId;
    String firstname;
    String lastname;
    String username;
    String email;
    String phone;
    String address;
    String gender;
    String image;
    LocalDate dateOfBirth;
    Set<String> roles;
}

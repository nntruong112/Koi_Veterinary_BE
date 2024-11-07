package com.KoiHealthService.Koi.demo.dto.request.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRequest {
    String firstname;
    String lastname;
    String name;
    String phone;
    String address;
    LocalDate dateOfBirth;
    String gender;
    String image;
    String fishSpecialtyId;
    Integer rating;
    String email;
}

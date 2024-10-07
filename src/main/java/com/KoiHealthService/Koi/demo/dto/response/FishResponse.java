package com.KoiHealthService.Koi.demo.dto.response;


import com.KoiHealthService.Koi.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FishResponse {
    String fishId;
    String species;
    Integer age;
    String customerId;
}

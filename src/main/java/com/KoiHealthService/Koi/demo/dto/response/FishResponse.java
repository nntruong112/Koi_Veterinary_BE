package com.KoiHealthService.Koi.demo.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FishResponse {
    String fishId;
    String name;
    String species;
    Integer age;
}

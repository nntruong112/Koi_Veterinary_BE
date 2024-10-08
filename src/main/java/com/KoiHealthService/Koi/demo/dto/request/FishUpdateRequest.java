package com.KoiHealthService.Koi.demo.dto.request;


import com.KoiHealthService.Koi.demo.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FishUpdateRequest {
    String species;
    @Min(value = 1, message = "FISH_AGE_INVALID")
    @Max(value = 40, message = "FISH_AGE_INVALID")
    int age;
}

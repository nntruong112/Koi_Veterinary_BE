package com.KoiHealthService.Koi.demo.dto.request.fish;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class FishCreationRequest {

    String fishId;
    String species;
    @Min(value = 1, message = "FISH_AGE_INVALID")
    @Max(value = 40, message = "FISH_AGE_INVALID")
    Integer age;
    String customerId;
    BigDecimal size;
    BigDecimal weight;
    String gender;
    String color;
    String image;
}

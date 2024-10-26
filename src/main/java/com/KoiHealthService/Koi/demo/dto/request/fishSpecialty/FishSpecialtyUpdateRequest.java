package com.KoiHealthService.Koi.demo.dto.request.fishSpecialty;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FishSpecialtyUpdateRequest {

    String fishSpecialtyName;
    String description;
    String category;
    String experience;

}
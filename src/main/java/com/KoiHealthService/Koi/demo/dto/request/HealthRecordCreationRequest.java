package com.KoiHealthService.Koi.demo.dto.request;

import com.KoiHealthService.Koi.demo.entity.Fish;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthRecordCreationRequest {
    String healthRecordId;
    LocalDate createdDate;
    String diagnosis;
    String treatment;
    String fishId;
    String veterinarianId;
    String medicine;
}

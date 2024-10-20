package com.KoiHealthService.Koi.demo.dto.response;

import com.KoiHealthService.Koi.demo.entity.Fish;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthRecordResponse {
    String healthRecordId;
    LocalDate createdDate;
    String diagnosis;
    String treatment;
    String fishId;
    String veterinarianId;
}

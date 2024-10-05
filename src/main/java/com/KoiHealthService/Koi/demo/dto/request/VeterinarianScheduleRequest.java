package com.KoiHealthService.Koi.demo.dto.request;

import com.KoiHealthService.Koi.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VeterinarianScheduleRequest {
    String scheduleId;
    LocalDate availableDate;
    LocalTime startTime;
    LocalTime endTime;
    User veterinarian;
}

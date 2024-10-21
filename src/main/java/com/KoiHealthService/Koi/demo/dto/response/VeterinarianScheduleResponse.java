package com.KoiHealthService.Koi.demo.dto.response;

import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.entity.VeterinarianProfile;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VeterinarianScheduleResponse {
    String scheduleId;
    String availableDate;
    LocalTime startTime;
    LocalTime endTime;
    String veterinarianId;
     String veterinarianName;

}

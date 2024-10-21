package com.KoiHealthService.Koi.demo.dto.request;

import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.entity.VeterinarianProfile;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class VeterinarianScheduleRequest {
    String scheduleId;
    String availableDate;
    LocalTime startTime;
    LocalTime endTime;
    String veterinarianId;
}

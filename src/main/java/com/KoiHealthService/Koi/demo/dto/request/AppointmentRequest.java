package com.KoiHealthService.Koi.demo.dto.request;

import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRequest {
    String id;
    LocalDate appointmentDate;
    String appointmentType;
    User customer;
    User veterinarian;
    Fish fish;
    String location;

}

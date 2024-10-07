package com.KoiHealthService.Koi.demo.dto.request;

import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRequest {
    String appointmentId;
    LocalDate appointmentDate;
    String appointmentType;
    String status;
    String customerId;
    String veterinarianId;
    String fishId;
    String location;
    LocalTime startTime;
    LocalTime endTime;

}

package com.KoiHealthService.Koi.demo.dto.response;

import com.KoiHealthService.Koi.demo.entity.Fish;
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
public class AppointmentResponse {
    String appointmentId;
    LocalDate appointmentDate;
    String status;
    String customerId;
    String veterinarianId;
    String fishId;
    String location;
    LocalTime startTime;
    LocalTime endTime;
    String appointmentTypeId;
    String paymentStatus;
    Long movingFee;
}

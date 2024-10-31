package com.KoiHealthService.Koi.demo.dto.request.appointment;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentUpdateRequest {
    String appointmentId;
    LocalDate appointmentDate;
    String appointmentTypeId;
    String status;
    String customerId;
    String veterinarianId;
    String location;
    LocalTime startTime;
    LocalTime endTime;
    String paymentStatus;
    String veterinarianId;

}

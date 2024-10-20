package com.KoiHealthService.Koi.demo.dto.request.appointmentType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentTypeCreationRequest {
    String appointmentTypeId;
    String appointmentService;
    Long price;
}

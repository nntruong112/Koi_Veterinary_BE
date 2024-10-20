package com.KoiHealthService.Koi.demo.dto.request.appointmentType;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentTypeUpdateRequest {
    String appointmentTypeId;
    String appointmentService;
    Long price;
}


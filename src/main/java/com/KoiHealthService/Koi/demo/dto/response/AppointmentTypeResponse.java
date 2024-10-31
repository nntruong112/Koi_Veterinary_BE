package com.KoiHealthService.Koi.demo.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentTypeResponse {
    String appointmentTypeId;
    String appointmentService;
    Long price;
    Long appointmentCount;
}

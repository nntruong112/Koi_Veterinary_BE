package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "appointment_types")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String appointmentTypeId;
    String appointmentService;
    BigDecimal price;

}

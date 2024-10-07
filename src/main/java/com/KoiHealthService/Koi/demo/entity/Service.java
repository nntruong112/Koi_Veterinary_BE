package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "services")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String serviceId;
    String serviceName;
    String description;
    String category;
    BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "appointmentId")
    Appointment appointment;
    @ManyToOne
    @JoinColumn(name = "veterinarianId")
    User veterinarian;
}

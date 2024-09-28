package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @Column(length = 255, nullable = false)
    private String serviceName;

    @Lob
    private String description;

    @Column(length = 255)
    private String category;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private User veterinarian;
}

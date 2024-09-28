package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    private LocalDate appointmentDate;

    private Integer consultationId;

    @Column(length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User staff;

    @Column(length = 255)
    private String location;
}

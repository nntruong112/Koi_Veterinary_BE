package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "appointment_id")
     String appointmentId;

    @Column(name = "appointment_date")
     LocalDate appointmentDate;

    @Column(name = "appointment_type", length = 50)
     String appointmentType;

    @Column(name = "status", length = 50)
     String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    User customer;  // Reference to Account (was User)

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    User veterinarian;  // Reference to Account (was User)

    @ManyToOne
    @JoinColumn(name = "fish_id")
     Fish fish;

    @Column(name = "location", length = 255)
     String location;

    @Column(name = "start_time")
     LocalTime startTime;

    @Column(name = "end_time")
     LocalTime endTime;

}

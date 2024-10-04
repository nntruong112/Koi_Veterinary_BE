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

    String appointmentId;


    LocalDate appointmentDate;


    String appointmentType;


    String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    User customer;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    User veterinarian;

    @ManyToOne
    @JoinColumn(name = "fish_id")
    Fish fish;


    String location;


    LocalTime startTime;


    LocalTime endTime;

}

package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String appointmentId;
    LocalDate appointmentDate;
    String appointmentType;
    String status;
    LocalTime startTime;
    LocalTime endTime;
    String location;

    @ManyToOne
    @JoinColumn(name = "customerId")
    User customer;

    @ManyToOne
    @JoinColumn(name = "veterinarianId")
    User veterinarian;

    @ManyToOne
    @JoinColumn(name = "fishId")
    Fish fish;




}

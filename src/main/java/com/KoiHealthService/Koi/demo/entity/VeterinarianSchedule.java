package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "veterinarian_schedules")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VeterinarianSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String scheduleId;


    LocalDate availableDate;


    LocalTime startTime;


    LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    User veterinarian;


}

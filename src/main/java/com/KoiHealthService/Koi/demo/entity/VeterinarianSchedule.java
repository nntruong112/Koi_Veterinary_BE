package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "vet_schedules")
public class VeterinarianSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    private LocalDate availableDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private User veterinarian;
}

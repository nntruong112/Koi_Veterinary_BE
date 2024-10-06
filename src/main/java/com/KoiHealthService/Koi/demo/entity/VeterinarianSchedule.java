package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "veterinarian_schedules")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeterinarianSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String scheduleId;
    LocalDate availableDate;
    LocalTime startTime;
    LocalTime endTime;
    @ManyToOne
    @JoinColumn(name = "veterinarianId")
    User veterinarian;


}

package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "health_records")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    String healthRecordId;
    LocalDate createdDate;
    String diagnosis;
    String treatment;
    String medicine;
    @ManyToOne
    @JoinColumn(name = "fishId")
    Fish fish;

    @ManyToOne
    @JoinColumn(name = "created_by") // Foreign key referencing the veterinarian who created this record
    private User veterinarian;
}

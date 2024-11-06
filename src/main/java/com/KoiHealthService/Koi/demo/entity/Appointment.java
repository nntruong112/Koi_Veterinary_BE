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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointmentTypeId")
    AppointmentType appointmentType;

    String status;
    LocalTime startTime;
    LocalTime endTime;
    String location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    User customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinarianId")
    User veterinarian;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishId")
    Fish fish;
    String paymentStatus;
    Long movingFee;

    public void CalculateDistance() {
        if (movingFee != null) {
            this.movingFee = movingFee * 10000;
        }
        else {
            movingFee = 0L;
        }
    }

}

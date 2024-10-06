package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "feedbacks")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String feedbackId;
    String comment;
    Integer rating;
    @ManyToOne
    @JoinColumn(name = "customerId")
    User customer;
    @ManyToOne
    @JoinColumn(name = "appointmentId")
    Appointment appointment;

    
}

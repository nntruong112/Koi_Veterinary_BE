package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "feedbacks")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "feedback_id")
     String feedbackId;

    @Column(name = "comment", columnDefinition = "TEXT")
     String comment;

    @Column(name = "rating")
     Integer rating;

    @ManyToOne
    @JoinColumn(name = "customer_id")
     User customer;  // Reference to Account (was User)

    @ManyToOne
    @JoinColumn(name = "appointment_id")
     Appointment appointment;

    
}

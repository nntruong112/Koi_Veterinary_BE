package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "invoices")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    String invoiceId;


    BigDecimal total;


    BigDecimal discount;


    String paymentStatus;


    String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    Appointment appointment;

    // Getters and Setters
}

package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

    private BigDecimal total;

    private BigDecimal discount;

    @Column(length = 50)
    private String paymentStatus;

    @Column(length = 50)
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;
}

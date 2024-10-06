package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "invoices")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String invoiceId;
    BigDecimal total;
    BigDecimal discount;
    String paymentStatus;
    String paymentMethod;
    @ManyToOne
    @JoinColumn(name = "appointmentId")
    Appointment appointment;
}

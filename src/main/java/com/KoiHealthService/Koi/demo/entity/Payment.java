package com.KoiHealthService.Koi.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String paymentId;

    @Column(nullable = false)
    Long amountValue;

    @Column(nullable = false)
    LocalDateTime vnp_PayDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
     User user;

    @ManyToOne
    @JoinColumn(name = "appointmentId")
    Appointment appointment;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String orderType;

    @Column(nullable = false)
    String vnp_TxnRef;

    @Column(nullable = false)
    String name;

}

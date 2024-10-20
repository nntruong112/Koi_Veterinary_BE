package com.KoiHealthService.Koi.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String createDate;
    Long expireDate;

    @ManyToOne
    @JoinColumn(name = "paymentId")
    Payment payment; // Liên kết tới Payment
}
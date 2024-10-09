package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "fishes")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String fishId;
    String species;
    Integer age;
    String gender;
    String color;
    BigDecimal size;
    BigDecimal weight;
    String image;
    @ManyToOne
    @JoinColumn(name = "customerId")
    User customer;
}

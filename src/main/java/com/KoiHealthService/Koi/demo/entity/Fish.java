package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "fishes")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String fishId;
    String name;
    String species;
    Integer age;
    @ManyToOne
    @JoinColumn(name = "customerId")
    User customer;
}

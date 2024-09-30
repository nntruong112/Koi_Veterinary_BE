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
    @Column(name = "fish_id")
     String fishId;

    @Column(name = "name", length = 255)
     String name;

    @Column(name = "species", length = 255)
     String species;

    @Column(name = "age")
     Integer age;

    @ManyToOne
    @JoinColumn(name = "customer_id")
     User customer;  // Reference to Account (was User)

    // Getters and Setters
}

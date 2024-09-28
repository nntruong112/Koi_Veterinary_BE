package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "fishes")
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fishId;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String species;

    private Integer age;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}

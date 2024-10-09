package com.KoiHealthService.Koi.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;
    String firstname;
    String lastname;
    String username;
    String password;
    String email;
    String phone;
    String address;
    String gender;
    String image;
    LocalDate dateOfBirth;
    String verificationCode;
    Date verificationCodeExpiration;
    Set<String> roles;

    @ManyToOne
    @JoinColumn(name = "specialtyId") // Foreign key column name
    FishSpecialty fishSpecialty;

}

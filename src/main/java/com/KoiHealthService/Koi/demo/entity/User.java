package com.KoiHealthService.Koi.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "accounts")
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
    LocalDate dateOfBirth;
    String verificationCode;
    Date verificationCodeExpiration;
    Set<String> roles;
}

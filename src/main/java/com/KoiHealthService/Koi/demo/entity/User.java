package com.KoiHealthService.Koi.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "accounts")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
     Long accountId;

    @Column(name = "name", length = 255)
     String name;

    @Column(name = "email", length = 255)
     String email;

    @Column(name = "username", length = 255)
     String username;

    @Column(name = "password", length = 255)
    String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
     Role role;
}

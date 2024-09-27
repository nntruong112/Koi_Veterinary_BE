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
@Table (name = "Account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String username;
    String password;
    String email;
    //String phone;
}

package com.KoiHealthService.Koi.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table (name = "Accounts")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String id;
//    String username;
//    String password;
//    String email;
//    String phone;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String email;
    String username;
    String password;

//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    Role role;

}

package com.KoiHealthService.Koi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
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
public class User implements Serializable {
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
    String roles;
    boolean checkIsLoginGoogle;

    // One-to-many with VeterinarianProfile
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<VeterinarianProfile> veterinarianProfiles;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "specialtyId") // Foreign key column name
    FishSpecialty fishSpecialty;

}



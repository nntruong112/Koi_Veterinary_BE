package com.KoiHealthService.Koi.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    Integer rating = 10;

}



package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Builder;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "veterinarian_profiles")
public class VeterinarianProfile {

//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String veterinarianProfileId;
//
//    // Many-to-One relationship with User
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "userId")
//    User user;
//
//    // Many-to-One relationship with FishSpecialty
//    @ManyToOne
//    @JoinColumn(name = "fish_specialty_id", referencedColumnName = "fishSpecialtyId")
//    FishSpecialty fishSpecialty;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String veterinarianProfilesId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    User user;

    @ManyToOne
    @JoinColumn(name = "fishSpecialtyId", referencedColumnName = "fishSpecialtyId")
    FishSpecialty fishSpecialty;

    // One-to-many with VeterinarianSchedule
    @OneToMany(mappedBy = "veterinarianProfile", cascade = CascadeType.ALL)
    List<VeterinarianSchedule> veterinarianSchedules;
    
}

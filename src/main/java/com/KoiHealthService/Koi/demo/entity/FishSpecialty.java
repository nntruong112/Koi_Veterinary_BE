package com.KoiHealthService.Koi.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "fish_specialties")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FishSpecialty {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String fishSpecialtyId;
    String fishSpecialtyName;
    String description;
    String category;
    BigDecimal price;

    // One-to-many with VeterinarianProfile
    @OneToMany(mappedBy = "fishSpecialty", cascade = CascadeType.ALL)
    List<VeterinarianProfile> veterinarianProfiles;
}
    

